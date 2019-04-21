package service.impl;

import dao.LoginMapper;
import entity.FrameLogin;
import entity.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.LoginService;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@Transactional
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;
    @Override
    public void saveLoginLog(String userName, String ip) {
        FrameLogin frameLogin = new FrameLogin();
        frameLogin.setLoginIp(ip);
        frameLogin.setLoginTime(new Date());
        frameLogin.setLoginName(userName);
        int i = loginMapper.saveLoginLog(frameLogin);
    }

    /**
     * 分页查询登录日志数据
     * @param loginName
     * @param pageSize
     * @param pageNum
     * @return
     */
    @Override
    public PageInfo findLoginLogList(String loginName, Integer pageSize, Integer pageNum) {
        Integer start = (pageNum-1)*pageSize;
        List<FrameLogin> list = loginMapper.selectLoginLogList(loginName, start, pageSize);
        Integer total = loginMapper.countLoginLogList(loginName);
        return new PageInfo(total,list);
    }
}
