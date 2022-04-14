package org.daming.hoteler.pojo;

import java.io.Serializable;
import java.time.Instant;
import java.util.StringJoiner;

/**
 * hoteler context
 *
 * @author gming001
 * @create 2021-01-09 22:52
 **/
public class HotelerContext implements Serializable {

    private static final long serialVersionUID = 2030630382170016266L;

    private String requestId;
    private Instant in;
    private String accessToken;
    private User user;

    public String getRequestId() {
        return requestId;
    }

    public HotelerContext setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public Instant getIn() {
        return in;
    }

    public HotelerContext setIn(Instant in) {
        this.in = in;
        return this;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public HotelerContext setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public User getUser() {
        return user;
    }

    public HotelerContext setUser(User user) {
        this.user = user;
        return this;
    }

    public HotelerContext() {
        super();
    }

    public HotelerContext(String requestId, Instant in, String accessToken) {
        super();
        this.requestId = requestId;
        this.in = in;
        this.accessToken = accessToken;
    }

    public HotelerContext(String requestId, Instant in, String accessToken, User user) {
        super();
        this.requestId = requestId;
        this.in = in;
        this.accessToken = accessToken;
        this.user = user;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", HotelerContext.class.getSimpleName() + "[", "]")
                .add("requestId='" + requestId + "'")
                .add("in=" + in)
                .add("accessToken='" + accessToken + "'")
                .add("user='" + user + "'")
                .toString();
    }
}
