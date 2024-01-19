package org.daming.hoteler.pojo.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.StringJoiner;

/**
 * 更新用户与房间的关联
 *
 * @author gming001
 * @create 2021-03-06 23:40
 **/
public class UpdateCustomerCheckinRecordRequest implements Serializable {

    private static final long serialVersionUID = 6530878491216149931L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private long customerId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private long roomId;

    private LocalDateTime beginDate;

    private LocalDateTime endDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
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

    public UpdateCustomerCheckinRecordRequest() {
        super();
    }

    public UpdateCustomerCheckinRecordRequest(long id, long customerId, long roomId, LocalDateTime beginDate, LocalDateTime endDate) {
        super();
        this.id = id;
        this.customerId = customerId;
        this.roomId = roomId;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UpdateCustomerCheckinRecordRequest.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("customerId=" + customerId)
                .add("roomId=" + roomId)
                .add("beginDate=" + beginDate)
                .add("endDate=" + endDate)
                .toString();
    }
}
