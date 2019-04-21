package dao;

import entity.FrameUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    /**
     * 通过用户名查找用户 用户名需保持唯一
     * @param username
     * @return
     */
    FrameUser findUserByUsername(@Param("username") String username);

    List<FrameUser> selectUserList(@Param("userName") String userName, @Param("start")Integer start, @Param("pageSize")Integer pageSize);

    Integer countUserList(@Param("userName") String userName);

    int stopUser(@Param("userId")Integer userId,@Param("userStatus") Integer userStatus);

    int useUser(@Param("userId")Integer userId, @Param("userStatus")Integer userStatus);

    int updateUser(FrameUser user);

}
