package org.daming.hoteler.security.service.impl;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.security.service.IPasswordService;
import org.daming.hoteler.service.IErrorService;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * RSA非对称加密/解密
 * 非对称加密算法的典型代表，既能加密、又能解密。和对称加密算法比如DES的明显区别在于用于加密、解密的密钥是不同的。使用RSA算法，只要密钥足够长(一般要求1024bit)，加密的信息是不能被破解的
 */
@Service("rsaPasswordService")
public class RSAPasswordService implements IPasswordService {

    private static String ALGORITHM = "RSA";

    private IErrorService errorService;

    private KeyPair keyPair;

    protected KeyPair generateKeyPair() throws HotelerException {
        try {
            var keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
            keyPairGenerator.initialize(1024);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException ex) {
            throw this.errorService.createHotelerSystemException(ALGORITHM +" algorithm is not supported", ex);
        }
    }

    /**
     * 解密
     *
     * @param encoderContent 待解密的字符
     * @return 解密后的字符
     * @throws HotelerException
     */
    @Override
    public String decrypt(String encoderContent) throws HotelerException {
        try {
            var cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, this.keyPair.getPublic());
            return new String(cipher.doFinal(encoderContent.getBytes()));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            throw this.errorService.createHotelerSystemException(ALGORITHM +" algorithm is not supported", ex);
        }
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
            var cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, this.keyPair.getPrivate());
            return new String(cipher.doFinal(content.getBytes()));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            throw this.errorService.createHotelerSystemException(ALGORITHM +" algorithm is not supported", ex);
        }
    }

    @PostConstruct
    private void init() {
        this.keyPair = this.generateKeyPair();
        if (Objects.isNull(this.keyPair)) {
            throw new RuntimeException("generateKeyPair error");
        }
    }

    public RSAPasswordService(IErrorService errorService) {
        super();
        this.errorService = errorService;
    }
}
