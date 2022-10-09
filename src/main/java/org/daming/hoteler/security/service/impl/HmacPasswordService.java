package org.daming.hoteler.security.service.impl;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.config.service.ISecretPropService;
import org.daming.hoteler.security.service.IPasswordService;
import org.daming.hoteler.service.IErrorService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * HMAC(Hash Message Authentication Code，散列消息鉴别码)
 */
@Service("hmacPasswordService")
public class HmacPasswordService implements IPasswordService {

    private static final String ALGORITHM = "HMAC";

    private IErrorService errorService;

    private ISecretPropService secretPropService;

    private SecretKey key;

    protected SecretKey  generateKey(String keyStr) throws HotelerException {
        return new SecretKeySpec(keyStr.getBytes(), ALGORITHM);
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
        throw this.errorService.createHotelerSystemException(
                "md5 algorithm encrypt is not supported",
                new RuntimeException("org.daming.hoteler.security.service.impl.HmacPasswordService#encrypt method is not supported"));
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
            var mac = Mac.getInstance(this.key.getAlgorithm());
            mac.init(this.key);
            return new String(mac.doFinal(content.getBytes()));
        } catch (NoSuchAlgorithmException | InvalidKeyException ex) {
            throw this.errorService.createHotelerSystemException(ALGORITHM +" algorithm is not supported", ex);
        }
    }

    @PostConstruct
    private void init() {
        this.key = this.generateKey(this.secretPropService.getKey());
        if (Objects.isNull(this.key)) {
            throw new RuntimeException("generateKey error for " + this.secretPropService.getKey());
        }
    }

    public HmacPasswordService(IErrorService errorService, ISecretPropService secretPropService) {
        super();
        this.errorService = errorService;
        this.secretPropService = secretPropService;
    }
}
