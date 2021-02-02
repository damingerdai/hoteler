package org.daming.hoteler.repository.jdbc.impl;

import org.daming.hoteler.pojo.Room;
import org.daming.hoteler.pojo.enums.RoomStatus;
import org.daming.hoteler.repository.jdbc.IRoomDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
        return this.jdbcTemplate.query(sql, params, (rs) -> {
            while (rs.next()) {
               this.convertRoomFromResultSet(rs);
            }
            return null;
        });
    }

    @Override
    public List<Room> list() {
        var sql = "select id, roomname, status, price from rooms order by create_dt desc, update_dt desc";
        return this.jdbcTemplate.query(sql, (rs, i) -> this.convertRoomFromResultSet(rs));
    }

    private Room convertRoomFromResultSet(ResultSet rs) throws SQLException {
        return new Room()
                .setId(rs.getLong("id"))
                .setRoomname(rs.getString("roomname"))
                .setStatus(RoomStatus.getInstance(rs.getInt("status")))
                .setPrice(rs.getDouble("price"));
    }

    public RoomDaoImpl(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }
}
