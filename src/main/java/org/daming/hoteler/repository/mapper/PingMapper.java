package org.daming.hoteler.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author gming001
 * @version 2022-12-04 08:22
 */
@Mapper
public interface PingMapper {

    @Select("SELECT 'pong'")
    String ping();
}
