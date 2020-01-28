package org.daming.hoteler.pojo.builder;

import org.daming.hoteler.pojo.User;
import org.daming.hoteler.pojo.request.CreateUserRequest;

public class UserBuilder {

    public static User fromCreateUserRequest(CreateUserRequest request) {
        return new User().setUsername(request.getUsername()).setPassword(request.getPassword());
    }
}
