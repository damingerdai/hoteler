package org.daming.hoteler.repository.jdbc.impl;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.base.logger.LoggerManager;
import org.daming.hoteler.base.logger.SqlLoggerUtil;
import org.daming.hoteler.constants.ErrorCodeConstants;
import org.daming.hoteler.pojo.Customer;
import org.daming.hoteler.pojo.enums.Gender;
import org.daming.hoteler.repository.jdbc.ICustomerDao;
import org.daming.hoteler.service.IErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.time.Duration;
import java.time.Instant;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * customer dao impl
 *
 * @author gming001
 * @create 2020-12-25 15:52
 **/
@Repository
public class CustomerDaoImpl implements ICustomerDao {

    private JdbcTemplate jdbcTemplate;

    private IErrorService errorService;

    private EnumMap<Gender, String> map;

    @Autowired
    public void setErrorService(IErrorService errorService) {
        this.errorService = errorService;
    }

    @Override
    public void create(Customer customer) throws HotelerException {
        var in = Instant.now();
        var sql = "insert into customers (id, name, gender, card_id, phone, create_dt, create_user, update_dt, update_user) values ( ?, ?, ?, ?, ?, statement_timestamp(), 'system', statement_timestamp(), 'system')";
        var params = new Object[] { customer.getId(), customer.getName(), customer.getGender().name(), customer.getCardId(), customer.getPhone() };
        try {
            jdbcTemplate.update(sql, params);
        } catch (Exception ex) {
            SqlLoggerUtil.logSqlException(sql, params, ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        } finally {
            SqlLoggerUtil.logSql(sql, params, Duration.between(in, Instant.now()));
        }
    }

    @Override
    public Customer get(long id) throws HotelerException {
        var in = Instant.now();
        var sql = "select id, name, gender, card_id, phone from customers where id = ? and deleted_at is null limit 1";
        var params = new Object[] { id };
        try {
            return this.jdbcTemplate.query(sql, (rs) -> {
                while (rs.next()) {
                    return new Customer().setId(rs.getLong("id")).setName(rs.getString("name")).setGender(this.getGender(rs.getString("gender"))).setCardId(rs.getString("card_id")).setPhone(rs.getLong("phone"));
                }
                return null;
            }, params);
        } catch (Exception ex) {
            SqlLoggerUtil.logSqlException(sql, params, ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        } finally {
            SqlLoggerUtil.logSql(sql, params, Duration.between(in, Instant.now()));
        }
    }

    @Override
    public List<Customer> list() throws HotelerException {
        var in = Instant.now();
        var sql = "select id, name, gender, card_id, phone from customers where deleted_at is null";
        try {
            return this.jdbcTemplate.query(sql, (rs,i) -> new Customer().setId(rs.getLong("id")).setName(rs.getString("name")).setGender(this.getGender(rs.getString("gender"))).setCardId(rs.getString("card_id")).setPhone(rs.getLong("phone")));
        } catch (Exception ex) {
            SqlLoggerUtil.logSqlException(sql, null, ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        } finally {
            SqlLoggerUtil.logSql(sql, null, Duration.between(in, Instant.now()));
        }
    }

    @Override
    public void delete(long id) throws HotelerException {
        var in = Instant.now();
        var sql = "update customers set update_dt = statement_timestamp(), update_user = 'system', deleted_at = statement_timestamp() where id = ?";
        var params = new Object[] { id };
        try {
            this.jdbcTemplate.update(sql, params);
        } catch (Exception ex) {
            SqlLoggerUtil.logSqlException(sql, params, ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        } finally {
            SqlLoggerUtil.logSql(sql, params, Duration.between(in, Instant.now()));
        }
    }

    @Override
    public void update(Customer customer) throws HotelerException {
        var in = Instant.now();
        var sql = "update customers set name = ?, gender = ?, card_id = ?, phone = ?, update_dt = statement_timestamp(), update_user = 'system' where id = ?";
        var params = new Object[] { customer.getName(), customer.getGender().name(), customer.getCardId(), customer.getPhone(), customer.getId() };
        try {
            this.jdbcTemplate.update(sql, params);
        } catch (Exception ex) {
            SqlLoggerUtil.logSqlException(sql, params, ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        } finally {
            SqlLoggerUtil.logSql(sql, params, Duration.between(in, Instant.now()));
        }
    }

    private Gender getGender(String value) {
        return this.map.entrySet().stream()
                .filter(e -> value.equalsIgnoreCase(e.getValue()))
                .map(Map.Entry::getKey).findFirst().orElse(Gender.N);
    }

    public CustomerDaoImpl(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    private void init() {
        this.map = new EnumMap<>(Gender.class);
        this.map.put(Gender.F, "F");
        this.map.put(Gender.M, "M");
        this.map.put(Gender.N, "N");
    }
}
