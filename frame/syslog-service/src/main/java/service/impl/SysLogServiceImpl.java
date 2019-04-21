package service.impl;

import entity.SysLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.SysLogService;

@Service
@Slf4j
@Transactional
public class SysLogServiceImpl implements SysLogService{
    @Override
    public void save(SysLog sysLog) {

    }
}
