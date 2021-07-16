package org.daming.hoteler.security;

import org.daming.hoteler.base.exceptions.HotelerException;

/**
 * 加密
 */
public interface Encryptable {

    /**
     * 加密
     * @param content
     * @return
     * @throws HotelerException
     */
    String encrypt(final String content) throws HotelerException;
}
