package org.daming.hoteler.base.exceptions;

import org.daming.hoteler.base.logger.LoggerManager;

import java.util.Objects;

/**
 * a tool to build execption
 *
 * @author gming001
 * @create 2020-12-13 18:47
 **/
public class ExceptionBuilder {

    public static HotelerException buildException(int code, String message, Throwable clause) {
        HotelerException ex = new HotelerException(code, message, clause);
        if (Objects.isNull(clause)) {
            LoggerManager.getErrorLogger().error("error: code {}, message: {}", code, message);
        } else {
            LoggerManager.getErrorLogger().error("error: code {}, message: {}, cause: {}", code, message, clause);
        }

        return ex;
    }

    public static HotelerException buildException(int code, String message) {
        return buildException(code, message, null);
    }
}
