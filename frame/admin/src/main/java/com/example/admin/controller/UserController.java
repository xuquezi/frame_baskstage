package com.example.admin.controller;

import com.example.admin.config.exception.NotActivatedException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import constant.FrameConstant;
import entity.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.LoginService;
import service.LogoutService;
import service.RoleService;
import service.UserService;
import utils.JWTUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/frame/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private LoginService loginService;
    @Autowired
    private LogoutService logoutService;

    /**
     * 登录方法
     * @param user
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public ResponseResult login(@RequestBody FrameUser user, HttpServletRequest request){
        String username = user.getUserName();
        String password = user.getUserPassword();
        String token = "";
        String ip = "";
        log.info("登录用户名为："+username);
        log.info("登录密码："+password);
        FrameUser frameUser = userService.findUserByUsername(username);
        if(frameUser!=null){
            if(frameUser.getUserPassword().equals(super.encryptPassword(password))){
                if(2==frameUser.getUserStatus()){
                    throw new NotActivatedException("用户未激活");
                }
                token = JWTUtil.sign(username, super.encryptPassword(password),frameUser.getUserId());
                ip = super.getIpAddr(request);
                log.info("登录的ip为："+ ip);
                try {
                    ActiveUser activeUser = super.generateActiveUser(frameUser, token, ip);
                    String jsonUser = mapper.writeValueAsString(activeUser);
                    // token存到redis中，有效時間為5分鐘
                    super.setRedisKey(FrameConstant.TOKEN_PREFIX+token+"."+ip,token,5L);
                    // 用户信息存到redis中，有效時間為5分鐘
                    super.setRedisKey(FrameConstant.ACTIVE_USERS_PREFIX+token+"."+ip,jsonUser,5L);
                    //保存登入日志
                    loginService.saveLoginLog(frameUser.getUserName(),ip);
                } catch (JsonProcessingException e) {
                    log.error("error：{}", e.getMessage());
                    throw new RuntimeException();
                }
                return new LoginResult("登录成功", FrameConstant.LOGIN_SUCCESS,true,token,username,frameUser.getUserPic());
            }else {
                throw new IncorrectCredentialsException("密码不正确");
            }

        }else {
            throw new UnknownAccountException("用户名不存在");
        }
    }

    /**
     * 登录时获取角色权限
     * @param token
     * @return
     */
    @GetMapping("/getRoles")
    @ResponseBody
    public RoleResult getStaffInfo(String token,HttpServletRequest request) throws IOException {
        Integer id = JWTUtil.getId(token);
        log.info("获取的解密后的id为："+id);
        String ip = super.getIpAddr(request);
        String userValue = super.getRedisValue(FrameConstant.ACTIVE_USERS_PREFIX + token + "." + ip);
        ActiveUser activeUser = mapper.readValue(userValue, ActiveUser.class);
        List<FrameRole> list = roleService.findRoleListByUserId(id);
        if(list!=null&&list.size()>0){
            String[] array = new String[list.size()];
            for (int i = 0;i<array.length;i++) {
                array[i] = list.get(i).getRoleName();
            }
            RoleResult roleResult = new RoleResult("获取权限角色成功",FrameConstant.AUTH_SUCCESS ,true,array,activeUser);

            return roleResult;
        }
        return new RoleResult("该用户无角色权限",FrameConstant.AUTH_SUCCESS ,true,null,null);
    }

    /**
     * 登出方法
     * @return
     */
    @PostMapping("/logout")
    @ResponseBody
    public ResponseResult logout(HttpServletRequest request) throws IOException {
        String token = request.getHeader(FrameConstant.LOGIN_SIGN);
        String ip = super.getIpAddr(request);
        log.info("获取的token："+token);
        log.info("登录的ip为："+ ip);
        //登出时删除redis中token和user的缓存
        boolean deleteToken = false;
        boolean deleteActiveUser = false;
        String username = JWTUtil.getUsername(token);
        if(StringUtils.isNotEmpty(username)){
            //保存登出日志
            logoutService.saveLogoutLog(username,ip);
        }
        if(StringUtils.isNotEmpty(token)){
            deleteToken = super.removeRedisValue(FrameConstant.TOKEN_PREFIX + token + "." + ip);
            deleteActiveUser = super.removeRedisValue(FrameConstant.ACTIVE_USERS_PREFIX + token + "." + ip);
        }
        log.info("登出成功");
        return new ResponseResult("登出成功",FrameConstant.LOGOUT_SUCCESS,true);
    }

    @ResponseBody
    @GetMapping("/page")
    public PageResult findUserList(@RequestParam(name = "search",defaultValue = "")String userName, @RequestParam(value = "limit") Integer pageSize, @RequestParam(value = "page")Integer pageNum){
        log.info("当前页为："+ pageNum);
        log.info("每页显示记录数："+ pageSize);
        log.info("搜索名为："+ userName);
        PageInfo pageInfo = userService.findUserList(userName, pageSize, pageNum);
        PageResult pageResult = new PageResult("查询成功", FrameConstant.REQUEST_SUCCESS,true,pageInfo);
        return pageResult;

    }

    @PostMapping("/stopAndUse")
    @ResponseBody
    public ResponseResult stopAndUseUser(@RequestBody FrameUser user){
        int i = userService.stopAndUseUser(user.getUserId(),user.getUserStatus());
        if(i>0){
            return new ResponseResult("操作成功",FrameConstant.REQUEST_SUCCESS,true);
        }else {
            throw new RuntimeException();
        }
    }
    @PostMapping("/update")
    @ResponseBody
    public ResponseResult update(@RequestBody FrameUser user,HttpServletRequest request){
        if(user.getRoleArray().length < 1){
            //控制用户下必须有权限角色
            throw new RuntimeException("用户下必须有权限角色");
        }
        String token = request.getHeader(FrameConstant.LOGIN_SIGN);
        String username = JWTUtil.getUsername(token);
        user.setUserUpdateName(username);
        user.setUserUpdate(new Date());
        //配置角色先删除对应角色再重新添加
        roleService.deleteRolesByUserId(user.getUserId());
        userService.updateUser(user);
        return new ResponseResult("操作成功",FrameConstant.REQUEST_SUCCESS,true);
    }
}
