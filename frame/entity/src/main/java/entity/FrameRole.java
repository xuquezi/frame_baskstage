package entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class FrameRole implements Serializable {
    private Integer roleId;

    private Integer roleDelete;

    private String roleDesc;

    private String roleName;

    private Date roleCreate;

    private String roleCreateName;

    private Date roleUpdate;

    private String roleUpdateName;

    private Integer roleStatus;

    private List<FrameAuth> auths;
}
