package org.daming.hoteler.repository.jdbc;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.UserRoom;

import java.time.LocalDate;
import java.util.List;

/**
 * @author gming001
 * @create 2021-03-05 15:59
 **/
public interface IUserRoomDao {

    void create(UserRoom userRoom) throws HotelerException;

    void update(UserRoom userRoom) throws HotelerException;

    UserRoom get(long id) throws HotelerException;

    void delete(long id) throws HotelerException;

    List<UserRoom> list() throws HotelerException;

    List<UserRoom> list(LocalDate date) throws HotelerException;
}
