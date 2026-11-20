package org.daming.hoteler.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.daming.hoteler.pojo.enums.RoomStatus;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * room pojo
 *
 * @author gming001
 * @create 2020-12-22 22:36
 **/
public class Room implements Serializable {

    @Serial
    private static final long serialVersionUID = 5964557657365714040L;

    // 该注解是必须的， long类型前端会有精度损失
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private long id;

    private String roomname;

    private RoomStatus status;

    private double price;

    private String groupId;

    public long getId() {
        return id;
    }

    public Room setId(long id) {
        this.id = id;
        return this;
    }

    public String getRoomname() {
        return roomname;
    }

    public Room setRoomname(String roomname) {
        this.roomname = roomname;
        return this;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public Room setStatus(RoomStatus status) {
        this.status = status;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Room setPrice(double price) {
        this.price = price;
        return this;
    }

    public String getGroupId() {
        return groupId;
    }

    public Room setGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public Room() {
        super();
    }

    public Room(long id, String roomname, RoomStatus status, double price, String groupId) {
        super();
        this.id = id;
        this.roomname = roomname;
        this.status = status;
        this.price = price;
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Room room)) return false;
        return id == room.id && Double.compare(price, room.price) == 0 && Objects.equals(roomname, room.roomname) && status == room.status && Objects.equals(groupId, room.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomname, status, price, groupId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("roomname", roomname)
                .append("status", status)
                .append("price", price)
                .append("groupId", groupId)
                .toString();
    }
}
