package org.daming.hoteler.pojo.response;

import org.daming.hoteler.pojo.UserToken;

import java.util.StringJoiner;

/**
 * user token response
 *
 * @author gming001
 * @create 2021-02-03 17:55
 **/
public class UserTokenResponse extends CommonResponse {

    private static final long serialVersionUID = -5280034481157822759L;

    private UserToken userToken;

    public UserToken getUserToken() {
        return userToken;
    }

    public void setUserToken(UserToken userToken) {
        this.userToken = userToken;
    }

    public UserTokenResponse() {
        super();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserTokenResponse.class.getSimpleName() + "[", "]")
                .add("userToken=" + userToken)
                .toString();
    }
}
