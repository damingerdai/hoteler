package org.daming.hoteler.pojo.handler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import org.daming.hoteler.pojo.enums.RoomStatus;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Room Status TypeHandler
 *
 * @author gming001
 * @create 2020-12-22 22:49
 **/
@MappedTypes(RoomStatus.class)
public class RoomStatusTypeHandler implements TypeHandler<RoomStatus> {

    @Override
    public void setParameter(PreparedStatement ps, int i, RoomStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getId());
    }

    @Override
    public RoomStatus getResult(ResultSet rs, String columnName) throws SQLException {
        return RoomStatus.getInstance(rs.getInt(columnName));
    }

    @Override
    public RoomStatus getResult(ResultSet rs, int columnIndex) throws SQLException {
        return RoomStatus.getInstance(rs.getInt(columnIndex));
    }

    @Override
    public RoomStatus getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return RoomStatus.getInstance(cs.getInt(columnIndex));
    }
}
