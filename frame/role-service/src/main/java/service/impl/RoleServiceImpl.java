package service.impl;

import dao.RoleMapper;
import dao.UserRoleMapper;
import entity.FrameRole;
import entity.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.RoleService;

import java.util.List;

@Slf4j
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Override
    public List<FrameRole> findRoleListByUserId(Integer userId) {
        return roleMapper.findRoleListByUserId(userId);
    }

    @Override
    public List<FrameRole> getRoles() {
        return roleMapper.getRoles();
    }

    @Override
    public void deleteRolesByUserId(Integer userId) {
        userRoleMapper.deleteRolesByUserId(userId);
    }

    /**
     * 分页查询用户数据
     * @param roleName
     * @param pageSize
     * @param pageNum
     * @return
     */
    @Override
    public PageInfo findRoleList(String roleName, Integer pageSize, Integer pageNum) {
        Integer start = (pageNum-1)*pageSize;
        List<FrameRole> list = roleMapper.selectRoleList(roleName, start, pageSize);
        Integer total = roleMapper.countRoleList(roleName);
        return new PageInfo(total,list);
    }
}
