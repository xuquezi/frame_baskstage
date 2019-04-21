package service;

import entity.PageInfo;

public interface LogoutService {
    void saveLogoutLog(String username, String ip);

    PageInfo findLogoutLogList(String logoutName, Integer pageSize, Integer pageNum);

}
