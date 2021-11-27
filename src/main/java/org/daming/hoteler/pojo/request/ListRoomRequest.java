package org.daming.hoteler.pojo.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.daming.hoteler.pojo.enums.RoomStatus;
import org.daming.hoteler.pojo.enums.deserializer.JacksonRoomStatusDeserializer;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * List Room Request
 *
 * @author gming001
 * @create 2021-03-15 14:57
 **/
public class ListRoomRequest implements Serializable {
    private static final long serialVersionUID = -182082371339657685L;

    @JsonDeserialize(using = JacksonRoomStatusDeserializer.class)
    private RoomStatus status;

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    public ListRoomRequest() {
        super();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ListRoomRequest.class.getSimpleName() + "[", "]")
                .add("status=" + status)
                .toString();
    }
}
