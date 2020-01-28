package org.daming.hoteler.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.daming.hoteler.pojo.User;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select id, username, password from users")
    List<User> list();

    @Select("select id, username, password from users where id = #{id}")
    User get(@Param("id")long id);

    @Update("insert into users (id, username, password, create_dt, create_user, update_dt, update_user) values (#{id}, #{username}, #{password}, now(), 'UserMapper', now(), 'UserMapper') ")
    void create(User user);
}
