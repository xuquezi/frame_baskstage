package dao;

import entity.FrameLogin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LoginMapper {
    int saveLoginLog(FrameLogin frameLogin);

    List<FrameLogin> selectLoginLogList(@Param("loginName") String loginName, @Param("start")Integer start, @Param("pageSize")Integer pageSize);

    Integer countLoginLogList(@Param("loginName") String loginName);

}
