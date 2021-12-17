package org.daming.hoteler.service.stat.impl;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.PastWeekCustomerCount;
import org.daming.hoteler.pojo.stat.PastWeekCustomerCountStat;
import org.daming.hoteler.repository.mapper.CustomerCheckinRecordMapper;
import org.daming.hoteler.service.stat.ICustomerStatService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ICustomerStatService默认实现
 *
 * @author gming001
 * @create 2021-06-17 22:02
 **/
@Service
public class CustomerStatServiceImpl implements ICustomerStatService {

    private CustomerCheckinRecordMapper customerCheckinRecordMapper;

    @Override
    public PastWeekCustomerCountStat countPastWeekCustomerCountStat() throws HotelerException {
        var pastWeek = getPastWeek();
        var pastWeekCustomerCounts = new ArrayList<PastWeekCustomerCount>(pastWeek.size());
        pastWeek.forEach(pw -> {
            var nums = this.customerCheckinRecordMapper.getUserRoomCounts(pw, pw.plusDays(1));
            pastWeekCustomerCounts.add(new PastWeekCustomerCount(pw.toLocalDate(), nums));
        });
        var pastWeekCustomerCountStat = new PastWeekCustomerCountStat(pastWeekCustomerCounts);
        return pastWeekCustomerCountStat;
    }

    /**
     * 获取过去一周的时间
     * @return
     */
    private List<LocalDateTime> getPastWeek() {
        var currentDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0));
        return Arrays.asList(7, 6, 5, 4, 3, 2, 1).stream().map(n -> currentDate.minusDays(n)).collect(Collectors.toList());
    }

    public CustomerStatServiceImpl(CustomerCheckinRecordMapper customerCheckinRecordMapper) {
        super();
        this.customerCheckinRecordMapper = customerCheckinRecordMapper;
    }
}
