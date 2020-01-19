package org.daming.hoteler.dao.jdbc;

import org.daming.hoteler.pojo.User;

import java.util.Optional;

public interface IUserDao {

    Optional<User> getUserByUsername(String username);
}
