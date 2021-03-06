package org.daming.hoteler.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.StringJoiner;

/**
 * UserRoom
 *
 * @author gming001
 * @create 2021-03-05 15:39
 **/
public class UserRoom  implements Serializable {

    private static final long serialVersionUID = -314732081658919026L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private long userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private long roomId;

    private LocalDateTime beginDate;

    private LocalDateTime endDate;

    public long getId() {
        return id;
    }

    public UserRoom setId(long id) {
        this.id = id;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public UserRoom setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public long getRoomId() {
        return roomId;
    }

    public UserRoom setRoomId(long roomId) {
        this.roomId = roomId;
        return this;
    }

    public LocalDateTime getBeginDate() {
        return beginDate;
    }

    public UserRoom setBeginDate(LocalDateTime beginDate) {
        this.beginDate = beginDate;
        return this;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public UserRoom setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public UserRoom() {
        super();
    }

    public UserRoom(long id, long userId, long roomId, LocalDateTime beginDate, LocalDateTime endDate) {
        super();
        this.id = id;
        this.userId = userId;
        this.roomId = roomId;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserRoom.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("userId=" + userId)
                .add("roomId=" + roomId)
                .add("beginDate=" + beginDate)
                .add("endDate=" + endDate)
                .toString();
    }
}
