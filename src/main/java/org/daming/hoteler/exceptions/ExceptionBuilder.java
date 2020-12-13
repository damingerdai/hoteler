package org.daming.hoteler.exceptions;

/**
 * a tool to build execption
 *
 * @author gming001
 * @create 2020-12-13 18:47
 **/
public class ExceptionBuilder {

    public static HotelerException buildException(int code, String message, Throwable clause) {
        HotelerException ex = new HotelerException(code, message, clause);
        return ex;
    }

    public static HotelerException buildException(int code, String message) {
        return buildException(code, message, null);
    }
}
