package org.daming.hoteler.config.service.impl;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.config.prop.SecretProp;
import org.daming.hoteler.config.service.ISecretPropService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author gming001
 * @version 2022-10-08 17:29
 */
@Service
public class SecretPropServiceImpl implements ISecretPropService, InitializingBean {

    private final SecretProp secretProp;

    @Override
    public String getSalt() throws HotelerException {
        return this.secretProp.getSalt();
    }

    @Override
    public String getKey() throws HotelerException {
        return this.secretProp.getKey();
    }

    @Override
    public String getPersonSalt() throws HotelerException {
        return this.secretProp.getPersonSalt();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (Objects.isNull(this.secretProp)) {
            throw new RuntimeException("secret prop error");
        }
    }

    public SecretPropServiceImpl(SecretProp secretProp) {
        super();
        this.secretProp = secretProp;
    }
}
