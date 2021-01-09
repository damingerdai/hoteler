package org.daming.hoteler.api.web;

import io.swagger.annotations.ApiOperation;
import org.daming.hoteler.pojo.Room;
import org.daming.hoteler.pojo.enums.RoomStatus;
import org.daming.hoteler.pojo.request.CreateRoomRequest;
import org.daming.hoteler.service.IRoomService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * room controller
 *
 * @author gming001
 * @create 2020-12-22 23:40
 **/
@RestController
@RequestMapping("api/v1")
public class RoomController {

    private IRoomService roomService;

    @ApiOperation(value = "create room", notes = "create a new room api")
    @PostMapping("room")
    public long createRoom(@RequestBody CreateRoomRequest request) {
        var room = new Room().setRoomname(request.getRoomname()).setStatus(RoomStatus.NoUse);
        roomService.create(room);
        return room.getId();
    }

    @ApiOperation(value = "get room", notes = "get a room api")
    @GetMapping("room/{id}")
    public Room get(@PathVariable(value = "id", required = true)long id) {
        return this.roomService.get(id);
    }

    @ApiOperation(value = "list room", notes = "get all rooms api")
    @GetMapping("rooms")
    public List<Room> get() {
        return this.roomService.list();
    }


    public RoomController(IRoomService roomService) {
        super();
        this.roomService = roomService;
    }
}
