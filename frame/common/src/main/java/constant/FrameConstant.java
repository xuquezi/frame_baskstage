package constant;

public class FrameConstant {
    //登录失败
    public static final Integer LOGIN_FAIL= 20001;
    //验证登录权限失败
    public static final Integer AUTHENTICATION_FAIL= 30001;
    //登录成功
    public static final Integer LOGIN_SUCCESS = 20000;
    //请求失败
    public static final Integer REQUEST_FAIL = 10001;
    //请求成功
    public static final Integer REQUEST_SUCCESS = 10000;
    //权限认证成功
    public static final Integer AUTH_SUCCESS = 40000;
    //登出成功
    public static final Integer LOGOUT_SUCCESS = 20000;
    //TOKEN前缀
    public static final String TOKEN_PREFIX = "frame.token";
    // 存储在线用户的前缀
    public static final String ACTIVE_USERS_PREFIX = "frame.user.active";
    // 登录标识
    public static final String LOGIN_SIGN = "Token";



}
