package org.daming.hoteler.pojo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.daming.hoteler.pojo.enums.RoomGroupType;

import java.util.Objects;

public class RoomGroup {

    private String id;

    private RoomGroupType type;

    private String code;

    private String name;

    private int sortOrder;

    private boolean active;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RoomGroupType getType() {
        return type;
    }

    public void setType(RoomGroupType type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public RoomGroup() {
        super();
    }

    public RoomGroup(String id, RoomGroupType type, String code, String name, int sortOrder, boolean active) {
        this.id = id;
        this.type = type;
        this.code = code;
        this.name = name;
        this.sortOrder = sortOrder;
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RoomGroup roomGroup)) return false;
        return sortOrder == roomGroup.sortOrder && active == roomGroup.active && Objects.equals(id, roomGroup.id) && type == roomGroup.type && Objects.equals(code, roomGroup.code) && Objects.equals(name, roomGroup.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, code, name, sortOrder, active);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("type", type)
                .append("code", code)
                .append("name", name)
                .append("sortOrder", sortOrder)
                .append("active", active)
                .toString();
    }
}
