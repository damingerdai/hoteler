package org.daming.hoteler.security.service;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.security.Decryptable;
import org.daming.hoteler.security.Encryptable;

/**
 * 密码服务
 */
public interface IPasswordService extends Decryptable, Encryptable {

    /**
     * 密码加密
     * @param password
     * @return
     * @throws HotelerException
     */
    default String encodePassword(String password) throws HotelerException {
         return this.encrypt(password);
    }

    /**
     * 密码解密
     * @param encoderPassword
     * @return
     * @throws HotelerException
     */
    default String decodePassword(String encoderPassword) throws HotelerException {
         return this.decrypt(encoderPassword);
    }
}
