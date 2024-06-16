package org.daming.hoteler.repository.interceptors;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.daming.hoteler.base.logger.SqlLoggerUtil;
import org.springframework.util.ObjectUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author gming001
 * @version 2024-06-16 09:29
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }),
        @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class }),
        @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class} )
})
public class SqlInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        var in = Instant.now();
        String sql = "";
        Object[] params = new Object[0];
        try {
            Object proceed = invocation.proceed();

            return proceed;
        } catch (Exception ex) {
            if (ObjectUtils.isEmpty(sql)) {
                sql = this.getSqlStatement(invocation);
            }
            if (ObjectUtils.isEmpty(params)) {
                params = this.getSqlStatementParameter(invocation);
            }
            SqlLoggerUtil.logSqlException(sql, params, ex);
            throw ex;
        } finally {
            if (ObjectUtils.isEmpty(sql)) {
                sql = this.getSqlStatement(invocation);
            }
            if (ObjectUtils.isEmpty(params)) {
                params = this.getSqlStatementParameter(invocation);
            }
            SqlLoggerUtil.logSql(sql, params, Duration.between(in, Instant.now()));
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target,this);
    }

    private String getSqlStatement(Invocation invocation) {
        var statement = (MappedStatement)invocation.getArgs()[0];
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }
        var boundSql = statement.getBoundSql(parameter);
        var sql = boundSql.getSql();
        sql = sql.replaceAll("[\\s]+", " ");

        return sql;
    }

    private Object[] getSqlStatementParameter(Invocation invocation) {
        var statement = (MappedStatement)invocation.getArgs()[0];
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }
        var configuration = statement.getConfiguration();
        var boundSql = statement.getBoundSql(parameter);
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> params = boundSql.getParameterMappings();
        List<Object> args = new ArrayList<>();
        if (!ObjectUtils.isEmpty(params) && !ObjectUtils.isEmpty(parameterObject)) {
            var typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                if (parameterObject instanceof Object[]) {
                    Stream.of(parameterObject).forEach(args::add);
                } else {
                    args.add(parameterObject);
                }

            } else {
                for (ParameterMapping param : params) {
                    var propertyName = param.getProperty();
                    var metaObject = configuration.newMetaObject(parameterObject);
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        args.add(obj);
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        args.add(obj);
                    }
                }
            }
        }

        return args.toArray();
    }
}
