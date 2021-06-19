package org.daming.hoteler.pojo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.StringJoiner;


/**
 * 上周入住客户数量
 *
 * @author gming001
 * @create 2021-06-17 21:49
 **/
public class PastWeekCustomerCount implements Serializable {

    private static final long serialVersionUID = -4470376498211300322L;

    private LocalDate checkInDate;

    private int customerCount;

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public PastWeekCustomerCount setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
        return this;
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public PastWeekCustomerCount setCustomerCount(int customerCount) {
        this.customerCount = customerCount;
        return this;
    }

    public PastWeekCustomerCount(LocalDate checkInDate, int customerCount) {
        super();
        this.checkInDate = checkInDate;
        this.customerCount = customerCount;
    }

    public PastWeekCustomerCount() {
        super();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PastWeekCustomerCount.class.getSimpleName() + "[", "]")
                .add("checkInDate=" + checkInDate)
                .add("customerCount=" + customerCount)
                .toString();
    }
}
