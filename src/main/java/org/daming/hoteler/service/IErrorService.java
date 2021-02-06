package org.daming.hoteler.service;

import org.daming.hoteler.base.exceptions.HotelerException;

/**
 * error service
 *
 * @author gming001
 * @create 2020-12-25 21:44
 **/
public interface IErrorService {

    HotelerException createHotelerException(int code, Object[] params, Throwable cause);

    HotelerException createHotelerSystemException(String message, Throwable cause);

    HotelerException createHotelerException(int code, Throwable cause);

    HotelerException createHotelerException(int code);

    HotelerException createSqlHotelerException(Exception ex, Object...params);
}
