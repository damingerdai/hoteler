package org.daming.hoteler.repository.jdbc.impl;

import org.daming.hoteler.base.logger.SqlLoggerUtil;
import org.daming.hoteler.repository.jdbc.IUserDao;
import org.daming.hoteler.pojo.User;
import org.daming.hoteler.service.IErrorService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class UserDaoImpl implements IUserDao {

    private JdbcTemplate jdbcTemplate;
    private IErrorService errorService;

    @Override
    public Optional<User> getUserByUsername(String username) {
        var in = Instant.now();
        var sql = "select id, username, password, password_type, failed_login_attempts, account_non_locked, lock_time from users where username = ? and deleted_at is null limit 1";
        var params = new Object[] { username };
        try {
            return jdbcTemplate.query(sql, UserDaoImpl::getUser,  params);
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
        var sql = "select id, username, password, password_type, failed_login_attempts, account_non_locked, lock_time from users where id = ? and deleted_at is null limit 1";
        var params = new Object[] { id };
        try {
            return jdbcTemplate.query(sql, UserDaoImpl::getUser, params);
        } catch (Exception ex) {
            SqlLoggerUtil.logSqlException(sql, params, ex);
            throw this.errorService.createSqlHotelerException(ex, sql);
        } finally {
            SqlLoggerUtil.logSql(sql, params, Duration.between(in, Instant.now()));
        }

    }

    @Override
    public List<User> getUnlockUsers() {
        var in = Instant.now();
        var sql = "select id, username, password, password_type, failed_login_attempts, account_non_locked, lock_time from users where account_non_locked = false and lock_time < current_timestamp - interval '5 minutes' and deleted_at is null";
        try {
            return jdbcTemplate.query(sql, (rs, i) -> {
                var user = new User()
                        .setId(rs.getLong("id"))
                        .setUsername(rs.getString("username"))
                        .setPassword(rs.getString("password"))
                        .setPasswordType(rs.getString("password_type"))
                        .setFailedLoginAttempts(rs.getInt("failed_login_attempts"))
                        .setAccountNonLocked(rs.getBoolean("account_non_locked"));
                var lokTime = rs.getTimestamp("lock_time");
                if (Objects.nonNull(lokTime)) {
                    user.setLockTime(lokTime.toLocalDateTime());
                }
                return user;
            });
        } catch (Exception ex) {
            var params = new Object[] {  };
            SqlLoggerUtil.logSqlException(sql, params, ex);
            throw this.errorService.createSqlHotelerException(ex, sql);
        } finally {
            var params = new Object[] {  };
            SqlLoggerUtil.logSql(sql, params, Duration.between(in, Instant.now()));
        }
    }

    private static Optional<User> getUser(ResultSet rs) throws SQLException {
        while (rs.next()) {
            var user = new User()
                    .setId(rs.getLong("id"))
                    .setUsername(rs.getString("username"))
                    .setPassword(rs.getString("password"))
                    .setPasswordType(rs.getString("password_type"))
                    .setFailedLoginAttempts(rs.getInt("failed_login_attempts"))
                    .setAccountNonLocked(rs.getBoolean("account_non_locked"));
            var lokTime = rs.getTimestamp("lock_time");
            if (Objects.nonNull(lokTime)) {
                user.setLockTime(lokTime.toLocalDateTime());
            }
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public UserDaoImpl(JdbcTemplate jdbcTemplate, IErrorService errorService) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        this.errorService = errorService;
    }
}
