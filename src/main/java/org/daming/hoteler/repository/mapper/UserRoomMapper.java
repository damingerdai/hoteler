package org.daming.hoteler.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;

/**
 * 客户房间mapper
 *
 * @author gming001
 * @create 2021-06-17 23:08
 **/
@Mapper
public interface UserRoomMapper {

    @Select("select count(*) from users_rooms where #{beginDate} <= begin_date and begin_date <= #{endDate} and end_date <= #{endDate}")
    int getUserRoomCounts(@Param("beginDate") LocalDateTime beginDate, @Param("endDate")LocalDateTime endDate);
}
