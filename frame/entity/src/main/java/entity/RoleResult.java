package entity;

public class RoleResult extends ResponseResult {
    private String[] roles;

    private ActiveUser activeUser;

    public ActiveUser getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(ActiveUser activeUser) {
        this.activeUser = activeUser;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public RoleResult(String message, Integer code, boolean success, String[] array,ActiveUser activeUser) {
        super(message, code, success);
        this.roles = array;
        this.activeUser = activeUser;
    }
}
