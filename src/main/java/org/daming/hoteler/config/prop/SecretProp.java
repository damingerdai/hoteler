package org.daming.hoteler.config.prop;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.StringJoiner;

/**
 * @author gming001
 * @version 2022-10-08 17:26
 */
@Component
@ConfigurationProperties(prefix = "secret")
public class SecretProp {

    private String salt;

    private String personSalt;

    private String key;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPersonSalt() {
        return personSalt;
    }

    public void setPersonSalt(String personSalt) {
        this.personSalt = personSalt;
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
        return new StringJoiner(", ", SecretProp.class.getSimpleName() + "[", "]")
                .add("salt='" + salt + "'")
                .add("personSalt='" + personSalt + "'")
                .add("key='" + key + "'")
                .toString();
    }
}
