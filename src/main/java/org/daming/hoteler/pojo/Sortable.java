package org.daming.hoteler.pojo;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class Sortable {

    private String orderBy;

    private String orderDir;

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderDir() {
        return orderDir;
    }

    public void setOrderDir(String orderDir) {
        this.orderDir = orderDir;
    }

    public Sortable() {
    }

    public Sortable(String orderBy, String orderDir) {
        this.orderBy = orderBy;
        this.orderDir = orderDir;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Sortable sortable)) return false;
        return Objects.equals(orderBy, sortable.orderBy) && Objects.equals(orderDir, sortable.orderDir);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderBy, orderDir);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("orderBy", orderBy)
                .append("orderDir", orderDir)
                .toString();
    }
}
