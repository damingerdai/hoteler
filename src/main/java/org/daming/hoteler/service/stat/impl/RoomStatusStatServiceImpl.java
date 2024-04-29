package org.daming.hoteler.service.stat.impl;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.Room;
import org.daming.hoteler.pojo.enums.RoomStatus;
import org.daming.hoteler.pojo.stat.RoomNumsStat;
import org.daming.hoteler.repository.jdbc.IRoomDao;
import org.daming.hoteler.repository.mapper.RoomMapper;
import org.daming.hoteler.service.stat.IRoomStatusStatService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * RoomStatusStatService的实现类
 *
 * @author gming001
 * @create 2021-06-09 20:11
 **/
@Service
public class RoomStatusStatServiceImpl implements IRoomStatusStatService {

    private IRoomDao roomDao;

    private RoomMapper roomMapper;

    @Override
    public RoomNumsStat countRoomNumStatistics() throws HotelerException {
        var rooms = this.roomDao.list();
        var groups = rooms.stream().collect(Collectors.groupingBy(Room::getStatus));
        return new RoomNumsStat(
                Optional.ofNullable(groups.get(RoomStatus.InUsed)).orElseGet(List::of).size(),
                Optional.ofNullable(groups.get(RoomStatus.NoUse)).orElseGet(List::of).size()
        );
    }

    public RoomStatusStatServiceImpl(IRoomDao roomDao, RoomMapper roomMapper) {
        super();
        this.roomDao = roomDao;
        this.roomMapper = roomMapper;
    }
}
