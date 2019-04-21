package service.impl;

import dao.SysLogMapper;
import entity.PageInfo;
import entity.SysLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.SysLogService;

import java.util.List;

@Service
@Slf4j
@Transactional
public class SysLogServiceImpl implements SysLogService{
    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public void save(SysLog sysLog) {
        sysLogMapper.saveSysLog(sysLog);
    }

    @Override
    public PageInfo findOperateLogList(String username, Integer pageSize, Integer pageNum) {
        Integer start = (pageNum-1)*pageSize;
        List<SysLog> list = sysLogMapper.selectOperateLogList(username, start, pageSize);
        Integer total = sysLogMapper.countOperateLogList(username);
        return new PageInfo(total,list);
    }
}
