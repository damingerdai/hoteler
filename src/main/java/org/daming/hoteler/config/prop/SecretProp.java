package org.daming.hoteler.config.prop;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author gming001
 * @version 2022-10-08 17:26
 */
@Component
@ConfigurationProperties(prefix = "secret")
public class SecretProp {

    private String salt;

    private String key;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public SecretProp() {
        super();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("salt", salt)
                .append("key", key)
                .toString();
    }
}
