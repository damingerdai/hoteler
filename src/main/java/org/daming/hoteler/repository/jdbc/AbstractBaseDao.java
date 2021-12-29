package org.daming.hoteler.repository.jdbc;

import org.daming.hoteler.base.logger.LoggerManager;
import org.daming.hoteler.constants.ErrorCodeConstants;
import org.daming.hoteler.service.IErrorService;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 通用dao
 *
 * @author gming001
 * @create 2021-03-05 16:08
 **/
public abstract class AbstractBaseDao<T> {

    protected JdbcTemplate jdbcTemplate;
    protected IErrorService errorService;

    public void create(String sql, Object...params) {
        try {
            this.getJdbcTemplate().update(sql, params);
        } catch (Exception ex) {
            // LoggerManager.getJdbcLogger().error(() -> "fail to update customer with '" + customer + "', err: " + ex.getMessage(), ex);
            throw this.getErrorService().createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        }
    }

    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    public IErrorService getErrorService() {
        return this.errorService;
    }
}
