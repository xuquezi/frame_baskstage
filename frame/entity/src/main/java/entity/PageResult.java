package entity;

public class PageResult extends ResponseResult {
    private PageInfo pageInfo;


    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public PageResult(String message, Integer code, boolean success,PageInfo pageInfo) {
        super(message, code, success);
        this.pageInfo = pageInfo;
    }
}
