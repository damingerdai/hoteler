package org.daming.hoteler.repository.jdbc.impl;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.base.logger.SqlLoggerUtil;
import org.daming.hoteler.pojo.Pageable;
import org.daming.hoteler.pojo.Room;
import org.daming.hoteler.pojo.Sortable;
import org.daming.hoteler.pojo.enums.RoomStatus;
import org.daming.hoteler.repository.jdbc.IRoomDao;
import org.daming.hoteler.service.IErrorService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * room dao implement
 *
 * @author gming001
 * @create 2020-12-23 12:43
 **/
@Repository
public class RoomDaoImpl implements IRoomDao {

    private JdbcTemplate jdbcTemplate;
    private IErrorService errorService;

    @Override
    public Room get(long id) {
        var in = Instant.now();
        var sql = "select id, roomname, status, price from rooms where id = ? and deleted_at is null limit 1";
        var params = new Object[] { id };
        try {
            return this.jdbcTemplate.query(sql, (rs) -> {
                while (rs.next()) {
                    return this.convertRoomFromResultSet(rs);
                }
                return null;
            }, params);
        } catch (Exception ex) {
            SqlLoggerUtil.logSqlException(sql, params, ex);
            throw this.errorService.createSqlHotelerException(ex, sql);
        } finally {
            SqlLoggerUtil.logSql(sql, params, Duration.between(in, Instant.now()));
        }

    }

    @Override
    public List<Room> list(Room room) {
        var in = Instant.now();
        var sql = "select id, roomname, status, price from rooms";
        var params = new ArrayList<>();
        if (Objects.nonNull(room.getStatus())) {
            sql += " where status = ? and deleted_at is null";
            params.add(room.getStatus().getId());
        } else {
            sql += " where deleted_at is null";
        }
        sql += " order by create_dt desc, update_dt desc";
        try {
            return this.jdbcTemplate.query(sql, (rs, i) -> this.convertRoomFromResultSet(rs), params.toArray());
        } catch (Exception ex) {
            SqlLoggerUtil.logSqlException(sql, params.toArray(), ex);
            throw this.errorService.createSqlHotelerException(ex, sql);
        } finally {
            SqlLoggerUtil.logSql(sql, params.toArray(), Duration.between(in, Instant.now()));
        }
    }

    @Override
    public List<Room> list(Room room, Pageable pageable, Sortable sortable) throws HotelerException {
        var in = Instant.now();
        var sql = "select id, roomname, status, price from rooms ";
        var paramsList = new ArrayList<>();
        if (Objects.isNull(room)) {
            sql += " where deleted_at is null";
        } else {
            var whereSql = new ArrayList<String>(2);
            whereSql.add("deleted_at is null");
            if (Objects.nonNull(room.getStatus())) {
                whereSql.add(" status = ?");
                paramsList.add(room.getStatus());
            }
            sql += " where " + String.join(" and ", whereSql);
        }
        if (Objects.nonNull(pageable)) {
            sql += " limit  " + pageable.toLimit() +  " offset " + pageable.toOffset();
        }
        if (Objects.nonNull(sortable)) {
            sql += " order by " + sortable.getOrderBy() + " " + sortable.getOrderDir();
        } else {
            sql += "order by create_dt desc, update_dt desc";
        }
        var params = paramsList.toArray();
        try {
            return this.jdbcTemplate.query(sql, (rs, i) -> this.convertRoomFromResultSet(rs), params);
        } catch (Exception ex) {
            SqlLoggerUtil.logSqlException(sql, null, ex);
            throw this.errorService.createSqlHotelerException(ex, sql);
        } finally {
            SqlLoggerUtil.logSql(sql, null, Duration.between(in, Instant.now()));
        }
    }

    @Override
    public List<Room> list() {
        var in = Instant.now();
        var sql = "select id, roomname, status, price from rooms where deleted_at is null order by create_dt desc, update_dt desc";
        try {
            return this.jdbcTemplate.query(sql, (rs, i) -> this.convertRoomFromResultSet(rs));
        } catch (Exception ex) {
            SqlLoggerUtil.logSqlException(sql, null, ex);
            throw this.errorService.createSqlHotelerException(ex, sql);
        } finally {
            SqlLoggerUtil.logSql(sql, null, Duration.between(in, Instant.now()));
        }

    }

    private Room convertRoomFromResultSet(ResultSet rs) throws SQLException {
        var room = new Room()
                .setId(rs.getLong("id"))
                .setRoomname(rs.getString("roomname"))
                .setStatus(RoomStatus.getInstance(rs.getInt("status")))
                .setPrice(rs.getDouble("price"));
        return room;
    }

    public RoomDaoImpl(JdbcTemplate jdbcTemplate, IErrorService errorService) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        this.errorService = errorService;
    }
}
