package org.daming.hoteler.pojo.request;

public class OrderListRequest {

    private Integer page;

    private Integer pageSize;

    private String sort;

    private String sortType;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public OrderListRequest(Integer page, Integer pageSize, String sort, String sortType) {
        this.page = page;
        this.pageSize = pageSize;
        this.sort = sort;
        this.sortType = sortType;
    }

    public OrderListRequest() {
        super();
    }

    @Override
    public String toString() {
        return "OrderListRequest{" +
                "page=" + page +
                ", pageSize=" + pageSize +
                ", sort='" + sort + '\'' +
                ", sortType='" + sortType + '\'' +
                '}';
    }
}
