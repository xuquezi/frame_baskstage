package com.example.admin.config.exception;

import org.apache.shiro.authc.AuthenticationException;

public class NotActivatedException extends AuthenticationException {
    private String msg;

    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public NotActivatedException() {
    }
    public NotActivatedException(String msg) {
        this.msg = msg;
    }
}
