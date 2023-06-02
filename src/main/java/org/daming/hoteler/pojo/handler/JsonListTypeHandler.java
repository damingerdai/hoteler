package org.daming.hoteler.pojo.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.daming.hoteler.base.logger.LoggerManager;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @see https://github.com/wangkezun/mybatis-json-typehandler/blob/master/src/main/kotlin/io/wkz/kotlin/mybatis/JsonListTypeHandler.kt
 * @author gming001
 * @version 2023-06-02 11:13
 */
@MappedJdbcTypes({JdbcType.ARRAY, JdbcType.CHAR, JdbcType.VARCHAR})
@MappedTypes(JsonListTypeHandler.class)
public class JsonListTypeHandler<T> extends BaseTypeHandler<List<T>> {

    private final ObjectMapper jsonMapper;

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<T> parameter, JdbcType jdbcType) throws SQLException {
        try {
            ps.setString(i, jsonMapper.writeValueAsString(i));
        } catch (JsonProcessingException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public List<T> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        var json = rs.getString(columnName);
        LoggerManager.getJdbcLogger().debug(json);
        try {
            return jsonMapper.readValue(json, new TypeReference<List<T>>() {});
        } catch (Exception ex) {
            ex.printStackTrace();
            return List.of();
        }
    }

    @Override
    public List<T> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        var json = rs.getString(columnIndex);
        LoggerManager.getJdbcLogger().debug(json);
        try {
            return jsonMapper.readValue(json, new TypeReference<List<T>>() {});
        } catch (Exception ex) {
            ex.printStackTrace();
            return List.of();
        }
    }

    @Override
    public List<T> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        var json = cs.getString(columnIndex);
        LoggerManager.getJdbcLogger().debug(json);
        try {
            return jsonMapper.readValue(json, new TypeReference<List<T>>() {});
        } catch (Exception ex) {
            ex.printStackTrace();
            return List.of();
        }
    }

    public JsonListTypeHandler() {
        this.jsonMapper = new ObjectMapper();
    }
}
