package org.daming.hoteler.repository.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.daming.hoteler.pojo.Room;

/**
 * room mapper
 *
 * @author gming001
 * @create 2020-12-22 22:46
 **/
@Mapper
public interface RoomMapper {

    @Insert("insert into rooms (id, roomname, price, status, create_dt, create_user, update_dt, update_user) values (#{id}, #{roomname}, #{price}, #{status, typeHandler=org.daming.hoteler.pojo.handler.RoomStatusTypeHandler}, statement_timestamp(),'system', statement_timestamp(), 'system')")
    void create(Room room);
}
