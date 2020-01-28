package org.daming.hoteler.pojo.request;

import org.daming.hoteler.pojo.User;

import java.io.Serializable;

public class CreateUserRequest implements Serializable {

    private static final long serialVersionUID = 3589158241998740233L;

    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public CreateUserRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public CreateUserRequest setPassword(String password) {
        this.password = password;
        return this;
    }

    public CreateUserRequest() {
        super();
    }
}
