package org.daming.hoteler.repository.jdbc.impl;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.base.logger.LoggerManager;
import org.daming.hoteler.constants.ErrorCodeConstants;
import org.daming.hoteler.pojo.CustomerCheckinRecord;
import org.daming.hoteler.repository.jdbc.ICustomerCheckinRecordDao;
import org.daming.hoteler.service.IErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * 'customer checkin record'Dao 默认实现
 *
 * @author gming001
 * @create 2021-03-05 16:20
 **/
@Repository
public class CustomerCheckinRecordDaoImpl implements ICustomerCheckinRecordDao {

    private JdbcTemplate jdbcTemplate;

    private IErrorService errorService;

    @Override
    public void create(CustomerCheckinRecord customerCheckinRecord) throws HotelerException {
        var sql = "insert into customer_checkin_record (id, user_id, room_id, begin_date, end_date, create_dt, create_user, update_dt, update_user) values ( ?, ?, ?, ?, ?, statement_timestamp(), 'system', statement_timestamp(), 'system')";
        var params = new Object[] { customerCheckinRecord.getId(), customerCheckinRecord.getUserId(), customerCheckinRecord.getRoomId(), customerCheckinRecord.getBeginDate(), customerCheckinRecord.getEndDate() };
        try {
            jdbcTemplate.update(sql, params);
        } catch (Exception ex) {
            LoggerManager.getJdbcLogger().error(() -> "fail to create 'customer checkin record' with '" + customerCheckinRecord + "', err: " + ex.getMessage(), ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        }
    }

    @Override
    public void update(CustomerCheckinRecord customerCheckinRecord) throws HotelerException {
        var sql = "update customer_checkin_record set user_id = ?, room_id = ?, begin_date = ?, end_date = ?, create_dt = statement_timestamp(), create_user = 'system', update_dt = statement_timestamp(), update_user = 'system' where id = ?";
        var params = new Object[] { customerCheckinRecord.getUserId(), customerCheckinRecord.getRoomId(), customerCheckinRecord.getBeginDate(), customerCheckinRecord.getEndDate(), customerCheckinRecord.getId() };
        try {
            jdbcTemplate.update(sql, params);
        } catch (Exception ex) {
            LoggerManager.getJdbcLogger().error(() -> "fail to update 'customer checkin record' with '" + customerCheckinRecord + "', err: " + ex.getMessage(), ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        }
    }

    @Override
    public CustomerCheckinRecord get(long id) throws HotelerException {
        var sql = "select id, user_id, room_id, begin_date, end_date from customer_checkin_record where id = ?";
        var params = new Object[] { id };
        try {
            return jdbcTemplate.query(sql, rs -> {
                while (rs.next()) {
                    return getCustomerCheckinRecord(rs);
                }
                return null;
            }, params);
        } catch (Exception ex) {
            LoggerManager.getJdbcLogger().error(() -> "fail to get 'customer checkin record' with id'" + id + "', err: " + ex.getMessage(), ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        }
    }



    @Override
    public void delete(long id) throws HotelerException {
        var sql = "delete from customer_checkin_record where id = ?";
        var params = new Object[] { id };
        try {
            jdbcTemplate.update(sql, params);
        } catch (Exception ex) {
            LoggerManager.getJdbcLogger().error(() -> "fail to delete 'customer checkin record' with id'" + id + "', err: " + ex.getMessage(), ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        }
    }

    @Override
    public List<CustomerCheckinRecord> list() throws HotelerException {
        var sql = " select id, user_id, room_id, begin_date, end_date from customer_checkin_record order by create_dt desc, update_dt desc";
        try {
            return this.jdbcTemplate.query(sql, (rs, i) -> getCustomerCheckinRecord(rs));
        } catch (Exception ex) {
            LoggerManager.getJdbcLogger().error(() -> "fail to list 'customer checkin record'', err: " + ex.getMessage(), ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        }
    }

    @Override
    public List<CustomerCheckinRecord> list(LocalDate date) throws HotelerException {
        var sql = "select id, user_id, room_id, begin_date, end_date from customer_checkin_record where begin_date <= ? and ? <= end_date order by create_dt desc, update_dt desc";
        var params = new Object[] { date, date };
        try {
            return this.jdbcTemplate.query(sql, (rs, i) -> getCustomerCheckinRecord(rs), params);
        } catch (Exception ex) {
            LoggerManager.getJdbcLogger().error(() -> "fail to list 'customer checkin record'', err: " + ex.getMessage(), ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        }
    }

    @Override
    public List<CustomerCheckinRecord> listByRoom(long roomId, LocalDate date) throws HotelerException {
        var sql = "select id, user_id, room_id, begin_date, end_date from customer_checkin_record where room_id = ? and begin_date <= ? and ? <= end_date order by create_dt desc, update_dt desc";
        var params = new Object[] { roomId, date, date };
        try {
            return this.jdbcTemplate.query(sql, (rs, i) -> getCustomerCheckinRecord(rs), params);
        } catch (Exception ex) {
            LoggerManager.getJdbcLogger().error(() -> "fail to list 'customer checkin record'', err: " + ex.getMessage(), ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        }
    }

    private CustomerCheckinRecord getCustomerCheckinRecord(ResultSet rs) throws SQLException {
        return new CustomerCheckinRecord()
                .setId(rs.getLong("id"))
                .setUserId(rs.getLong("user_id"))
                .setRoomId(rs.getLong("room_id"))
                .setBeginDate(rs.getTimestamp("begin_date").toLocalDateTime())
                .setEndDate(rs.getTimestamp("end_date").toLocalDateTime());
    }

    public CustomerCheckinRecordDaoImpl(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setErrorService(IErrorService errorService) {
        this.errorService = errorService;
    }
}
