package entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class FrameUser implements Serializable {
    private Integer userId;

    @JsonProperty("username")
    private String userName;

    @JsonProperty("password")
    private String userPassword;

    private String userPic;

    private Integer userStatus;

    private String userEmail;

    private String userMobile;

    private Integer userDelete;

    private Date userCreate;

    private String userCreateName;

    private Date userUpdate;

    private String userUpdateName;

    private List<FrameUser> roles;

    //  用于接受前端传来的角色数组
    private Integer[] roleArray;

}
