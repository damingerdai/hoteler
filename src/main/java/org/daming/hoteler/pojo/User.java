package org.daming.hoteler.pojo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.StringJoiner;

public class User implements Serializable {
    private static final long serialVersionUID = 120505218967153077L;

    @ApiModelProperty(value = "user id")
    private long id;

    private String username;

    private String password;

    public long getId() {
        return id;
    }

    public User setId(long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("username='" + username + "'")
                .add("password='" + password + "'")
                .toString();
    }
}
