package service;

import entity.FrameUser;
import entity.PageInfo;

public interface UserService {
    FrameUser findUserByUsername(String username);

    PageInfo findUserList(String userName, Integer pageSize, Integer pageNum);

    int stopAndUseUser(Integer userId, Integer userStatus);

    Integer updateUser(FrameUser user);

}
