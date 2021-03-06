package org.daming.hoteler.pojo.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.StringJoiner;

/**
 * 创建用户与房间之间的关联
 *
 * @author gming001
 * @create 2021-03-04 16:29
 **/
public class CreateUserRoomRequest implements Serializable {
    private static final long serialVersionUID = 3021114417834745800L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private long userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private long roomId;

    private LocalDateTime beginDate;

    private LocalDateTime endDate;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public LocalDateTime getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDateTime beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public CreateUserRoomRequest() {
        super();
    }

    public CreateUserRoomRequest(long userId, long roomId, LocalDateTime beginDate, LocalDateTime endDate) {
        super();
        this.userId = userId;
        this.roomId = roomId;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CreateUserRoomRequest.class.getSimpleName() + "[", "]")
                .add("userId=" + userId)
                .add("roomId=" + roomId)
                .add("beginDate=" + beginDate)
                .add("endDate=" + endDate)
                .toString();
    }
}
