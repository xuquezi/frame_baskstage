package com.example.admin.controller;

import annotation.SysLog;
import constant.FrameConstant;
import entity.FrameAuth;
import entity.ListResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.AuthService;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/frame/auth")
public class AuthController extends BaseController{
    @Autowired
    private AuthService authService;

    /**
     * 获取所有权限
     */
    @RequestMapping("/list")
    @ResponseBody
    @SysLog
    public ListResult getRoles(){

        List<FrameAuth> list = authService.getAuths();
        return new ListResult("查询成功", FrameConstant.REQUEST_SUCCESS,true,list);
    }
}
