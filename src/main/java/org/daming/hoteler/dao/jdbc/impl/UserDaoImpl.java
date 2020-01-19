package org.daming.hoteler.dao.jdbc.impl;

import org.daming.hoteler.dao.jdbc.IUserDao;
import org.daming.hoteler.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDaoImpl implements IUserDao {

    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<User> getUserByUsername(String username) {
        return jdbcTemplate.query("select id, username, password from users where username = ? ", new Object[] { username }, rs -> {
            while (rs.next()) {
                var user = new User().setId(rs.getLong("id")).setUsername(rs.getString("username")).setPassword(rs.getString("password"));
                return Optional.of(user);
            }
            return Optional.empty();
        });
    }

    @Autowired
    public UserDaoImpl setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        return this;
    }
}
