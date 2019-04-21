package service.impl;

import dao.LogoutMapper;
import entity.FrameLogin;
import entity.FrameLogout;
import entity.PageInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.LogoutService;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@Transactional
public class LogoutServiceImpl implements LogoutService {
    @Autowired
    private LogoutMapper logoutMapper;
    @Override
    public void saveLogoutLog(String username, String ip) {
        FrameLogout frameLogout = new FrameLogout();
        frameLogout.setLogoutIp(ip);
        frameLogout.setLogoutName(username);
        frameLogout.setLogoutTime(new Date());
        int i = logoutMapper.saveLogoutLog(frameLogout);
    }

    @Override
    public PageInfo findLogoutLogList(String logoutName, Integer pageSize, Integer pageNum) {
        Integer start = (pageNum-1)*pageSize;
        List<FrameLogin> list = logoutMapper.selectLogoutLogList(logoutName, start, pageSize);
        Integer total = logoutMapper.countLogoutLogList(logoutName);
        return new PageInfo(total,list);
    }
}
