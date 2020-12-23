package org.daming.hoteler.service;

import org.daming.hoteler.pojo.Room;

/**
 * room service
 *
 * @author gming001
 * @create 2020-12-22 23:20
 **/
public interface IRoomService {

    long create(Room room);

    Room get(long id);
}
