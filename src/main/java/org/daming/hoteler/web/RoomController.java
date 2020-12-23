package org.daming.hoteler.web;

import org.daming.hoteler.pojo.Room;
import org.daming.hoteler.pojo.enums.RoomStatus;
import org.daming.hoteler.pojo.request.CreateRoomRequest;
import org.daming.hoteler.service.IRoomService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public long createRoom(CreateRoomRequest request) {
        var room = new Room().setRoomname(request.getRoomname()).setStatus(RoomStatus.NoUse);
        return 0L;
    }

    public RoomController(IRoomService roomService) {
        super();
        this.roomService = roomService;
    }
}
