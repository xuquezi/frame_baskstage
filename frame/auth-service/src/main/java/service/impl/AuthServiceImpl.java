package service.impl;

import dao.AuthMapper;
import entity.FrameAuth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.AuthService;

import java.util.List;

@Service
@Slf4j
@Transactional
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthMapper authMapper;
    @Override
    public List<FrameAuth> findAuthListByRoleId(Integer roleId) {
        return authMapper.findAuthListByRoleId(roleId);
    }

    @Override
    public List<FrameAuth> getAuths() {
        return authMapper.getAuths();
    }
}
