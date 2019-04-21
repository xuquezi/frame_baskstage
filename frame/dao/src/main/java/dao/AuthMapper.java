package dao;

import entity.FrameAuth;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthMapper {
    List<FrameAuth> findAuthListByRoleId(@Param("roleId") Integer roleId);

    List<FrameAuth> getAuths();

}
