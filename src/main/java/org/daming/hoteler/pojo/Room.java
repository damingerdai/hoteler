package org.daming.hoteler.pojo;

import org.daming.hoteler.pojo.enums.RoomStatus;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * room pojo
 *
 * @author gming001
 * @create 2020-12-22 22:36
 **/
public class Room implements Serializable {

    private static final long serialVersionUID = 5964557657365714040L;

    private long id;

    private String roomname;

    private RoomStatus status;

    public double price;

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

    public Room(long id, String roomname, RoomStatus status, double price) {
        super();
        this.id = id;
        this.roomname = roomname;
        this.status = status;
        this.price = price;
    }

    public Room() {
        super();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Room.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("roomname='" + roomname + "'")
                .add("status=" + status)
                .toString();
    }
}
