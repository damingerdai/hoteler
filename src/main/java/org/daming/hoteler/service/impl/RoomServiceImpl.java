package org.daming.hoteler.service.impl;

import org.daming.hoteler.pojo.Room;
import org.daming.hoteler.pojo.enums.RoomStatus;
import org.daming.hoteler.repository.jdbc.IRoomDao;
import org.daming.hoteler.repository.mapper.RoomMapper;
import org.daming.hoteler.service.IRoomService;
import org.daming.hoteler.service.ISnowflakeService;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * room service implement
 *
 * @author gming001
 * @create 2020-12-22 23:29
 **/
@Service
public class RoomServiceImpl implements IRoomService {

    private ISnowflakeService snowflakeService;

    private RoomMapper roomMapper;

    private IRoomDao roomDao;

    @Override
    public long create(Room room) {
        var roomId = this.snowflakeService.nextId();
        room.setId(roomId);
        if (Objects.isNull(room.getStatus())) {
            room.setStatus(RoomStatus.NoUse);
        }
        this.roomMapper.create(room);
        return roomId;
    }

    @Override
    public Room get(long id) {
        return this.roomDao.get(id);
    }

    public RoomServiceImpl(ISnowflakeService snowflakeService, RoomMapper roomMapper, IRoomDao roomDao) {
        super();
        this.snowflakeService = snowflakeService;
        this.roomMapper = roomMapper;
        this.roomDao = roomDao;
    }
}
