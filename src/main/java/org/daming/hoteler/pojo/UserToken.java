package org.daming.hoteler.pojo;

import java.io.Serializable;
import java.util.StringJoiner;

public class UserToken implements Serializable {

    private static final long serialVersionUID = 6156817161731552023L;

    private String accessToken;

    private String refreshToken;

    private long exp;

    public String getAccessToken() {
        return accessToken;
    }

    public UserToken setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public UserToken setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public long getExp() {
        return exp;
    }

    public UserToken setExp(long exp) {
        this.exp = exp;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserToken.class.getSimpleName() + "[", "]")
                .add("accessToken='" + accessToken + "'")
                .add("refreshToken='" + refreshToken + "'")
                .add("exp=" + exp)
                .toString();
    }
}
