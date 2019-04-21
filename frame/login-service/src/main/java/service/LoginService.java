package service;

import entity.PageInfo;

public interface LoginService {
    void saveLoginLog(String userName, String ip);

    PageInfo findLoginLogList(String loginName, Integer pageSize, Integer pageNum);

}
