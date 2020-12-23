package org.daming.hoteler.pojo.request;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * create room request
 *
 * @author gming001
 * @create 2020-12-22 23:51
 **/
public class CreateRoomRequest implements Serializable {

    private static final long serialVersionUID = 3097150561081198317L;

    private String roomname;

    public String getRoomname() {
        return roomname;
    }

    public CreateRoomRequest setRoomname(String roomname) {
        this.roomname = roomname;
        return this;
    }

    public CreateRoomRequest() {
        super();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CreateRoomRequest.class.getSimpleName() + "[", "]")
                .add("roomname='" + roomname + "'")
                .toString();
    }
}
