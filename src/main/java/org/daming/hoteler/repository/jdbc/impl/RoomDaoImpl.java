package org.daming.hoteler.repository.jdbc.impl;

import org.daming.hoteler.pojo.Room;
import org.daming.hoteler.pojo.enums.RoomStatus;
import org.daming.hoteler.repository.jdbc.IRoomDao;
import org.daming.hoteler.service.IErrorService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    @Override
    public Room get(long id) {
        var sql = "select id, roomname, status, price from rooms where id = ? limit 1";
        var params = new Object[] { id };
        return this.jdbcTemplate.query(sql, (rs) -> {
            while (rs.next()) {
               return this.convertRoomFromResultSet(rs);
            }
            return null;
        }, params);
    }

    @Override
    public List<Room> list(Room room) {
        // var sql = "select id, roomname, status, price from rooms order by create_dt desc, update_dt desc";
        var sql = "select id, roomname, status, price from rooms ";
        var params = new ArrayList<Object>();
        if (Objects.nonNull(room.getStatus())) {
            sql += " where status = ?";
            params.add(room.getStatus().getId());
        }
        sql += " order by create_dt desc, update_dt desc";
        return this.jdbcTemplate.query(sql, (rs, i) -> this.convertRoomFromResultSet(rs), params.toArray());
    }

    @Override
    public List<Room> list() {
        var sql = "select id, roomname, status, price from rooms order by create_dt desc, update_dt desc";
        return this.jdbcTemplate.query(sql, (rs, i) -> this.convertRoomFromResultSet(rs));
    }

    private Room convertRoomFromResultSet(ResultSet rs) throws SQLException {
        var room = new Room()
                .setId(rs.getLong("id"))
                .setRoomname(rs.getString("roomname"))
                .setStatus(RoomStatus.getInstance(rs.getInt("status")))
                .setPrice(rs.getDouble("price"));
        return room;
    }

    public RoomDaoImpl(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }
}
