package org.daming.hoteler.pojo.response;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.daming.hoteler.pojo.UserToken;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * user token response
 *
 * @author gming001
 * @create 2021-02-03 17:55
 **/
public class UserTokenResponse extends CommonResponse implements OAuth2Compatible {

    private UserToken userToken;

    public UserToken getUserToken() {
        return userToken;
    }

    public void setUserToken(UserToken userToken) {
        this.userToken = userToken;
    }

    @Override
    public String getAccessToken() {
        if (Objects.isNull(userToken)) {
            return null;
        }
        return userToken.getAccessToken();
    }

    @Override
    public String getTokenType() {
        return OAuth2Compatible.super.getTokenType();
    }

    @Override
    public long getExpiresIn() {
        if (Objects.isNull(userToken)) {
            return OAuth2Compatible.super.getExpiresIn();
        }
        return userToken.getExp() / 1000L;
    }

    public UserTokenResponse(UserToken userToken) {
        super();
        this.userToken = userToken;
    }

    public UserTokenResponse() {
        super();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("userToken", userToken)
                .toString();
    }
}
