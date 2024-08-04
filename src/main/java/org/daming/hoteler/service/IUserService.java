package org.daming.hoteler.service;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.User;
import org.daming.hoteler.pojo.request.CreateUserRequest;

import java.util.List;

public interface IUserService {

    List<User> list() throws HotelerException;

    User getUserByUsername(String username) throws HotelerException;

    User get(long id) throws HotelerException;

    void create(User user) throws HotelerException;

    User create(CreateUserRequest createUserRequest) throws HotelerException;

    void loginFailed(User user) throws HotelerException;

    void resetFailedAttempts(User user) throws HotelerException;

    boolean isAccountLocked(long id) throws HotelerException;

    boolean isAccountLocked(User user) throws HotelerException;

    List<User> getUnlockUsers() throws HotelerException;

    void delete(long id) throws HotelerException;
}
