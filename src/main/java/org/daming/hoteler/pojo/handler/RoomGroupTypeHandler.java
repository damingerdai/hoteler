package org.daming.hoteler.pojo.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.daming.hoteler.pojo.converter.RoomGroupConverter;
import org.daming.hoteler.pojo.enums.RoomGroupType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(RoomGroupType.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class RoomGroupTypeHandler extends BaseTypeHandler<RoomGroupType> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, RoomGroupType parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, RoomGroupConverter.toDbValue(parameter));
    }

    @Override
    public RoomGroupType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return RoomGroupConverter.toEnum(rs.getString(columnName));
    }

    @Override
    public RoomGroupType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return RoomGroupConverter.toEnum(rs.getString(columnIndex));
    }

    @Override
    public RoomGroupType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return RoomGroupConverter.toEnum(cs.getString(columnIndex));
    }
}
