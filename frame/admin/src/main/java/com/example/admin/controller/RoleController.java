package com.example.admin.controller;

import constant.FrameConstant;
import entity.FrameRole;
import entity.ListResult;
import entity.PageInfo;
import entity.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.RoleService;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/frame/role")
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;

    /**
     * 获取所有角色
     */
    @GetMapping("/list")
    @ResponseBody
    public ListResult getRoles(){

        List<FrameRole> list = roleService.getRoles();
        return new ListResult("查询成功", FrameConstant.REQUEST_SUCCESS,true,list);
    }


    @ResponseBody
    @GetMapping("/page")
    public PageResult findRoleList(@RequestParam(name = "search",defaultValue = "")String roleName, @RequestParam(value = "limit") Integer pageSize, @RequestParam(value = "page")Integer pageNum){
        log.info("当前页为："+ pageNum);
        log.info("每页显示记录数："+ pageSize);
        log.info("搜索名为："+ roleName);
        PageInfo pageInfo = roleService.findRoleList(roleName, pageSize, pageNum);
        PageResult pageResult = new PageResult("查询成功", FrameConstant.REQUEST_SUCCESS,true,pageInfo);
        return pageResult;
    }
}
