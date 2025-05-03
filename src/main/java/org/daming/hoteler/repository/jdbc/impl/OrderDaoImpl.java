package org.daming.hoteler.repository.jdbc.impl;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.base.logger.LoggerManager;
import org.daming.hoteler.base.logger.SqlLoggerUtil;
import org.daming.hoteler.constants.ErrorCodeConstants;
import org.daming.hoteler.pojo.Order;
import org.daming.hoteler.pojo.Pageable;
import org.daming.hoteler.pojo.Sortable;
import org.daming.hoteler.pojo.request.OrderListRequest;
import org.daming.hoteler.repository.jdbc.IOrderDao;
import org.daming.hoteler.service.IErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 'order'Dao 默认实现
 *
 * @author gming001
 * @create 2021-03-05 16:20
 **/
@Repository
public class OrderDaoImpl implements IOrderDao {

    private final JdbcTemplate jdbcTemplate;

    private IErrorService errorService;

    @Override
    public void create(Order order) throws HotelerException {
        var in = Instant.now();
        var sql = "insert into orders (id, customer_id, room_id, begin_date, end_date, create_dt, create_user, update_dt, update_user) values ( ?, ?, ?, ?, ?, statement_timestamp(), 'system', statement_timestamp(), 'system')";
        var params = new Object[] { order.getId(), order.getCustomerId(), order.getRoomId(), order.getBeginDate(), order.getEndDate() };
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
    public void update(Order order) throws HotelerException {
        var in = Instant.now();
        var sql = "update orders set customer_id= ?, room_id = ?, begin_date = ?, end_date = ?, update_dt = statement_timestamp(), update_user = 'system' where id = ?";
        var params = new Object[] { order.getCustomerId(), order.getRoomId(), order.getBeginDate(), order.getEndDate(), order.getId() };
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
    public Order get(long id) throws HotelerException {
        var in = Instant.now();
        var sql = "select id, customer_id, room_id, begin_date, end_date from orders where id = ? and deleted_at is null";
        var params = new Object[] { id };
        try {
            return jdbcTemplate.query(sql, rs -> {
                while (rs.next()) {
                    return getOrder(rs);
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
    public void delete(long id) throws HotelerException {
        var sql = "update orders set update_dt = statement_timestamp(), update_user = 'system', deleted_at = statement_timestamp() where id = ?";
        var params = new Object[] { id };
        try {
            jdbcTemplate.update(sql, params);
        } catch (Exception ex) {
            LoggerManager.getJdbcLogger().error(() -> "fail to delete 'order' with id'" + id + "', err: " + ex.getMessage(), ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        }
    }

    @Override
    public List<Order> list() throws HotelerException {
        var sql = "select id, customer_id, room_id, begin_date, end_date from orders where deleted_at is null order by create_dt desc, update_dt desc";
        try {
            return this.jdbcTemplate.query(sql, (rs, i) -> getOrder(rs));
        } catch (Exception ex) {
            LoggerManager.getJdbcLogger().error(() -> "fail to list 'order'', err: " + ex.getMessage(), ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        }
    }

    @Override
    public List<Order> list(OrderListRequest request) throws HotelerException {
        var sql = "select id, customer_id, room_id, begin_date, end_date from orders where deleted_at is null";
        var paramsList = new ArrayList<>();
        if (Objects.nonNull(request.getSort())) {
            sql += " order by ? ";
            paramsList.add(request.getSort());
            if (Objects.nonNull(request.getSortType())) {
                if (request.getSortType().equalsIgnoreCase("asc") || request.getSortType().equalsIgnoreCase("desc")) {
                    sql += "  ? ";
                    paramsList.add(request.getSort());
                }
            }
        }
        if (Objects.nonNull(request.getPage()) && Objects.nonNull(request.getPageSize())) {
            sql += " limit  ? offset ?";
            paramsList.add((request.getPageSize()));
            paramsList.add((request.getPage() - 1) * request.getPageSize());
        }
        var params = paramsList.toArray();
        try {
            return this.jdbcTemplate.query(sql, (rs, i) -> getOrder(rs), params);
        } catch (Exception ex) {
            LoggerManager.getJdbcLogger().error(() -> "fail to list 'order'', err: " + ex.getMessage(), ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        }
    }

    @Override
    public List<Order> list(Pageable pageable) throws HotelerException {
        var sql = " select id, customer_id, room_id, begin_date, end_date from orders where deleted_at is null ";
        if (Objects.nonNull(pageable)) {
            sql += " limit  " + pageable.toLimit() +  " offset " + pageable.toOffset();
        }
        try {
            return this.jdbcTemplate.query(sql, (rs, i) -> getOrder(rs));
        } catch (Exception ex) {
            LoggerManager.getJdbcLogger().error(() -> "fail to list 'order'', err: " + ex.getMessage(), ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        }
    }

    @Override
    public List<Order> list(Pageable pageable, Sortable sortable) throws HotelerException {
        var sql = " select id, customer_id, room_id, begin_date, end_date from orders where deleted_at is null ";
        if (Objects.nonNull(pageable)) {
            sql += " limit  " + pageable.toLimit() +  " offset " + pageable.toOffset();
        }
        if (Objects.nonNull(sortable)) {
            sql += " order by " + sortable.getOrderBy() + " " + sortable.getOrderDir();
        }
        try {
            return this.jdbcTemplate.query(sql, (rs, i) -> getOrder(rs));
        } catch (Exception ex) {
            LoggerManager.getJdbcLogger().error(() -> "fail to list 'order'', err: " + ex.getMessage(), ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        }
    }

    @Override
    public List<Order> list(LocalDate date) throws HotelerException {
        var sql = "select id, customer_id, room_id, begin_date, end_date from orders where begin_date <= ? and ? <= end_date and deleted_at is null order by create_dt desc, update_dt desc";
        var params = new Object[] { date, date };
        try {
            return this.jdbcTemplate.query(sql, (rs, i) -> getOrder(rs), params);
        } catch (Exception ex) {
            LoggerManager.getJdbcLogger().error(() -> "fail to list 'order'', err: " + ex.getMessage(), ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        }
    }

    @Override
    public List<Order> listByRoom(long roomId, LocalDate date) throws HotelerException {
        var sql = "select id, customer_id, room_id, begin_date, end_date from orders where room_id = ? and begin_date <= ? and ? <= end_date and deleted_at is null order by create_dt desc, update_dt desc";
        var params = new Object[] { roomId, date, date };
        try {
            return this.jdbcTemplate.query(sql, (rs, i) -> getOrder(rs), params);
        } catch (Exception ex) {
            LoggerManager.getJdbcLogger().error(() -> "fail to list 'order'', err: " + ex.getMessage(), ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        }
    }

    private Order getOrder(ResultSet rs) throws SQLException {
        return new Order()
                .setId(rs.getLong("id"))
                .setCustomerId(rs.getLong("customer_id"))
                .setRoomId(rs.getLong("room_id"))
                .setBeginDate(rs.getTimestamp("begin_date").toLocalDateTime())
                .setEndDate(rs.getTimestamp("end_date").toLocalDateTime());
    }

    public OrderDaoImpl(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setErrorService(IErrorService errorService) {
        this.errorService = errorService;
    }
}
