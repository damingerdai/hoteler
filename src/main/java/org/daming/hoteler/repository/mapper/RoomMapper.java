package org.daming.hoteler.repository.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.daming.hoteler.pojo.Room;
import org.daming.hoteler.pojo.enums.RoomStatus;

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

    @Delete("update rooms set update_dt = statement_timestamp(), update_user = 'system', deleted_at = statement_timestamp() where id = #{id}")
    void delete(long id);

    @Update("update rooms set roomname = #{roomname}, price = #{price}, status = #{status, typeHandler=org.daming.hoteler.pojo.handler.RoomStatusTypeHandler}, update_dt = statement_timestamp(), update_user = 'system' where id = #{id}")
    void update(Room room);

    @Update("update rooms set status = #{status, typeHandler=org.daming.hoteler.pojo.handler.RoomStatusTypeHandler}, update_dt = statement_timestamp(), update_user = 'system' where id = #{id}")
    void updateStatus(@Param("id") long id, @Param("status") RoomStatus status);
}