package org.daming.hoteler.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.daming.hoteler.pojo.User;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select id, username, password from users")
    List<User> list();
}
