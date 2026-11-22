package org.daming.hoteler.pojo.request;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class AssignRoomGroupRequest {

    private String groupId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public AssignRoomGroupRequest() {
        super();
    }

    public AssignRoomGroupRequest(String groupId) {
        super();
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AssignRoomGroupRequest that)) return false;
        return Objects.equals(groupId, that.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(groupId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("groupId", groupId)
                .toString();
    }
}
