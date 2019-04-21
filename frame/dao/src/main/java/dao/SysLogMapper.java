package dao;

import entity.SysLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysLogMapper {

    int saveSysLog(SysLog sysLog);

    List<SysLog> selectOperateLogList(@Param("username") String username, @Param("start")Integer start, @Param("pageSize")Integer pageSize);

    Integer countOperateLogList(@Param("username")String username);
}
