package org.daming.hoteler.repository.jdbc;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.Pageable;
import org.daming.hoteler.pojo.Room;
import org.daming.hoteler.pojo.Sortable;

import java.util.List;

/**
 * room dao
 *
 * @author gming001
 * @create 2020-12-23 12:42
 **/
public interface IRoomDao {

    Room get(long id);

    List<Room> list(Room room);

    List<Room> list(Room room, Pageable pageable, Sortable sortable) throws HotelerException;

    List<Room> list();
}
