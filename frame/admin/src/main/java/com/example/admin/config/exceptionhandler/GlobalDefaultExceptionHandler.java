package com.example.admin.config.exceptionhandler;

import constant.FrameConstant;
import entity.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice// @ResponseBody+@ControllerAdvice
//需要被@SpringBootApplication扫描
public class GlobalDefaultExceptionHandler {


    /**
     * 拦截AuthenticationException
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseResult shiroExceptionHandler(AuthenticationException e)
    {
        log.error("登录权限异常，异常信息：", e);
        String simpleName = e.getClass().getSimpleName();//获得异常名称
        if("UnknownAccountException".equals(simpleName)){
            return new ResponseResult("用户名不存在", FrameConstant.LOGIN_FAIL,false);
        }else if("IncorrectCredentialsException".equals(simpleName)){
            return new ResponseResult("密码不正确", FrameConstant.LOGIN_FAIL,false);
        }else if("NotActivatedException".equals(simpleName)){
            return new ResponseResult("账户未激活", FrameConstant.LOGIN_FAIL,false);
        }
        return new ResponseResult("获取登录权限失败", FrameConstant.LOGIN_FAIL,false);
    }

    // 捕捉运行时所有异常
    @ExceptionHandler(RuntimeException.class)
    public ResponseResult RuntimeExceptionHandler(RuntimeException e){
        log.error("系统内部异常，异常信息：", e);
        log.error(e.getMessage());
        return new ResponseResult(e.getMessage(), FrameConstant.REQUEST_FAIL,false);
    }

    // 捕捉其他所有异常
    @ExceptionHandler(Exception.class)
    public ResponseResult globalExceptionHandler(Exception e){
        log.error("系统内部异常，异常信息：", e);
        return new ResponseResult("系统内部异常,请求失败", FrameConstant.REQUEST_FAIL,false);
    }

}
