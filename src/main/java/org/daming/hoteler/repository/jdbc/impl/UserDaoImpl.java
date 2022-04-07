package org.daming.hoteler.repository.jdbc.impl;

import org.daming.hoteler.repository.jdbc.IUserDao;
import org.daming.hoteler.pojo.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDaoImpl implements IUserDao {

    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<User> getUserByUsername(String username) {
        return jdbcTemplate.query("select id, username, password from users where username = ? limit 1 ", rs -> {
            while (rs.next()) {
                var user = new User().setId(rs.getLong("id")).setUsername(rs.getString("username")).setPassword(rs.getString("password"));
                return Optional.of(user);
            }
            return Optional.empty();
        },  new Object[] { username });
    }

    @Override
    public Optional<User> get(long id) {
        return jdbcTemplate.query("select id, username, password from users where id = ? limit 1 ", rs -> {
            while (rs.next()) {
                var user = new User().setId(rs.getLong("id")).setUsername(rs.getString("username")).setPassword(rs.getString("password"));
                return Optional.of(user);
            }
            return Optional.empty();
        }, new Object[] { id });
    }

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }
}
