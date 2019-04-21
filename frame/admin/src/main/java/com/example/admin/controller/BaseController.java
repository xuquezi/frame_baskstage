package com.example.admin.controller;

import entity.ActiveUser;
import entity.FrameUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
public class BaseController {
    @Autowired
    private StringRedisTemplate redisTemplate;
    /**
     * 加密密码
     * @return
     */
    public String encryptPassword(String password){
        String hashAlgorithmName = "MD5";
        int hashIterations = 1;
        //干扰数据 盐 防破解
        String salt = "mar%#$@";
        Object obj = new SimpleHash(hashAlgorithmName, password,salt, hashIterations);
        return obj.toString();
    }


    public  void setRedisKey(String key,String value,Long timeout){
        log.info("存入redis键为"+key);
        log.info("存入redis值为"+value);
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(key,value,timeout, TimeUnit.MINUTES);//5分钟过期
    }

    public String getRedisValue(String key){
        ValueOperations<String, String> ops = this.redisTemplate.opsForValue();
        String value = ops.get(key);
        log.info("从redis中获取值为："+value);
        return value;
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

    /**
     * 生成在线用户存入redis
     * @param user
     * @param token
     * @param ip
     * @return
     */
    public ActiveUser generateActiveUser(FrameUser user,String token,String ip){
        ActiveUser activeUser = new ActiveUser();
        activeUser.setId(user.getUserId());
        activeUser.setIp(ip);
        activeUser.setUsername(user.getUserName());
        activeUser.setToken(token);
        activeUser.setPassword(user.getUserPassword());
        activeUser.setAvatar(user.getUserPic());
        DateFormat bf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        activeUser.setLoginTime(bf.format(new Date()));
        return activeUser;
    }

    public boolean removeRedisValue(String key){
        Boolean delete = redisTemplate.delete(key);
        log.info("从redis中删除，对应键为：" + key);
        return delete;
    }
}
