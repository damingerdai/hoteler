package org.daming.hoteler.service;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.UserRoom;

import java.util.List;

/**
 * 用户和房间关联
 *
 * @author gming001
 * @create 2021-03-05 15:38
 **/
public interface IUserRoomService {

    long create(UserRoom userRoom) throws HotelerException;

    void update(UserRoom userRoom) throws HotelerException;

    UserRoom get(long id) throws HotelerException;

    void delete(long id) throws HotelerException;

    List<UserRoom> list() throws HotelerException;

    List<UserRoom> listCurrentDate() throws HotelerException;
}
