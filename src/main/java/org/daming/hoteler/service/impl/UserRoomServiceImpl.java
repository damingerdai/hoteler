package org.daming.hoteler.service.impl;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.UserRoom;
import org.daming.hoteler.repository.jdbc.IUserRoomDao;
import org.daming.hoteler.service.ISnowflakeService;
import org.daming.hoteler.service.IUserRoomService;
import org.springframework.stereotype.Service;

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
        this.userRoomDao.create(userRoom);
        return id;
    }

    @Override
    public void update(UserRoom userRoom) throws HotelerException {
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

    public UserRoomServiceImpl(IUserRoomDao userRoomDao,ISnowflakeService snowflakeService) {
        super();
        this.userRoomDao = userRoomDao;
        this.snowflakeService = snowflakeService;
    }
}
