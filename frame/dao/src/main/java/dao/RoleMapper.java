package dao;

import entity.FrameRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    List<FrameRole> findRoleListByUserId(@Param("userId") Integer userId);

    List<FrameRole> getRoles();

    int findRoleIsDeleteOrStopByUserId(@Param("userId")Integer userId);

    int countUserRoleByUserId(@Param("userId")Integer userId);

    List<FrameRole> selectRoleList(@Param("roleName") String roleName, @Param("start")Integer start, @Param("pageSize")Integer pageSize);

    Integer countRoleList(@Param("roleName") String roleName);

}
