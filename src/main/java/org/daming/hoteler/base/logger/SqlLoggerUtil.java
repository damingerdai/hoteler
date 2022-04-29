package org.daming.hoteler.base.logger;

import org.daming.hoteler.base.context.ThreadLocalContextHolder;
import org.daming.hoteler.utils.CommonUtils;

import java.time.Duration;
import java.util.UUID;

/**
 * @author gming001
 * @version 2022-04-29 19:50
 */
public class SqlLoggerUtil {

    private static final HotelerLogger sqlLogger = LoggerManager.getJdbcLogger();

    private static final String SQL_LOGGER_QUERY =  "RequestId: [%s], Sql: [%s], Params: [%s]";

    private static final String SQL_LOGGER_ERROR =  "RequestId: [%s], Sql: [%s], Params: [%s], Error: [%s]";

    private static final String SQL_LOGGER_QUERY_MS =  "RequestId: [%s], Sql: [%s], Params: [%s], MS:[%d]";

    public static void logSqlException(String sql, Object[] params, Exception e) {
        String requestId = null;
        if (ThreadLocalContextHolder.get() != null) {
            requestId = ThreadLocalContextHolder.get().getRequestId();
        }
        if (requestId == null) {
            requestId = UUID.randomUUID().toString();
        }
        sqlLogger.info(String.format(SQL_LOGGER_ERROR, requestId, sql, getParamsString(params), e.getMessage()));
    }

    public static void logSql(String sql, Object[] params, Duration duration) {
        String requestId = null;
        if (ThreadLocalContextHolder.get() != null) {
            requestId = ThreadLocalContextHolder.get().getRequestId();
        }
        if (requestId == null) {
            requestId = UUID.randomUUID().toString();
        }
        sqlLogger.info(String.format(SQL_LOGGER_QUERY_MS, requestId, sql, getParamsString(params), duration.toMillis()));
    }

    public static void logSql(String sql, Object[] params) {
        String requestId = null;
        if (ThreadLocalContextHolder.get() != null) {
            requestId = ThreadLocalContextHolder.get().getRequestId();
        }
        if (requestId == null) {
            requestId = UUID.randomUUID().toString();
        }
        sqlLogger.info(String.format(SQL_LOGGER_QUERY, requestId, sql, getParamsString(params)));
    }

    private static String getParamsString(Object[] params) {
        StringBuilder sb = new StringBuilder();
        if (!CommonUtils.isEmpty(params)) {
            for (Object param : params) {
                if (param != null) {
                    sb.append(param);
                } else {
                    sb.append("null");
                }
                sb.append(",");
            }

            return sb.substring(0, sb.length() - 1);
        } else {
            return sb.toString();
        }

    }
}
