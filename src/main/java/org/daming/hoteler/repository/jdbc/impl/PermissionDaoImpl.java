package org.daming.hoteler.repository.jdbc.impl;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.base.logger.SqlLoggerUtil;
import org.daming.hoteler.constants.ErrorCodeConstants;
import org.daming.hoteler.pojo.Permission;
import org.daming.hoteler.repository.jdbc.AbstractBaseDao;
import org.daming.hoteler.repository.jdbc.IPermissionDao;
import org.daming.hoteler.service.IErrorService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class PermissionDaoImpl extends AbstractBaseDao<Permission> implements IPermissionDao {

    private JdbcTemplate jdbcTemplate;

    private IErrorService errorService;

    @Override
    public Optional<Permission> get(String id) throws HotelerException {
        var in = Instant.now();
        var sql = "SELECT id, name, description FROM permissions WHERE id = ?";
        var params = new Object[] { id };
        try {
            var permission = this.jdbcTemplate.query(sql,(rs) -> {
                var p = new Permission();
                p.setId(rs.getString("id"));
                p.setName(rs.getString("name"));
                p.setDescription(rs.getString("description"));
                return p;
            }, params);
            return Optional.ofNullable(permission);
        } catch (Exception ex) {
            SqlLoggerUtil.logSqlException(sql, params, ex);
            throw this.getErrorService().createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        } finally {
            SqlLoggerUtil.logSql(sql, params, Duration.between(in, Instant.now()));
        }
    }

    @Override
    public List<Permission> list() throws HotelerException {
        var in = Instant.now();
        var sql = "SELECT id, name, description FROM permissions";
        try {
            return this.jdbcTemplate.query(sql,(rs, i) -> {
                var p = new Permission();
                p.setId(rs.getString("id"));
                p.setName(rs.getString("name"));
                p.setDescription(rs.getString("description"));
                return p;
            });
        } catch (Exception ex) {
            SqlLoggerUtil.logSqlException(sql, null, ex);
            throw this.getErrorService().createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        } finally {
            SqlLoggerUtil.logSql(sql, null, Duration.between(in, Instant.now()));
        }
    }

    @Override
    public List<Permission> listByRoleId(long roleId) throws HotelerException {
        return this.doListByRoleId(roleId);
    }

    @Override
    public List<Permission> listByRoleId(long... roleIds) throws HotelerException {
        return this.doListByRoleId(roleIds);
    }

    protected List<Permission> doListByRoleId(long... roleIds) throws HotelerException {
        var in = Instant.now();
        var sql = "SELECT p.id, p.name, p.description FROM permissions p JOIN role_permissions rp ON p.id = rp.permission_id JOIN roles r ON r.id = rp.role_id AND r.id = ANY(?::bigint[]);";
        var params = Stream.of(roleIds).toArray();
        try {
            return this.jdbcTemplate.query(sql,(rs, i) -> {
                var p = new Permission();
                p.setId(rs.getString("id"));
                p.setName(rs.getString("name"));
                p.setDescription(rs.getString("description"));
                return p;
            }, params);
        } catch (Exception ex) {
            SqlLoggerUtil.logSqlException(sql, params, ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        } finally {
            SqlLoggerUtil.logSql(sql, params, Duration.between(in, Instant.now()));
        }
    }

    private java.sql.Array createSqlArray(long... roleIds) throws SQLException {
        return Objects.requireNonNull(this.jdbcTemplate.getDataSource()).getConnection().createArrayOf("bigint",  Stream.of(roleIds).toArray());
    }



    public PermissionDaoImpl(JdbcTemplate jdbcTemplate,IErrorService errorService) {
        this.jdbcTemplate = jdbcTemplate;
        this.errorService = errorService;
    }

}
