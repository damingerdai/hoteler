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
public class Order implements Serializable {

    private static final long serialVersionUID = -314732081658919026L;

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

    public Order setId(long id) {
        this.id = id;
        return this;
    }

    public long getCustomerId() {
        return customerId;
    }

    public Order setCustomerId(long customerId) {
        this.customerId = customerId;
        return this;
    }

    public long getRoomId() {
        return roomId;
    }

    public Order setRoomId(long roomId) {
        this.roomId = roomId;
        return this;
    }

    public LocalDateTime getBeginDate() {
        return beginDate;
    }

    public Order setBeginDate(LocalDateTime beginDate) {
        this.beginDate = beginDate;
        return this;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Order setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public Order() {
        super();
    }

    public Order(long id, long customerId, long roomId, LocalDateTime beginDate, LocalDateTime endDate) {
        super();
        this.id = id;
        this.customerId = customerId;
        this.roomId = roomId;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Order.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("customerId=" + customerId)
                .add("roomId=" + roomId)
                .add("beginDate=" + beginDate)
                .add("endDate=" + endDate)
                .toString();
    }
}
