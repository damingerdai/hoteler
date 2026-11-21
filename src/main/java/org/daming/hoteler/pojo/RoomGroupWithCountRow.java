package org.daming.hoteler.pojo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.daming.hoteler.pojo.dto.RoomGroupRow;

import java.time.LocalDateTime;
import java.util.Objects;

public class RoomGroupWithCountRow extends RoomGroupRow {

    private long roomCount;

    public long getRoomCount() {
        return roomCount;
    }

    public RoomGroupWithCountRow setRoomCount(long roomCount) {
        this.roomCount = roomCount;
        return this;
    }

    public RoomGroupWithCountRow() {
    }

    public RoomGroupWithCountRow(long roomCount) {
        this.roomCount = roomCount;
    }

    public RoomGroupWithCountRow(String id, String type, String code, String name, int sortOrder, boolean active, LocalDateTime createDt, String createUser, LocalDateTime updateDt, String updateUser, LocalDateTime deletedAt, long roomCount) {
        super(id, type, code, name, sortOrder, active, createDt, createUser, updateDt, updateUser, deletedAt);
        this.roomCount = roomCount;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RoomGroupWithCountRow that)) return false;
        if (!super.equals(o)) return false;
        return roomCount == that.roomCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), roomCount);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("roomCount", roomCount)
                .toString();
    }
}
