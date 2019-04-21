package service;

import entity.PageInfo;
import entity.SysLog;

public interface SysLogService {
    void save(SysLog sysLog);

    PageInfo findOperateLogList(String username, Integer pageSize, Integer pageNum);

}
