package org.daming.hoteler.repository.jdbc.impl;

import org.daming.hoteler.base.logger.SqlLoggerUtil;
import org.daming.hoteler.repository.jdbc.IUserDao;
import org.daming.hoteler.pojo.User;
import org.daming.hoteler.service.IErrorService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Repository
public class UserDaoImpl implements IUserDao {

    private JdbcTemplate jdbcTemplate;
    private IErrorService errorService;

    @Override
    public Optional<User> getUserByUsername(String username) {
        var in = Instant.now();
        var sql = "select id, username, password, password_type from users where username = ? limit 1";
        var params = new Object[] { username };
        try {
            return jdbcTemplate.query(sql, rs -> {
                while (rs.next()) {
                    var user = new User()
                            .setId(rs.getLong("id"))
                            .setUsername(rs.getString("username"))
                            .setPassword(rs.getString("password"))
                            .setPasswordType(rs.getString("password_type"));
                    return Optional.of(user);
                }
                return Optional.empty();
            },  params);
        } catch (Exception ex) {
            SqlLoggerUtil.logSqlException(sql, params, ex);
            throw this.errorService.createSqlHotelerException(ex, sql);
        } finally {
            SqlLoggerUtil.logSql(sql, params, Duration.between(in, Instant.now()));
        }
    }

    @Override
    public Optional<User> get(long id) {
        var in = Instant.now();
        var sql = "select id, username, password, password_type from users where id = ? limit 1";
        var params = new Object[] { id };
        try {
            return jdbcTemplate.query(sql, rs -> {
                while (rs.next()) {
                    var user = new User()
                            .setId(rs.getLong("id"))
                            .setUsername(rs.getString("username"))
                            .setPassword(rs.getString("password"))
                            .setPasswordType(rs.getString("password_type"));
                    return Optional.of(user);
                }
                return Optional.empty();
            }, params);
        } catch (Exception ex) {
            SqlLoggerUtil.logSqlException(sql, params, ex);
            throw this.errorService.createSqlHotelerException(ex, sql);
        } finally {
            SqlLoggerUtil.logSql(sql, params, Duration.between(in, Instant.now()));
        }

    }

    public UserDaoImpl(JdbcTemplate jdbcTemplate, IErrorService errorService) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        this.errorService = errorService;
    }
}
