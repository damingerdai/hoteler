package org.daming.hoteler.service.impl;

import org.daming.hoteler.dao.jdbc.IUserDao;
import org.daming.hoteler.dao.mapper.UserMapper;
import org.daming.hoteler.pojo.User;
import org.daming.hoteler.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    private UserMapper userMapper;

    private IUserDao userDao;

    @Override
    public List<User> list() {
        return this.userMapper.list();
    }

    @Override
    public User getUserByUsername(String username) {
        Assert.hasText("username", "params 'username' is required");
        return userDao.getUserByUsername(username).get();
    }

    public UserServiceImpl(UserMapper userMapper,  IUserDao userDao) {
        super();
        this.userMapper = userMapper;
    }
}
