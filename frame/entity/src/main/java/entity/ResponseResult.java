package entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ResponseResult implements Serializable {
    private String message;
    private Integer code;
    private boolean isSuccess;

    public ResponseResult() {
    }

    public ResponseResult(String message, Integer code, boolean isSuccess) {
        this.message = message;
        this.code = code;
        this.isSuccess = isSuccess;
    }
}
