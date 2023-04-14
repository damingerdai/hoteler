package org.daming.hoteler.repository.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.daming.hoteler.pojo.User;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select id, username, password, password_type from users where deleted_at is null")
    List<User> list();

    @Select("select id, username, password, password_type from users where id = #{id} and deleted_at is null")
    User get(@Param("id")long id);

    //@Insert("insert into users (id, username, password, create_dt, create_user, update_dt, update_user) values (#{id}, #{username}, #{password}, #{passwordType}::passwordtype, now(), 'UserMapper', now(), 'UserMapper') ")
    //void create(User user);

    @Insert("insert into users (id, username, password, password_type, create_dt, create_user, update_dt, update_user) values (#{id}, #{username}, #{password}, #{password_type}::passwordtype, statement_timestamp(),'system', statement_timestamp(), 'system')")
    void create(@Param("id")long id, @Param("username")String username, @Param("password")String password, @Param("password_type")String passwordType);

    @Select("select count(id) from users where deleted_at is null")
    int count();
}
