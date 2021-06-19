package org.daming.hoteler.pojo.stat;

import org.daming.hoteler.pojo.PastWeekCustomerCount;

import java.io.Serializable;
import java.util.List;
import java.util.StringJoiner;

/**
 * 上周入住客户数量统计
 *
 * @author gming001
 * @create 2021-06-17 21:38
 **/
public class PastWeekCustomerCountStat implements Serializable {

    private static final long serialVersionUID = -8946779993838392239L;

    List<PastWeekCustomerCount> pastWeekCustomerCounts;

    public List<PastWeekCustomerCount> getPastWeekCustomerCounts() {
        return pastWeekCustomerCounts;
    }

    public PastWeekCustomerCountStat setPastWeekCustomerCounts(List<PastWeekCustomerCount> pastWeekCustomerCounts) {
        this.pastWeekCustomerCounts = pastWeekCustomerCounts;
        return this;
    }

    public PastWeekCustomerCountStat(List<PastWeekCustomerCount> pastWeekCustomerCounts) {
        this.pastWeekCustomerCounts = pastWeekCustomerCounts;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PastWeekCustomerCountStat.class.getSimpleName() + "[", "]")
                .add("pastWeekCustomerCounts=" + pastWeekCustomerCounts)
                .toString();
    }
}
