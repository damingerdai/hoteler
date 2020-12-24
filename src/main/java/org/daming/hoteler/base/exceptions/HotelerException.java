package org.daming.hoteler.base.exceptions;

import java.util.StringJoiner;

public class HotelerException extends RuntimeException {

    private static final long serialVersionUID = 5720403538911447986L;

    private int code;

    private String message;

    private Throwable cause;

    public int getCode() {
        return code;
    }

    public HotelerException setCode(int code) {
        this.code = code;
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HotelerException setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    public HotelerException setCause(Throwable cause) {
        this.cause = cause;
        return this;
    }

    public HotelerException() {
        super();
    }

    public HotelerException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public HotelerException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
        this.cause = cause;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", HotelerException.class.getSimpleName() + "[", "]")
                .add("code=" + code)
                .add("message='" + message + "'")
                .add("cause=" + cause)
                .toString();
    }
}
