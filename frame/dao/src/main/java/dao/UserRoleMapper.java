package dao;

import org.apache.ibatis.annotations.Param;

public interface UserRoleMapper {
    int deleteRolesByUserId(@Param("userId")Integer userId);

    int insertRoles(@Param("userId")Integer userId, @Param("roleId")Integer roleId);

}
