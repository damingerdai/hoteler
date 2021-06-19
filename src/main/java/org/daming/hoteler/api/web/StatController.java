package org.daming.hoteler.api.web;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.daming.hoteler.pojo.response.DataResponse;
import org.daming.hoteler.pojo.stat.PastWeekCustomerCountStat;
import org.daming.hoteler.pojo.stat.RoomNumsStat;
import org.daming.hoteler.pojo.stat.RoomStatusStat;
import org.daming.hoteler.service.stat.ICustomerStatService;
import org.daming.hoteler.service.stat.IRoomStatusStatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 统计controller层
 *
 * @author gming001
 * @create 2021-06-08 22:19
 **/
@RestController
@RequestMapping("api/v1/stat")
public class StatController {

    private IRoomStatusStatService roomStatusStatService;

    private ICustomerStatService customerStatService;

    @ApiOperation(value = "room status stats", notes = "get room status stats api")
    @GetMapping(path = "rooms")
    public DataResponse<RoomStatusStat> getRoomStatusStat() {
        var roomStatusStat = new RoomStatusStat().setCurrentWeekInUsedRoomNum(10).setLastWeekInUsedRoomNum(12);
        return new DataResponse(roomStatusStat);
    }

    @ApiOperation(value = "room num stats", notes = "get room num stats api")
    @GetMapping(path = "rooms/nums")
    public DataResponse<RoomNumsStat> getRoomNumStat() {
        var roomNumsStat = this.roomStatusStatService.countRoomNumStatistics();
        return new DataResponse(roomNumsStat);
    }

    @ApiOperation(value = "customer count stats", notes = "get customer count stats api")
    @GetMapping(path = "customers/counts")
    public DataResponse<PastWeekCustomerCountStat> getPastWeekCustomersCounts() {
        var pastWeekCustomerCountStat = this.customerStatService.countPastWeekCustomerCountStat();
        return new DataResponse(pastWeekCustomerCountStat);
    }

    public StatController(IRoomStatusStatService roomStatusStatService, ICustomerStatService customerStatService) {
        super();
        this.roomStatusStatService = roomStatusStatService;
        this.customerStatService = customerStatService;
    }
}
