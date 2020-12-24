package org.daming.hoteler.base.logger;

import org.slf4j.Logger;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * hoteler logger warped by slf4j logger
 *
 * @author gming001
 * @create 2020-12-24 19:14
 **/
public class HotelerLogger {

    private final Logger logger;

    public void trace(String message) {
        if (logger.isTraceEnabled()) {
            logger.trace(message);
        }
    }

    public void trace(String pattern, Object...params) {
        if (logger.isTraceEnabled()) {
            logger.trace(pattern, params);
        }
    }

    public void trace(String message, Throwable cause) {
        if (logger.isTraceEnabled()) {
            logger.trace(message, cause);
        }
    }

    public void trace(Supplier<String> supplier) {
        if (logger.isTraceEnabled()) {
            logger.trace(nullSafeGet(supplier));
        }
    }

    public void trace(Supplier<String> supplier, Throwable cause) {
        if (logger.isTraceEnabled()) {
            logger.trace(nullSafeGet(supplier), cause);
        }
    }

    public void trace(Supplier<String> messageSupplier, Supplier<Throwable> causeSupplier) {
        if (logger.isTraceEnabled()) {
            logger.trace(nullSafeGet((messageSupplier)), nullSafeGet(causeSupplier));
        }
    }

    public void debug(String message) {
        if (logger.isDebugEnabled()) {
            logger.debug(message);
        }
    }

    public void debug(String pattern, Object...params) {
        if (logger.isDebugEnabled()) {
            logger.debug(pattern, params);
        }
    }

    public void debug(String message, Throwable cause) {
        if (logger.isDebugEnabled()) {
            logger.debug(message, cause);
        }
    }

    public void debug(Supplier<String> supplier) {
        if (logger.isDebugEnabled()) {
            logger.debug(nullSafeGet(supplier));
        }
    }

    public void debug(Supplier<String> supplier, Throwable cause) {
        if (logger.isDebugEnabled()) {
            logger.debug(nullSafeGet(supplier), cause);
        }
    }

    public void debug(Supplier<String> messageSupplier, Supplier<Throwable> causeSupplier) {
        if (logger.isDebugEnabled()) {
            logger.debug(nullSafeGet((messageSupplier)), nullSafeGet(causeSupplier));
        }
    }

    public void info(String message) {
        if (logger.isInfoEnabled()) {
            logger.info(message);
        }
    }

    public void info(String pattern, Object...params) {
        if (logger.isInfoEnabled()) {
            logger.info(pattern, params);
        }
    }

    public void info(String message, Throwable cause) {
        if (logger.isInfoEnabled()) {
            logger.info(message, cause);
        }
    }

    public void info(Supplier<String> supplier) {
        if (logger.isInfoEnabled()) {
            logger.info(nullSafeGet(supplier));
        }
    }

    public void info(Supplier<String> supplier, Throwable cause) {
        if (logger.isInfoEnabled()) {
            logger.info(nullSafeGet(supplier), cause);
        }
    }

    public void info(Supplier<String> messageSupplier, Supplier<Throwable> causeSupplier) {
        if (logger.isInfoEnabled()) {
            logger.info(nullSafeGet((messageSupplier)), nullSafeGet(causeSupplier));
        }
    }

    public void warn(String message) {
        if (logger.isWarnEnabled()) {
            logger.warn(message);
        }
    }

    public void warn(String pattern, Object...params) {
        if (logger.isWarnEnabled()) {
            logger.warn(pattern, params);
        }
    }

    public void warn(String message, Throwable cause) {
        if (logger.isWarnEnabled()) {
            logger.warn(message, cause);
        }
    }

    public void warn(Supplier<String> supplier) {
        if (logger.isWarnEnabled()) {
            logger.warn(nullSafeGet(supplier));
        }
    }

    public void warn(Supplier<String> supplier, Throwable cause) {
        if (logger.isWarnEnabled()) {
            logger.warn(nullSafeGet(supplier), cause);
        }
    }

    public void warn(Supplier<String> messageSupplier, Supplier<Throwable> causeSupplier) {
        if (logger.isWarnEnabled()) {
            logger.warn(nullSafeGet((messageSupplier)), nullSafeGet(causeSupplier));
        }
    }

    public void error(String message) {
        if (logger.isErrorEnabled()) {
            logger.error(message);
        }
    }

    public void error(String pattern, Object...params) {
        if (logger.isErrorEnabled()) {
            logger.error(pattern, params);
        }
    }

    public void error(String message, Throwable cause) {
        if (logger.isErrorEnabled()) {
            logger.error(message, cause);
        }
    }

    public void error(Supplier<String> supplier) {
        if (logger.isErrorEnabled()) {
            logger.error(nullSafeGet(supplier));
        }
    }

    public void error(Supplier<String> supplier, Throwable cause) {
        if (logger.isErrorEnabled()) {
            logger.error(nullSafeGet(supplier), cause);
        }
    }

    public void error(Supplier<String> messageSupplier, Supplier<Throwable> causeSupplier) {
        if (logger.isErrorEnabled()) {
            logger.error(nullSafeGet((messageSupplier)), nullSafeGet(causeSupplier));
        }
    }


    private <T> T nullSafeGet(Supplier<T> supplier) {
        return Objects.nonNull(supplier) ? supplier.get() : null;
    }


    public HotelerLogger(Logger logger) {
        super();
        this.logger = logger;
    }
}
