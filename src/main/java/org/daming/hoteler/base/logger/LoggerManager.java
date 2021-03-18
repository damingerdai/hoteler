package org.daming.hoteler.base.logger;

import org.daming.hoteler.constants.CommonConstants;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * hoteler logger manager
 *
 * @author gming001
 * @create 2020-12-24 19:29
 **/
public class LoggerManager {

    private static Map<String, HotelerLogger> cache;

    static {
        cache = new HashMap<>();
    }

    public static HotelerLogger getCommonLogger() {
        return getLogger(CommonConstants.LOGGER_NAME_COMMON);
    }

    public static HotelerLogger getErrorLogger() {
        return getLogger(CommonConstants.LOGGER_NAME_ERROR);
    }

    public static HotelerLogger getApiLogger() {
        return getLogger(CommonConstants.LOGGER_NAME_API);
    }

    public static HotelerLogger getJdbcLogger() {
        return getLogger(CommonConstants.LOGGER_NAME_JDBC);
    }

    public static HotelerLogger getJobLogger() {
        return getLogger(CommonConstants.LOGGER_NAME_JOB);
    }

    public static HotelerLogger getLogger(String loggerName) {
        return cache.computeIfAbsent(loggerName, LoggerManager::buildLogger);
    }

    private static HotelerLogger buildLogger(String loggerName) {
        return new HotelerLogger(LoggerFactory.getLogger(loggerName));
    }
}
