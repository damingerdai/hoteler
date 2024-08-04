package org.daming.hoteler.repository.jdbc;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.User;

import java.util.List;
import java.util.Optional;

public interface IUserDao {

    Optional<User> getUserByUsername(String username) throws HotelerException;

    Optional<User> get(long id) throws HotelerException;

    List<User> getUnlockUsers() throws HotelerException;

    void delete(long id) throws HotelerException;
}
