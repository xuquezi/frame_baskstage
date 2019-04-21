package com.example.admin.controller;

import annotation.SysLog;
import constant.FrameConstant;
import entity.PageInfo;
import entity.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.LoginService;
import service.LogoutService;
import service.SysLogService;

@Slf4j
@Controller
@RequestMapping("/frame/log")
public class LogController extends BaseController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private LogoutService logoutService;
    @Autowired
    private SysLogService sysLogService;

    @ResponseBody
    @RequestMapping("/login/page")
    @SysLog
    public PageResult findLoginLogList(@RequestParam(name = "search",defaultValue = "")String loginName, @RequestParam(value = "limit") Integer pageSize, @RequestParam(value = "page")Integer pageNum){
        log.info("当前页为："+ pageNum);
        log.info("每页显示记录数："+ pageSize);
        log.info("搜索名为："+ loginName);
        PageInfo pageInfo = loginService.findLoginLogList(loginName, pageSize, pageNum);
        PageResult pageResult = new PageResult("查询成功", FrameConstant.REQUEST_SUCCESS,true,pageInfo);
        return pageResult;
    }
    @ResponseBody
    @RequestMapping("/logout/page")
    @SysLog
    public PageResult findLogoutLogList(@RequestParam(name = "search",defaultValue = "")String logoutName, @RequestParam(value = "limit") Integer pageSize, @RequestParam(value = "page")Integer pageNum){
        log.info("当前页为："+ pageNum);
        log.info("每页显示记录数："+ pageSize);
        log.info("搜索名为："+ logoutName);
        PageInfo pageInfo = logoutService.findLogoutLogList(logoutName, pageSize, pageNum);
        PageResult pageResult = new PageResult("查询成功", FrameConstant.REQUEST_SUCCESS,true,pageInfo);
        return pageResult;
    }

    @ResponseBody
    @RequestMapping("/operate/page")
    @SysLog
    public PageResult findOperateLogList(@RequestParam(name = "search",defaultValue = "")String username, @RequestParam(value = "limit") Integer pageSize, @RequestParam(value = "page")Integer pageNum){
        log.info("当前页为："+ pageNum);
        log.info("每页显示记录数："+ pageSize);
        log.info("搜索名为："+ username);
        PageInfo pageInfo = sysLogService.findOperateLogList(username, pageSize, pageNum);
        PageResult pageResult = new PageResult("查询成功", FrameConstant.REQUEST_SUCCESS,true,pageInfo);
        return pageResult;
    }
}
