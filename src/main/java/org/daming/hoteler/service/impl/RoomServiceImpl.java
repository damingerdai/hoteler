package org.daming.hoteler.service.impl;

import org.daming.hoteler.pojo.Room;
import org.daming.hoteler.repository.mapper.RoomMapper;
import org.daming.hoteler.service.IRoomService;
import org.daming.hoteler.service.ISnowflakeService;
import org.springframework.stereotype.Service;

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

    @Override
    public long create(Room room) {
        var roomId = this.snowflakeService.nextId();
        room.setId(roomId);
        this.roomMapper.create(room);
        return roomId;
    }

    public RoomServiceImpl(ISnowflakeService snowflakeService, RoomMapper roomMapper) {
        super();
        this.snowflakeService = snowflakeService;
        this.roomMapper = roomMapper;
    }
}
