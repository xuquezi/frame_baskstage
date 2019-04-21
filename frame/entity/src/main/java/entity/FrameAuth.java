package entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class FrameAuth implements Serializable{
    private Integer authId;

    private String authDesc;

    private String authName;

    private Integer authDelete;

    private Date authCreate;

    private String authCreateName;

    private Date authUpdate;

    private String authUpdateName;

    private Integer authStatus;
}
