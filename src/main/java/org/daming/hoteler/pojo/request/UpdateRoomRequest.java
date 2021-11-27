package org.daming.hoteler.pojo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.daming.hoteler.pojo.enums.RoomStatus;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * update room request
 *
 * @author gming001
 * @create 2021-02-07 20:36
 **/
public class UpdateRoomRequest implements Serializable {

    private static final long serialVersionUID = -157882609205230197L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private long id;

    private String roomname;

    private double price;

    private RoomStatus status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    public UpdateRoomRequest() {
        super();
    }

    public UpdateRoomRequest(long id, String roomname, double price, RoomStatus status) {
        super();
        this.id = id;
        this.roomname = roomname;
        this.price = price;
        this.status = status;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UpdateRoomRequest.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("roomname='" + roomname + "'")
                .add("price=" + price)
                .add("status=" + status)
                .toString();
    }
}
