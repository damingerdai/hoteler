package org.daming.hoteler.api.web;

//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiImplicitParams;
//import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.daming.hoteler.pojo.response.DataResponse;
import org.daming.hoteler.pojo.stat.PastWeekCustomerCountStat;
import org.daming.hoteler.pojo.stat.RoomNumsStat;
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
@Tag(name = "统计服务接口")
@RestController
@RequestMapping("api/v1/stat")
public class StatController {

    private IRoomStatusStatService roomStatusStatService;

    private ICustomerStatService customerStatService;

    @Operation(summary = "获取房间数量统计", security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping(path = "rooms/nums")
    public DataResponse<RoomNumsStat> getRoomNumStat() {
        var roomNumsStat = this.roomStatusStatService.countRoomNumStatistics();
        return new DataResponse(roomNumsStat);
    }

    @Operation(summary = "获取客户数量统计", security = { @SecurityRequirement(name = "bearer-key") })
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
