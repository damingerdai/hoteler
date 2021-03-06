package org.daming.hoteler.repository.jdbc.impl;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.base.logger.LoggerManager;
import org.daming.hoteler.constants.ErrorCodeConstants;
import org.daming.hoteler.pojo.Customer;
import org.daming.hoteler.pojo.enums.Gender;
import org.daming.hoteler.repository.jdbc.ICustomerDao;
import org.daming.hoteler.service.IErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
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
        var sql = "insert into customers (id, name, gender, card_id, phone, create_dt, create_user, update_dt, update_user) values ( ?, ?, ?, ?, ?, statement_timestamp(), 'system', statement_timestamp(), 'system')";
        var params = new Object[] { customer.getId(), customer.getName(), customer.getGender().name(), customer.getCardId(), customer.getPhone() };
        try {
            jdbcTemplate.update(sql, params);
        } catch (Exception ex) {
            LoggerManager.getJdbcLogger().error(() -> "fail to update customer with '" + customer + "', err: " + ex.getMessage(), ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        }
    }

    @Override
    public Customer get(long id) throws HotelerException {
        var sql = "select id, name, gender, card_id, phone from customers where id = ? limit 1";
        var params = new Object[] { id };
        try {
            return this.jdbcTemplate.query(sql, (rs) -> {
                while (rs.next()) {
                    return new Customer().setId(rs.getLong("id")).setName(rs.getString("name")).setGender(this.getGender(rs.getString("gender"))).setCardId(rs.getString("card_id")).setPhone(rs.getLong("phone"));
                }
                return null;
            }, params);
        } catch (Exception ex) {
            LoggerManager.getJdbcLogger().error(() -> "fail to get customer with '" + id + "', err: " + ex.getMessage(), ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        }
    }

    @Override
    public List<Customer> list() throws HotelerException {
        var sql = "select id, name, gender, card_id, phone from customers";
        try {
            return this.jdbcTemplate.query(sql, (rs,i) -> new Customer().setId(rs.getLong("id")).setName(rs.getString("name")).setGender(this.getGender(rs.getString("gender"))).setCardId(rs.getString("card_id")).setPhone(rs.getLong("phone")));
        } catch (Exception ex) {
            LoggerManager.getJdbcLogger().error(() -> "fail to list customer, err: " + ex.getMessage(), ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        }
    }

    @Override
    public void delete(long id) throws HotelerException {
        var sql = "delete from customers where id = ?";
        var params = new Object[] { id };
        try {
            this.jdbcTemplate.update(sql, params);
        } catch (Exception ex) {
            LoggerManager.getJdbcLogger().error(() -> "fail to list customer, err: " + ex.getMessage(), ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        }
    }

    @Override
    public void update(Customer customer) throws HotelerException {
        var sql = "update customers set name = ?, gender = ?, card_id = ?, phone = ?, create_dt = statement_timestamp(), create_user = 'system', update_dt = statement_timestamp(), update_user = 'system' where id = ?";
        var params = new Object[] { customer.getName(), customer.getGender().name(), customer.getCardId(), customer.getPhone(), customer.getId() };
        try {
            this.jdbcTemplate.update(sql, params);
        } catch (Exception ex) {
            LoggerManager.getJdbcLogger().error(() -> "fail to list customer, err: " + ex.getMessage(), ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
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
