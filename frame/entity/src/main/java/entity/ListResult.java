package entity;

import java.util.List;

public class ListResult extends ResponseResult {
    private List list;

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public ListResult(String message, Integer code, boolean isSuccess, List list) {
        super(message, code, isSuccess);
        this.list = list;
    }
}
