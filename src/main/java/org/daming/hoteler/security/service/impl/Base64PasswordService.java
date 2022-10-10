package org.daming.hoteler.security.service.impl;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.security.service.IPasswordService;
import org.springframework.stereotype.Service;

import java.util.Base64;

/**
 * BASE64进行加密/解密
 * 通常用作对二进制数据进行加密
 */
@Service("base64PasswordService")
public class Base64PasswordService implements IPasswordService {

    /**
     * 解密
     *
     * @param encoderContent 待解密的字符
     * @return 解密后的字符
     * @throws HotelerException
     */
    @Override
    public String decrypt(String encoderContent) throws HotelerException {
        return new String(Base64.getDecoder().decode(encoderContent));
    }

    /**
     * 加密
     *
     * @param content
     * @return
     * @throws HotelerException
     */
    @Override
    public String encrypt(String content) throws HotelerException {
        return new String(Base64.getEncoder().encode(content.getBytes()));
    }
}
