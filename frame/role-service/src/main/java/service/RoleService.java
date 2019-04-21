package service;

import entity.FrameRole;
import entity.PageInfo;

import java.util.List;

public interface RoleService {
    List<FrameRole> findRoleListByUserId(Integer userId);

    List<FrameRole> getRoles();

    void deleteRolesByUserId(Integer userId);

    PageInfo findRoleList(String roleName, Integer pageSize, Integer pageNum);

}
