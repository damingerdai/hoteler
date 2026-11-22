package org.daming.hoteler.pojo.request;

import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class UpdateRoomGroupRequest {

    @Size(max = 100)
    private String name;

    private Integer sortOrder;

    private Boolean active;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public UpdateRoomGroupRequest() {
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UpdateRoomGroupRequest that)) return false;
        return Objects.equals(name, that.name) && Objects.equals(sortOrder, that.sortOrder) && Objects.equals(active, that.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, sortOrder, active);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("sortOrder", sortOrder)
                .append("active", active)
                .toString();
    }
}
