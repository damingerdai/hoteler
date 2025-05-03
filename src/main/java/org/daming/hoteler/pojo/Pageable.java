package org.daming.hoteler.pojo;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class Pageable {

    private Integer pageNo;

    private Integer pageSize;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Pageable() {
    }

    public Pageable(Integer pageNo, Integer pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public Integer toLimit() {
        return this.pageSize;
    }

    public Integer toOffset() {
        return (this.pageNo - 1) * this.pageSize;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pageable pageable)) return false;
        return Objects.equals(pageNo, pageable.pageNo) && Objects.equals(pageSize, pageable.pageSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageNo, pageSize);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("pageNo", pageNo)
                .append("pageSize", pageSize)
                .toString();
    }
}
