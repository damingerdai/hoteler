package org.daming.hoteler.service.impl;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.UserRoom;
import org.daming.hoteler.repository.jdbc.IUserRoomDao;
import org.daming.hoteler.service.ISnowflakeService;
import org.daming.hoteler.service.IUserRoomService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * UserRoomService的默认实现类
 *
 * @author gming001
 * @create 2021-03-05 15:56
 **/
@Service
public class UserRoomServiceImpl implements IUserRoomService {

    private IUserRoomDao userRoomDao;

    private ISnowflakeService snowflakeService;

    @Override
    public long create(UserRoom userRoom) throws HotelerException {
        var id  = this.snowflakeService.nextId();
        userRoom.setId(id);
        this.processBeginDateAndEndDate(userRoom);
        this.userRoomDao.create(userRoom);
        return id;
    }

    @Override
    public void update(UserRoom userRoom) throws HotelerException {
        this.processBeginDateAndEndDate(userRoom);
        this.userRoomDao.update(userRoom);
    }

    @Override
    public UserRoom get(long id) throws HotelerException {
        return this.userRoomDao.get(id);
    }

    @Override
    public void delete(long id) throws HotelerException {
        this.userRoomDao.delete(id);
    }

    private void processBeginDateAndEndDate(UserRoom userRoom) {
        var beginDate = userRoom.getBeginDate();
        userRoom.setBeginDate(LocalDateTime.of(beginDate.toLocalDate(), LocalTime.of(12, 0)));
        var endDate = userRoom.getEndDate();
        userRoom.setEndDate(LocalDateTime.of(endDate.toLocalDate(), LocalTime.of(12, 0)));
    }

    @Override
    public List<UserRoom> list() throws HotelerException {
        return this.userRoomDao.list();
    }

    @Override
    public List<UserRoom> listCurrentDate() throws HotelerException {
        return this.userRoomDao.list(LocalDate.now());
    }

    @Override
    public List<UserRoom> listByRoomIdAndDate(long roomId, LocalDate date) {
        return this.userRoomDao.listByRoom(roomId, date);
    }

    public UserRoomServiceImpl(IUserRoomDao userRoomDao,ISnowflakeService snowflakeService) {
        super();
        this.userRoomDao = userRoomDao;
        this.snowflakeService = snowflakeService;
    }
}
