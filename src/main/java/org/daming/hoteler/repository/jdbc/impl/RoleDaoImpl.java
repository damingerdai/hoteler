package org.daming.hoteler.repository.jdbc.impl;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.constants.ErrorCodeConstants;
import org.daming.hoteler.pojo.Role;
import org.daming.hoteler.pojo.Room;
import org.daming.hoteler.pojo.enums.RoomStatus;
import org.daming.hoteler.repository.jdbc.AbstractBaseDao;
import org.daming.hoteler.repository.jdbc.IRoleDao;
import org.daming.hoteler.service.IErrorService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author gming001
 * @since 2021-12-29 19:23
 **/
@Repository
public class RoleDaoImpl extends AbstractBaseDao<Role> implements IRoleDao {

    @Override
    public List<Role> list() throws HotelerException {
        var sql = "SELECT id, name, description FROM roles";
        try {
            return this.jdbcTemplate.query(sql,(rs, i) -> this.convertRoleFromResultSet(rs));
        } catch (Exception ex) {
            throw this.getErrorService().createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        }
    }

    @Override
    public List<Role> list(List<Long> ids) throws HotelerException {
        var sql = "SELECT id, name, description FROM roles WHERE id = ANY(?::bigint[])";
        var params = new Object[] { ids.toArray(new Long[0]) };
        try {
            return this.jdbcTemplate.query(sql,(rs, i) -> this.convertRoleFromResultSet(rs),  params);
        } catch (Exception ex) {
            throw this.getErrorService().createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        }
    }

    @Override
    public Role get(long id) throws HotelerException {
        var sql = "SELECT id, name, description FROM roles WHERE id = ?";
        var params = new Object[] { id };
        try {
            return this.jdbcTemplate.query(sql, (rs) -> {
                while (rs.next()) {
                    return this.convertRoleFromResultSet(rs);
                }
                return null;
            }, params);
        } catch (Exception ex) {
            throw this.getErrorService().createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        }
    }

    private Role convertRoleFromResultSet(ResultSet rs) throws SQLException {
        var role = new Role()
                .setId(rs.getLong("id"))
                .setName(rs.getString("name"))
                .setDescription(rs.getString("description"));

        return role;
    }

    public RoleDaoImpl(JdbcTemplate jdbcTemplate,IErrorService errorService) {
        this.jdbcTemplate = jdbcTemplate;
        this.errorService = errorService;
    }
}
