package org.daming.hoteler.task;

import org.daming.hoteler.base.logger.HotelerLogger;
import org.daming.hoteler.base.logger.LoggerManager;
import org.daming.hoteler.pojo.Room;
import org.daming.hoteler.pojo.enums.RoomStatus;
import org.daming.hoteler.service.IRoomService;
import org.daming.hoteler.service.IUserRoomService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;

@Component
public class RoomStatusTask {

    private final HotelerLogger logger = LoggerManager.getJobLogger();

    private IUserRoomService userRoomService;

    private IRoomService roomService;

    @Scheduled(cron = "0 0 1 * * ?")
    // @Scheduled(cron = "*/5 * * * * ?")
    public void updateRoomStatus() {
        logger.info("开始更新房间的状态");
        var rooms = this.roomService.list();
        if (Objects.isNull(rooms) && rooms.size() == 0) {
            logger.warn("no rooms");
            return;
        }
        rooms.stream().map(Room::getId).filter(roomId -> {
            var userRooms = this.userRoomService.listByRoomIdAndDate(roomId, LocalDate.now());
            return !userRooms.isEmpty();
        }).forEach(roomId -> {
            roomService.updateStatus(roomId, RoomStatus.InUsed);
        });


    }

    public RoomStatusTask(
            IUserRoomService userRoomService,
            IRoomService roomService) {
        super();
        this.userRoomService = userRoomService;
        this.roomService = roomService;
    }
}
