package org.daming.hoteler.security.service.impl;

import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;
import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.config.service.ISecretPropService;
import org.daming.hoteler.security.service.IPasswordService;
import org.daming.hoteler.service.IErrorService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * 使用Argon2算法进行加密解密
 * @author gming001
 * @version 2023-12-15 13:33
 */
@Service("argon2PasswordService")
public class Argon2PasswordService implements IPasswordService, InitializingBean {

    private final int iterations = 2;
    private final int memLimit = 16384;
    private final int hashLength = 32;
    private final int parallelism = 1;

    private Argon2BytesGenerator generater;

    private final IErrorService errorService;

    private final ISecretPropService secretPropService;

    @Override
    public String decrypt(String encoderContent) throws HotelerException {
        throw this.errorService.createHotelerSystemException(
                "argon2 algorithm encrypt is not supported",
                new RuntimeException("org.daming.hoteler.security.service.impl.Argon2PasswordService#decrypt method is not supported"));
    }

    @Override
    public String encrypt(String content) throws HotelerException {
        var result = new byte[this.hashLength];
        this.generater.generateBytes(content.getBytes(StandardCharsets.UTF_8), result, 0, result.length);
        return new String(result, StandardCharsets.UTF_8);
    }

    @Deprecated
    private byte[] generateSalt16Byte() {
        var secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);

        return salt;
    }

    // @PostConstruct
    private void init() {
        byte[] salt = this.secretPropService.getSalt().getBytes(StandardCharsets.UTF_8);
        var builder = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
                .withVersion(Argon2Parameters.ARGON2_VERSION_13)
                .withIterations(iterations)
                .withMemoryAsKB(memLimit)
                .withParallelism(parallelism)
                .withSalt(salt);

        var generate = new Argon2BytesGenerator();
        generate.init(builder.build());
        this.generater = generate;
    }


    public Argon2PasswordService(IErrorService errorService, ISecretPropService secretPropService) {
        super();
        this.errorService = errorService;
        this.secretPropService = secretPropService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }
}
