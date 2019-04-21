package service;

import entity.FrameAuth;

import java.util.List;

public interface AuthService {
    List<FrameAuth> findAuthListByRoleId(Integer roleId);

    List<FrameAuth> getAuths();

}
