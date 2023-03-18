package org.daming.hoteler.base.exceptions;

/**
 * @author daming
 * @version 2023-03-18 09:45
 **/
public class RateLimitException extends HotelerException {

    public RateLimitException(int code, String message) {
        super(code, message);
    }

    public RateLimitException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
