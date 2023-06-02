package org.daming.hoteler.repository.mapper;

import org.apache.ibatis.annotations.*;
import org.daming.hoteler.pojo.User;
import org.daming.hoteler.pojo.handler.RoleListTypeHandler;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("""
            SELECT
            	u.id, u.username, u.password, u.password_type,
            	COALESCE(ARRAY_TO_JSON(
            		COALESCE(
            			ARRAY_AGG(
            				JSON_BUILD_OBJECT('id', r.id, 'name', r.name, 'description', r.description)
            			)
            			FILTER
            			(WHERE r.id IS NOT NULL)
            		)
            	), '[]') AS roles
            FROM users u
            LEFT JOIN user_roles ur ON ur.user_id = u.id AND ur.deleted_at IS NULL
            LEFT JOIN roles r on r.id = ur.role_id and r.deleted_at IS NULL
            WHERE u.deleted_at IS NULL
            GROUP BY u.id
    """)
    @Results(
            value= {
                    @Result(property="id", column = "id"),
                    @Result(property="username", column = "username"),
                    @Result(property="password", column = "password"),
                    @Result(property="passwordType", column = "password_type"),
                    @Result(property="roles", column = "roles", javaType=List.class, typeHandler= RoleListTypeHandler.class)
            }
    )
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
