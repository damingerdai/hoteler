package org.daming.hoteler.service.impl;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.Pageable;
import org.daming.hoteler.pojo.Room;
import org.daming.hoteler.pojo.Sortable;
import org.daming.hoteler.pojo.enums.RoomStatus;
import org.daming.hoteler.repository.jdbc.IRoomDao;
import org.daming.hoteler.repository.mapper.RoomMapper;
import org.daming.hoteler.service.IErrorService;
import org.daming.hoteler.service.IRoomService;
import org.daming.hoteler.service.ISnowflakeService;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
@CacheConfig(cacheNames = {"RoomCache"})
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
    @Cacheable(cacheNames = { "room" }, key = "#id")
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
    @CacheEvict(cacheNames = { "room" }, key = "#room.id", allEntries = true)
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
    //@Cacheable(cacheNames = { "rooms" }, key = "#room")
    public List<Room> list(Room room) {
        return this.roomDao.list(room);
    }

    @Override
    public List<Room> list(Room room, Pageable pageable, Sortable sortable) throws HotelerException {
        return this.roomDao.list(room, pageable, sortable);
    }

    @Override
    //@Cacheable("roomList")
    public List<Room> list() throws HotelerException {
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

    @Override
    @CacheEvict(cacheNames = { "rooms", "roomList" }, allEntries = true)
    public void updateStatus(long id, RoomStatus status) throws HotelerException {
        try {
            this.roomMapper.updateStatus(id, status);
        } catch (PersistenceException | DataAccessException ex) {
            throw errorService.createSqlHotelerException(ex, "update room where id = " + id);
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
