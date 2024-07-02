package org.daming.hoteler.repository.jdbc;

import org.daming.hoteler.pojo.User;

import java.util.List;
import java.util.Optional;

public interface IUserDao {

    Optional<User> getUserByUsername(String username);

    Optional<User> get(long id);

    List<User> getUnlockUsers();
}
