package org.daming.hoteler.pojo.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;
import java.util.Objects;

public class RoomGroupRow {

    private String id;

    private String type;

    private String code;

    private String name;

    private int sortOrder;

    private boolean active;

    private LocalDateTime createDt;

    private String createUser;

    private LocalDateTime updateDt;

    private String updateUser;

    private LocalDateTime deletedAt;

    public String getId() {
        return id;
    }

    public RoomGroupRow setId(String id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return type;
    }

    public RoomGroupRow setType(String type) {
        this.type = type;
        return this;
    }

    public String getCode() {
        return code;
    }

    public RoomGroupRow setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public RoomGroupRow setName(String name) {
        this.name = name;
        return this;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public RoomGroupRow setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public RoomGroupRow setActive(boolean active) {
        this.active = active;
        return this;
    }

    public LocalDateTime getCreateDt() {
        return createDt;
    }

    public RoomGroupRow setCreateDt(LocalDateTime createDt) {
        this.createDt = createDt;
        return this;
    }

    public String getCreateUser() {
        return createUser;
    }

    public RoomGroupRow setCreateUser(String createUser) {
        this.createUser = createUser;
        return this;
    }

    public LocalDateTime getUpdateDt() {
        return updateDt;
    }

    public RoomGroupRow setUpdateDt(LocalDateTime updateDt) {
        this.updateDt = updateDt;
        return this;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public RoomGroupRow setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public RoomGroupRow setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public RoomGroupRow() {
        super();
    }

    public RoomGroupRow(String id, String type, String code, String name, int sortOrder, boolean active, LocalDateTime createDt, String createUser, LocalDateTime updateDt, String updateUser, LocalDateTime deletedAt) {
        super();
        this.id = id;
        this.type = type;
        this.code = code;
        this.name = name;
        this.sortOrder = sortOrder;
        this.active = active;
        this.createDt = createDt;
        this.createUser = createUser;
        this.updateDt = updateDt;
        this.updateUser = updateUser;
        this.deletedAt = deletedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RoomGroupRow that)) return false;
        return sortOrder == that.sortOrder && active == that.active && Objects.equals(id, that.id) && Objects.equals(type, that.type) && Objects.equals(code, that.code) && Objects.equals(name, that.name) && Objects.equals(createDt, that.createDt) && Objects.equals(createUser, that.createUser) && Objects.equals(updateDt, that.updateDt) && Objects.equals(updateUser, that.updateUser) && Objects.equals(deletedAt, that.deletedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, code, name, sortOrder, active, createDt, createUser, updateDt, updateUser, deletedAt);
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
                .append("createDt", createDt)
                .append("createUser", createUser)
                .append("updateDt", updateDt)
                .append("updateUser", updateUser)
                .append("deletedAt", deletedAt)
                .toString();
    }
}
