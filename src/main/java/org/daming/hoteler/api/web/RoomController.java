package org.daming.hoteler.api.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.Room;
import org.daming.hoteler.pojo.enums.RoomStatus;
import org.daming.hoteler.pojo.enums.SortType;
import org.daming.hoteler.pojo.request.CreateRoomRequest;
import org.daming.hoteler.pojo.request.UpdateRoomRequest;
import org.daming.hoteler.pojo.response.CommonResponse;
import org.daming.hoteler.pojo.response.DataResponse;
import org.daming.hoteler.service.IRoomService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.Optional;

/**
 * room controller
 *
 * @author gming001
 * @create 2020-12-22 23:40
 **/
@Tag(name = "房间 controller")
@RestController
@RequestMapping("api/v1")
public class RoomController {

    private IRoomService roomService;

    @Operation(summary = "创建房间", security = { @SecurityRequirement(name = "bearer-key") })
    @PostMapping("room")
    public CommonResponse create(@RequestBody CreateRoomRequest request) {
        var room = new Room().setRoomname(request.getRoomname()).setStatus(RoomStatus.NoUse).setPrice(request.getPrice());
        roomService.create(room);
        var roomId = room.getId();
        return new DataResponse<String>(Long.toString(roomId));
    }

    @Operation(summary = "获取房间", security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("room/{id}")
    public DataResponse<Room> get(@PathVariable(value = "id")long id) {
        var room = this.roomService.get(id);
        return new DataResponse(room);
    }

    @Operation(summary = "获取所有的房间", security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("rooms")
    public CommonResponse list(@RequestParam Optional<RoomStatus> status,
                               @RequestParam(required = true, defaultValue = "1") Integer pageNo,
                               @RequestParam(required = true, defaultValue = "10") Integer pageSize,
                               @RequestParam(required = false) String orderBy,
                               @RequestParam(required = false, defaultValue = "dscss") SortType sortType
                               ) {

        var room = new Room();
        status.ifPresent(room::setStatus);
        var rooms = this.roomService.list(room);
        return new DataResponse<>(rooms);
    }

    @Operation(summary = "更新房间", security = { @SecurityRequirement(name = "bearer-key") })
    @PutMapping("room")
    public CommonResponse update(@RequestBody UpdateRoomRequest request) throws HotelerException {
        var room = new Room().setId(request.getId()).setRoomname(request.getRoomname()).setPrice(request.getPrice()).setStatus(request.getStatus());
        this.roomService.update(room);
        return new CommonResponse();
    }

    @Operation(summary = "删除", security = { @SecurityRequirement(name = "bearer-key") })
    @DeleteMapping("room/{id}")
    public CommonResponse delete(@PathVariable long id) throws HotelerException {
        this.roomService.delete(id);
        return new CommonResponse();
    }


    public RoomController(IRoomService roomService) {
        super();
        this.roomService = roomService;
    }
}
