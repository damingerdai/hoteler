package org.daming.hoteler.security.service.impl;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.security.service.IPasswordService;
import org.daming.hoteler.service.IErrorService;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA(Secure Hash Algorithm，安全散列算法)
 * 数字签名等密码学应用中重要的工具，被广泛地应用于电子商务等信息安全领域
 */
@Service("shaPasswordService")
public class ShaPasswordService implements IPasswordService {

    private static final String ALGORITHM = "SHA";

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
                "SHA algorithm encrypt is not supported",
                new RuntimeException("org.daming.hoteler.security.service.impl.ShaPasswordService#encrypt method is not supported"));
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
            var sha = MessageDigest.getInstance(ALGORITHM);
            byte[] bytes = sha.digest(content.getBytes());
            StringBuilder hexValue = new StringBuilder();
            for (byte b : bytes) {
                //将其中的每个字节转成十六进制字符串：byte类型的数据最高位是符号位，通过和0xff进行与操作，转换为int类型的正整数。
                String toHexString = Integer.toHexString(b & 0xff);
                hexValue.append(toHexString.length() == 1 ? "0" + toHexString : toHexString);
            }
            return hexValue.toString();
        } catch (NoSuchAlgorithmException ex) {
            throw this.errorService.createHotelerSystemException(ALGORITHM +" algorithm is not supported", ex);
        }
    }

    public ShaPasswordService(IErrorService errorService) {
        super();
        this.errorService = errorService;
    }
}
