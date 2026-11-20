package org.daming.hoteler.pojo.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.daming.hoteler.pojo.enums.RoomGroupType;

import java.util.Objects;

public class CreateRoomGroupRequest {

    @NotNull
    private String type;

    @NotBlank
    private String code;

    @NotBlank
    @Size(max = 100)
    private String name;

    private Integer sortOrder;

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public CreateRoomGroupRequest() {
        super();
    }

    public CreateRoomGroupRequest(String type, String code, String name, Integer sortOrder) {
        super();
        this.type = type;
        this.code = code;
        this.name = name;
        this.sortOrder = sortOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CreateRoomGroupRequest that)) return false;
        return Objects.equals(type, that.type) && Objects.equals(code, that.code) && Objects.equals(name, that.name) && Objects.equals(sortOrder, that.sortOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, code, name, sortOrder);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("type", type)
                .append("code", code)
                .append("name", name)
                .append("sortOrder", sortOrder)
                .toString();
    }
}
