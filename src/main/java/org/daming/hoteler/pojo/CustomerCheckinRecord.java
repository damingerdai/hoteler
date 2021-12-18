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
public class CustomerCheckinRecord implements Serializable {

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

    public CustomerCheckinRecord setId(long id) {
        this.id = id;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public CustomerCheckinRecord setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public long getRoomId() {
        return roomId;
    }

    public CustomerCheckinRecord setRoomId(long roomId) {
        this.roomId = roomId;
        return this;
    }

    public LocalDateTime getBeginDate() {
        return beginDate;
    }

    public CustomerCheckinRecord setBeginDate(LocalDateTime beginDate) {
        this.beginDate = beginDate;
        return this;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public CustomerCheckinRecord setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public CustomerCheckinRecord() {
        super();
    }

    public CustomerCheckinRecord(long id, long userId, long roomId, LocalDateTime beginDate, LocalDateTime endDate) {
        super();
        this.id = id;
        this.userId = userId;
        this.roomId = roomId;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CustomerCheckinRecord.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("userId=" + userId)
                .add("roomId=" + roomId)
                .add("beginDate=" + beginDate)
                .add("endDate=" + endDate)
                .toString();
    }
}
