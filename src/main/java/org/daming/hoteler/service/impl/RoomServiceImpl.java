package org.daming.hoteler.service.impl;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.Room;
import org.daming.hoteler.pojo.enums.RoomStatus;
import org.daming.hoteler.repository.jdbc.IRoomDao;
import org.daming.hoteler.repository.mapper.RoomMapper;
import org.daming.hoteler.service.IErrorService;
import org.daming.hoteler.service.IRoomService;
import org.daming.hoteler.service.ISnowflakeService;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * room service implement
 *
 * @author gming001
 * @create 2020-12-22 23:29
 **/
@Service
public class RoomServiceImpl implements IRoomService {

    private RoomMapper roomMapper;

    private IRoomDao roomDao;

    private ISnowflakeService snowflakeService;

    private IErrorService errorService;

    @Override
    public long create(Room room) throws HotelerException {
        try {
            var roomId = this.snowflakeService.nextId();
            room.setId(roomId);
            if (Objects.isNull(room.getStatus())) {
                room.setStatus(RoomStatus.NoUse);
            }
            this.roomMapper.create(room);
            return roomId;
        } catch (HotelerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw errorService.createHotelerSystemException(ex.getMessage(), ex);
        }

    }

    @Override
    public Room get(long id) throws HotelerException {
        try {
            return this.roomDao.get(id);
        } catch (HotelerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw errorService.createHotelerSystemException(ex.getMessage(), ex);
        }

    }

    @Override
    public void update(Room room) throws HotelerException {
        try {
            this.roomMapper.update(room);
        } catch (PersistenceException | DataAccessException ex) {
            throw errorService.createSqlHotelerException(ex, "update room where id = " + room.getId());
        } catch (HotelerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw errorService.createHotelerSystemException(ex.getMessage(), ex);
        }
    }

    @Override
    public List<Room> list() {
        return this.roomDao.list();
    }

    @Override
    public void delete(long id) throws HotelerException {
        try {
            this.roomMapper.delete(id);
        } catch (PersistenceException | DataAccessException ex) {
            throw errorService.createSqlHotelerException(ex, "delete room from id = " + id + " limit 1");
        } catch (HotelerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw errorService.createHotelerSystemException(ex.getMessage(), ex);
        }
    }

    public RoomServiceImpl(
            RoomMapper roomMapper,
            IRoomDao roomDao,
            ISnowflakeService snowflakeService,
            IErrorService errorService) {
        super();
        this.roomMapper = roomMapper;
        this.roomDao = roomDao;
        this.snowflakeService = snowflakeService;
        this.errorService = errorService;
    }


    public void setSnowflakeService(ISnowflakeService snowflakeService) {
        this.snowflakeService = snowflakeService;
    }

    public void setErrorService(IErrorService errorService) {
        this.errorService = errorService;
    }
}
