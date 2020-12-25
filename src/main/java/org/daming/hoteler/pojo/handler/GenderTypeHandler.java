package org.daming.hoteler.pojo.handler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import org.daming.hoteler.pojo.enums.Gender;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumMap;
import java.util.Map;

/**
 * gender and db type handler
 *
 * @author gming001
 * @create 2020-12-25 15:29
 **/
@MappedJdbcTypes({JdbcType.CHAR})
@MappedTypes(GenderTypeHandler.class)
public class GenderTypeHandler implements TypeHandler<Gender> {

    private EnumMap<Gender, String> map;

    @Override
    public void setParameter(PreparedStatement ps, int i, Gender gender, JdbcType jdbcType) throws SQLException {
        ps.setString(i, map.get(gender));
    }

    @Override
    public Gender getResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return this.getGender(value);
    }

    @Override
    public Gender getResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return this.getGender(value);
    }

    @Override
    public Gender getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return this.getGender(value);
    }

    private Gender getGender(String value) {
        return this.map.entrySet().stream()
                .filter(e -> value.equalsIgnoreCase(e.getValue()))
                .map(Map.Entry::getKey).findFirst().orElse(Gender.N);
    }

    public GenderTypeHandler() {
        super();
        this.init();
    }

    private void init() {
        this.map = new EnumMap<>(Gender.class);
        this.map.put(Gender.F, "F");
        this.map.put(Gender.M, "M");
        this.map.put(Gender.N, "N");
    }
}
