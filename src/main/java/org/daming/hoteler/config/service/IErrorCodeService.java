package org.daming.hoteler.config.service;

import org.daming.hoteler.base.exceptions.HotelerException;

/**
 * error code service
 *
 * @author gming001
 * @create 2020-12-25 20:08
 **/
public interface IErrorCodeService {

    String getMessage(int code) throws HotelerException;

    String getMessage(int code, Object...params) throws HotelerException;
}
