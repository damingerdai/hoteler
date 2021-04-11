package org.daming.hoteler.repository.jdbc.impl;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.base.logger.LoggerManager;
import org.daming.hoteler.constants.ErrorCodeConstants;
import org.daming.hoteler.pojo.UserRoom;
import org.daming.hoteler.repository.jdbc.IUserRoomDao;
import org.daming.hoteler.service.IErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * UserRoomDao 默认实现
 *
 * @author gming001
 * @create 2021-03-05 16:20
 **/
@Repository
public class UserRoomDaoImpl implements IUserRoomDao {

    private JdbcTemplate jdbcTemplate;

    private IErrorService errorService;

    @Override
    public void create(UserRoom userRoom) throws HotelerException {
        var sql = "insert into users_rooms (id, user_id, room_id, begin_date, end_date, create_dt, create_user, update_dt, update_user) values ( ?, ?, ?, ?, ?, statement_timestamp(), 'system', statement_timestamp(), 'system')";
        var params = new Object[] { userRoom.getId(), userRoom.getUserId(), userRoom.getRoomId(), userRoom.getBeginDate(), userRoom.getEndDate() };
        try {
            jdbcTemplate.update(sql, params);
        } catch (Exception ex) {
            LoggerManager.getJdbcLogger().error(() -> "fail to create userRoom with '" + userRoom + "', err: " + ex.getMessage(), ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        }
    }

    @Override
    public void update(UserRoom userRoom) throws HotelerException {
        var sql = "update users_rooms set user_id = ?, room_id = ?, begin_date = ?, end_date = ?, create_dt = statement_timestamp(), create_user = 'system', update_dt = statement_timestamp(), update_user = 'system' where id = ?";
        var params = new Object[] { userRoom.getUserId(), userRoom.getRoomId(), userRoom.getBeginDate(), userRoom.getEndDate(), userRoom.getId() };
        try {
            jdbcTemplate.update(sql, params);
        } catch (Exception ex) {
            LoggerManager.getJdbcLogger().error(() -> "fail to update userRoom with '" + userRoom + "', err: " + ex.getMessage(), ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        }
    }

    @Override
    public UserRoom get(long id) throws HotelerException {
        var sql = "select id, user_id, room_id, begin_date, end_date from users_rooms where id = ?";
        var params = new Object[] { id };
        try {
            return jdbcTemplate.query(sql, rs -> {
                while (rs.next()) {
                    return getUserRoom(rs);
                }
                return null;
            }, params);
        } catch (Exception ex) {
            LoggerManager.getJdbcLogger().error(() -> "fail to get userRoom with id'" + id + "', err: " + ex.getMessage(), ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        }
    }



    @Override
    public void delete(long id) throws HotelerException {
        var sql = "delete from users_rooms where id = ?";
        var params = new Object[] { id };
        try {
            jdbcTemplate.update(sql, params);
        } catch (Exception ex) {
            LoggerManager.getJdbcLogger().error(() -> "fail to delete userRoom with id'" + id + "', err: " + ex.getMessage(), ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        }
    }

    @Override
    public List<UserRoom> list() throws HotelerException {
        var sql = " select id, user_id, room_id, begin_date, end_date from users_rooms order by create_dt desc, update_dt desc";
        try {
            return this.jdbcTemplate.query(sql, (rs, i) -> getUserRoom(rs));
        } catch (Exception ex) {
            LoggerManager.getJdbcLogger().error(() -> "fail to list userRoom', err: " + ex.getMessage(), ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        }
    }

    @Override
    public List<UserRoom> list(LocalDate date) throws HotelerException {
        var sql = "select id, user_id, room_id, begin_date, end_date from users_rooms where begin_date <= ? and ? <= end_date order by create_dt desc, update_dt desc";
        var params = new Object[] { date, date };
        try {
            return this.jdbcTemplate.query(sql, (rs, i) -> getUserRoom(rs), params);
        } catch (Exception ex) {
            LoggerManager.getJdbcLogger().error(() -> "fail to list userRoom', err: " + ex.getMessage(), ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        }
    }

    @Override
    public List<UserRoom> listByRoom(long roomId, LocalDate date) throws HotelerException {
        var sql = "select id, user_id, room_id, begin_date, end_date from users_rooms where room_id = ? and begin_date <= ? and ? <= end_date order by create_dt desc, update_dt desc";
        var params = new Object[] { roomId, date, date };
        try {
            return this.jdbcTemplate.query(sql, (rs, i) -> getUserRoom(rs), params);
        } catch (Exception ex) {
            LoggerManager.getJdbcLogger().error(() -> "fail to list userRoom', err: " + ex.getMessage(), ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        }
    }

    private UserRoom getUserRoom(ResultSet rs) throws SQLException {
        return new UserRoom()
                .setId(rs.getLong("id"))
                .setUserId(rs.getLong("user_id"))
                .setRoomId(rs.getLong("room_id"))
                .setBeginDate(rs.getTimestamp("begin_date").toLocalDateTime())
                .setEndDate(rs.getTimestamp("end_date").toLocalDateTime());
    }

    public UserRoomDaoImpl(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setErrorService(IErrorService errorService) {
        this.errorService = errorService;
    }
}
