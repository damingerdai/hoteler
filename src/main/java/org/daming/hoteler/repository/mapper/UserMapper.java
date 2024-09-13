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
          u.failed_login_attempts, u.account_non_locked, u.lock_time,
          COALESCE(ARRAY_TO_JSON(ARRAY_AGG(
            JSON_BUILD_OBJECT('id', r.id, 'name', r.name, 'description', r.description)
          ) FILTER (WHERE r.id IS NOT NULL)), '[]') AS roles
        FROM users u
        LEFT JOIN user_roles ur ON ur.user_id = u.id AND ur.deleted_at IS NULL
        LEFT JOIN roles r on r.id = ur.role_id AND r.deleted_at IS NULL
        WHERE u.deleted_at IS NULL
        GROUP BY u.id
            """)
    @Results(
            value = {
                    @Result(property = "id", column = "id"),
                    @Result(property = "username", column = "username"),
                    @Result(property = "password", column = "password"),
                    @Result(property = "passwordType", column = "password_type"),
                    @Result(property = "failedLoginAttempts", column = "failed_login_attempts"),
                    @Result(property = "accountNonLocked", column = "account_non_locked"),
                    @Result(property = "lockTime", column = "lock_time"),
                    @Result(property = "roles", column = "roles", javaType = List.class, typeHandler = RoleListTypeHandler.class)
            }
    )
    List<User> list();

    @Select("select id, username, password, password_type, failed_login_attempts, account_non_locked, lock_time from users where id = #{id} and deleted_at is null")
    User get(@Param("id") long id);

    @Insert("insert into users (id, username, password, password_type, create_dt, create_user, update_dt, update_user) values (#{id}, #{username}, #{password}, #{password_type}::passwordtype, statement_timestamp(),'system', statement_timestamp(), 'system')")
    void create(@Param("id") long id, @Param("username") String username, @Param("password") String password, @Param("password_type") String passwordType);

    @Select("select count(id) from users where deleted_at is null")
    int count();

    @Update("""
             <script>
             UPDATE users
              <set>
              <if test="username != null">username=#{username},</if>
              <if test="password != null">password=#{password},</if>
              <if test="passwordType != null">password_type=#{passwordType}::passwordtype,</if>
              <if test="failedLoginAttempts != null">failed_login_attempts=#{failedLoginAttempts} ,</if>
              <if test="accountNonLocked != null">account_non_locked=#{accountNonLocked} ,</if>
              <if test="lockTime != null">lock_time=#{lockTime} ,</if>
              update_dt=statement_timestamp(),
              update_user='system'
            </set>
             WHERE id = #{id} AND deleted_at IS NULL
             </script>
            """)
    void update(User user);
}
