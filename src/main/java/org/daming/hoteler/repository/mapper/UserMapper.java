package org.daming.hoteler.repository.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.daming.hoteler.pojo.User;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select id, username, password from users")
    List<User> list();

    @Select("select id, username, password from users where id = #{id}")
    User get(@Param("id")long id);

//    @Insert("insert into users (id, username, password, create_dt, create_user, update_dt, update_user) values (#{id}, #{username}, #{password}, now(), 'UserMapper', now(), 'UserMapper') ")
//    void create(User user);

    @Insert("insert into users (id, username, password, create_dt, create_user, update_dt, update_user) values (#{id}, #{username}, #{password}, statement_timestamp(),'system', statement_timestamp(), 'system')")
    void create(@Param("id")long id, @Param("username")String username, @Param("password")String password);
}
