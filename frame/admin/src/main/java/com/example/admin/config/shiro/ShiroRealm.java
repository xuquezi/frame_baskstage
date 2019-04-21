package com.example.admin.config.shiro;

import com.example.admin.config.jwt.JWTToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import constant.FrameConstant;
import entity.ActiveUser;
import entity.FrameAuth;
import entity.FrameRole;
import entity.FrameUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import service.AuthService;
import service.RoleService;
import service.UserService;
import utils.JWTUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Slf4j
public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthService authService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ObjectMapper mapper;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("---------------- 执行 Shiro 权限获取 ----------------");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String userName = JWTUtil.getUsername(principalCollection.toString());
        FrameUser user = userService.findUserByUsername(userName);
        List<FrameRole> roleList = null;
        if(user!=null&&user.getUserDelete()==0){
            //查询所有角色
            roleList = roleService.findRoleListByUserId(user.getUserId());
            if(roleList!=null&&roleList.size()>0){
                //注入角色(查询所有的角色注入控制器）
                for (FrameRole role : roleList) {
                    authorizationInfo.addRole(role.getRoleName());
                    //查询角色下所有权限
                    List<FrameAuth> authList = authService.findAuthListByRoleId(role.getRoleId());
                    for (FrameAuth auth : authList) {
                        //注入权限
                        authorizationInfo.addStringPermission(auth.getAuthName());
                        log.info("---------------- Shiro 权限获取成功 ----------------------");
                    }
                }
                return authorizationInfo;
            }
        }
        return null;
    }

    /**
     * 用户验证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("---------------- 执行 Shiro 凭证认证 ----------------");
        String token = (String) authenticationToken.getPrincipal();
        HttpServletRequest request = this.getHttpServletRequest();
        String ip = this.getIpAddr(request);
        log.info("获取到的ip为："+ ip);
        String tokenKey = FrameConstant.TOKEN_PREFIX+token+"."+ip;
        String userKey = FrameConstant.ACTIVE_USERS_PREFIX+token+"."+ip;
        String redisValue = this.getRedisValue(tokenKey);
        String userValue = this.getRedisValue(userKey);
        if(StringUtils.isBlank(redisValue)||StringUtils.isBlank(userValue)){
            throw new AuthenticationException("身份已经过期");
        }
        try {
            FrameUser user = this.getUser(userValue, token);
            if(user==null){
                throw new AuthenticationException("用户名或密码错误");
            }
            if(!JWTUtil.verify(token,user.getUserName(),user.getUserPassword(),user.getUserId())){
                throw new AuthenticationException("身份校验不通过");
            }
            AuthenticationInfo info = new SimpleAuthenticationInfo(token,token,getName());//getName()获得的是shiro.xml中配置的myRealm
            log.info("---------------- Shiro 凭证认证成功 ----------------");
            return info;
        } catch (IOException e) {
            log.error("error：{}", e);
            throw new RuntimeException();
        }
    }

    /**
     * 使用JWT替代原生Token
     *
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    // 获取request
    public HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    /**
     * 获取 IP地址
     * 使用 Nginx等反向代理软件， 则不能通过 request.getRemoteAddr()获取 IP地址
     * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，
     * X-Forwarded-For中第一个非 unknown的有效IP字符串，则为真实IP地址
     */
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }


    //根据键获取redis中的值
    public String getRedisValue(String key){
        ValueOperations<String, String> ops = this.redisTemplate.opsForValue();
        String value = ops.get(key);
        log.info("从redis中获取值为："+value);
        return value;
    }

    public FrameUser getUser(String userValue,String token) throws IOException {
        FrameUser user = new FrameUser();
        if(StringUtils.isNotEmpty(userValue)){
            ActiveUser activeUser = mapper.readValue(userValue, ActiveUser.class);
            if(activeUser==null){
                // 解密获得username，用于和数据库进行对比
                String username = JWTUtil.getUsername(token);
                log.info("获取的解密后的username为："+username);
                FrameUser frameUser = userService.findUserByUsername(username);
               return frameUser.getUserStatus() == 0 ? frameUser : null;
            }
            user.setUserName(activeUser.getUsername());
            user.setUserPassword(activeUser.getPassword());
            user.setUserId(activeUser.getId());
            return user;
        }
        return null;
    }
}
