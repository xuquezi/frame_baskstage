package entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ActiveUser implements Serializable{
    private String ip;// ip地址
    private String username;// 用户名
    private String loginTime;// 登录时间
    private String token;// token
    private Integer id;
    private String password;
    private String avatar;

}
