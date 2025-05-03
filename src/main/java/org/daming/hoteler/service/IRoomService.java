package org.daming.hoteler.service;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.Pageable;
import org.daming.hoteler.pojo.Room;
import org.daming.hoteler.pojo.Sortable;
import org.daming.hoteler.pojo.enums.RoomStatus;

import java.util.List;

/**
 * room service
 *
 * @author gming001
 * @create 2020-12-22 23:20
 **/
public interface IRoomService {

    long create(Room room) throws HotelerException;

    Room get(long id) throws HotelerException;

    void update(Room room) throws HotelerException;

    List<Room> list(Room room) throws HotelerException;

    List<Room> list(Room room, Pageable pageable, Sortable sortable) throws HotelerException;

    List<Room> list() throws HotelerException;

    void delete(long id) throws HotelerException;

    void updateStatus(long id, RoomStatus status)  throws HotelerException;
}
