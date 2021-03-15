package org.daming.hoteler.pojo.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import org.daming.hoteler.pojo.enums.RoomStatus;
import org.daming.hoteler.pojo.enums.deserializer.JacksonRoomStatusDeserializer;
import org.springframework.web.bind.annotation.RequestParam;

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

    @ApiModelProperty(name = "room status", notes = "status for room", dataType="Integer")
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
