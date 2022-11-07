package org.daming.hoteler.repository.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author gming001
 * @version 2022-11-03 18:59
 */
@Mapper
public interface UserRoleMapper {

    @Insert("INSERT INTO user_roles  (user_id, role_id, created_at, updated_at) VALUES (#{userId}, #{roleId}, statement_timestamp(), statement_timestamp()) RETURNING id")
    Long create(long userId, long roleId);
}
