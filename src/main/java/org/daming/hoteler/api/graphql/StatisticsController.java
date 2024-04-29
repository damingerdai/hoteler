package org.daming.hoteler.api.graphql;

import org.daming.hoteler.pojo.stat.PastWeekCustomerCountStat;
import org.daming.hoteler.pojo.stat.RoomNumsStat;
import org.daming.hoteler.service.stat.ICustomerStatService;
import org.daming.hoteler.service.stat.IRoomStatusStatService;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

/**
 * @author gming001
 * @version 2024-04-29 20:16
 */
@Controller(value = "GraphqlStatisticsController")
public class StatisticsController {

    private final IRoomStatusStatService roomStatusStatService;

    private final ICustomerStatService customerStatService;

    @QueryMapping("getRoomStatusStat")
    public RoomNumsStat getRoomNumStat() {
        var roomNumsStat = this.roomStatusStatService.countRoomNumStatistics();
        return roomNumsStat;
    }

    @QueryMapping("getPastWeekCustomersCounts")
    public PastWeekCustomerCountStat getPastWeekCustomersCounts() {
        var pastWeekCustomerCountStat = this.customerStatService.countPastWeekCustomerCountStat();
        System.out.println("pastWeekCustomerCountStat" + pastWeekCustomerCountStat);
        return pastWeekCustomerCountStat;
    }


    public StatisticsController(IRoomStatusStatService roomStatusStatService, ICustomerStatService customerStatService) {
        super();
        this.roomStatusStatService = roomStatusStatService;
        this.customerStatService = customerStatService;
    }
}
