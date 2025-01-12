package org.daming.hoteler.pojo.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.daming.hoteler.pojo.Customer;
import org.daming.hoteler.pojo.Order;
import org.daming.hoteler.pojo.Room;

public class OrderVO extends Order {

    private Customer customer;

    private Room room;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public OrderVO() {
        super();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("customer", customer)
                .append("room", room)
                .toString();
    }
}
