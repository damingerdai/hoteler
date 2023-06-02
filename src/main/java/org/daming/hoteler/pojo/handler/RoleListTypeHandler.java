package org.daming.hoteler.pojo.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.daming.hoteler.base.logger.LoggerManager;
import org.daming.hoteler.pojo.Role;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author gming001
 * @version 2023-06-02 13:50
 */
@MappedJdbcTypes({JdbcType.ARRAY, JdbcType.CHAR, JdbcType.VARCHAR})
@MappedTypes(JsonListTypeHandler.class)

public class RoleListTypeHandler extends BaseTypeHandler<List<Role>> {

    private final ObjectMapper jsonMapper;

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<Role> parameter, JdbcType jdbcType) throws SQLException {
        try {
            ps.setString(i, jsonMapper.writeValueAsString(i));
        } catch (JsonProcessingException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public List<Role> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        var json = rs.getString(columnName);
        LoggerManager.getJdbcLogger().debug("praise: " +json);
        try {
            var javaType = jsonMapper.getTypeFactory().constructParametricType(List.class, Role.class);
            return jsonMapper.readValue(json, javaType);
        } catch (Exception ex) {
            ex.printStackTrace();
            return List.of();
        }
    }

    @Override
    public List<Role> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        var json = rs.getString(columnIndex);
        LoggerManager.getJdbcLogger().debug("praise: " +json);
        try {
            var javaType = jsonMapper.getTypeFactory().constructParametricType(List.class, Role.class);
            return jsonMapper.readValue(json, javaType);
        } catch (Exception ex) {
            ex.printStackTrace();
            return List.of();
        }
    }

    @Override
    public List<Role> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        var json = cs.getString(columnIndex);
        LoggerManager.getJdbcLogger().debug("praise: " +json);
        try {
            var javaType = jsonMapper.getTypeFactory().constructParametricType(List.class, Role.class);
            return jsonMapper.readValue(json, javaType);
        } catch (Exception ex) {
            ex.printStackTrace();
            return List.of();
        }
    }

    public RoleListTypeHandler() {
        this.jsonMapper = new ObjectMapper();
    }
}
