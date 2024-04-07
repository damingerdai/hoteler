package org.daming.hoteler.service;

import org.daming.hoteler.pojo.User;
import org.daming.hoteler.pojo.request.CreateUserRequest;

import java.util.List;

public interface IUserService {

    List<User> list();

    User getUserByUsername(String username);

    User get(long id);

    void create(User user);

    User create(CreateUserRequest createUserRequest);
}
