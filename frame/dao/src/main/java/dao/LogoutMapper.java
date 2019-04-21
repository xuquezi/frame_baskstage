package dao;

import entity.FrameLogin;
import entity.FrameLogout;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LogoutMapper {

    int saveLogoutLog(FrameLogout frameLogout);

    List<FrameLogin> selectLogoutLogList(@Param("logoutName")String logoutName, @Param("start")Integer start, @Param("pageSize")Integer pageSize);

    Integer countLogoutLogList(@Param("logoutName")String logoutName);
}
