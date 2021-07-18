package org.daming.hoteler.security.service.impl;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.security.service.IPasswordService;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
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
