package org.daming.hoteler.service;

import org.daming.hoteler.pojo.User;

import java.util.List;

public interface IUserService {

    List<User> list();

    User getUserByUsername(String username);
}
