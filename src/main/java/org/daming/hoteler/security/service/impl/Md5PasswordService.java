package org.daming.hoteler.security.service.impl;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.security.service.IPasswordService;
import org.daming.hoteler.service.IErrorService;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5(Message Digest Algorithm)加密算法
 * 是一种单向加密算法，只能加密不能解密
 */
@Service
public class Md5PasswordService implements IPasswordService {

    private IErrorService errorService;

    /**
     * 解密
     *
     * @param encoderContent 待解密的字符
     * @return 解密后的字符
     * @throws HotelerException
     */
    @Override
    public String decrypt(String encoderContent) throws HotelerException {
        throw this.errorService.createHotelerSystemException(
                "md5 algorithm encrypt is not supported",
                new RuntimeException("org.daming.hoteler.security.service.impl.Md5PasswordService#encrypt method is not supported"));
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
        try {
            var md5 = MessageDigest.getInstance("md5");
            var digest = md5.digest(content.getBytes());
            StringBuilder md5code = new StringBuilder(new BigInteger(1, digest).toString(16));
            for (int i = 0; i < 32 - md5code.length(); i++) {
                md5code.insert(0, "0");
            }
            return md5code.toString();
        } catch (NoSuchAlgorithmException ne) {
            throw this.errorService.createHotelerSystemException("md5 algorithm is not supported", ne);
        }
    }

    public Md5PasswordService(IErrorService errorService) {
        super();
        this.errorService = errorService;
    }
}
