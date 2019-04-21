package entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class FrameUserRole implements Serializable {

    private Integer userRoleId;

    private Integer userId;

    private Integer roleId;
}
