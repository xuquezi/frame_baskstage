package service.impl;

import dao.RoleMapper;
import dao.UserMapper;
import dao.UserRoleMapper;
import entity.FrameUser;
import entity.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.UserService;

import java.util.List;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 通过用户名查找用户 用户名需保持唯一
     * @param username
     * @return
     */
    @Override
    public FrameUser findUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }

    /**
     * 分页查询用户数据
     * @param userName
     * @param pageSize
     * @param pageNum
     * @return
     */
    @Override
    public PageInfo findUserList(String userName, Integer pageSize, Integer pageNum) {
        Integer start = (pageNum-1)*pageSize;
        List<FrameUser> list = userMapper.selectUserList(userName, start, pageSize);
        Integer total = userMapper.countUserList(userName);
        return new PageInfo(total,list);
    }

    @Override
    public int stopAndUseUser(Integer userId, Integer userStatus) {
        int i ;
        if(userStatus == 0){
            //传过来状态为0,启用状态，进行停用操作
            i = userMapper.stopUser(userId,userStatus);
        }else {
            //启用用户时判断用户下角色有没有停用或者删除的。
            //如果角色停用或者删除需要先恢复或者启用用户下角色
            //且判断用户下角色不为空
            int count = roleMapper.findRoleIsDeleteOrStopByUserId(userId);
            if(count>0){
                throw new RuntimeException("用户下有停用或者删除角色，要先恢复或者启用用户下角色");
            }
            //判断用户下角色不为空,为空的话抛出异常
            int countUserRole = roleMapper.countUserRoleByUserId(userId);
            if(countUserRole <= 0){
                throw new RuntimeException("该用户下没有角色，请先配置角色权限");
            }
            i = userMapper.useUser(userId,userStatus);


        }
        return i;
    }

    @Override
    public Integer updateUser(FrameUser user) {
        int i = userMapper.updateUser(user);
        int j= 0;
        for (Integer roleId : user.getRoleArray()) {
            int k = userRoleMapper.insertRoles(user.getUserId(),roleId);
            j += k;
        }
        return i + j;
    }
}
