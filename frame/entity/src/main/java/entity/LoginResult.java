package entity;

public class LoginResult extends ResponseResult {

    private String token;
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public LoginResult(String message, Integer code, boolean success,String token,String username,String avatar) {
        super(message, code, success);
        this.token = token;
    }
}
