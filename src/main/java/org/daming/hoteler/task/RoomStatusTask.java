package org.daming.hoteler.task;

import org.daming.hoteler.base.logger.HotelerLogger;
import org.daming.hoteler.base.logger.LoggerManager;
import org.daming.hoteler.pojo.enums.RoomStatus;
import org.daming.hoteler.service.IRoomService;
import org.daming.hoteler.service.IUserRoomService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RoomStatusTask {

    private final HotelerLogger logger = LoggerManager.getJobLogger();

    private IUserRoomService userRoomService;

    private IRoomService roomService;

    // @Scheduled(cron = "0 0 1 * * ?")
    @Scheduled(cron = "*/5 * * * * ?")
    public void updateRoomStatus() {
        logger.info("当前时间：{}\t\t任务：cron task，每5秒执行一次", System.currentTimeMillis());
        var userRooms = this.userRoomService.listCurrentDate();
        if (Objects.isNull(userRooms) && userRooms.size() == 0) {
            logger.warn("no user rooms");
            return;
        }

        userRooms.forEach(userRoom -> {
            var roomId = userRoom.getRoomId();
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
