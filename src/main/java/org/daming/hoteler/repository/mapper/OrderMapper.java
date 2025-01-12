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
public interface OrderMapper {

    @Select("select count(*) from orders where #{beginDate} <= begin_date and begin_date <= #{endDate} and end_date <= #{endDate} and deleted_at is null")
    int getUserRoomCounts(@Param("beginDate") LocalDateTime beginDate, @Param("endDate")LocalDateTime endDate);

    @Select("select count(id) from orders where deleted_at is null")
    int count();
}
