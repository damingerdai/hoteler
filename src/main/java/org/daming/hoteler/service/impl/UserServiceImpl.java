package org.daming.hoteler.service.impl;

import org.daming.hoteler.base.exceptions.ExceptionBuilder;
import org.daming.hoteler.repository.jdbc.IUserDao;
import org.daming.hoteler.repository.mapper.UserMapper;
import org.daming.hoteler.pojo.User;
import org.daming.hoteler.service.IErrorService;
import org.daming.hoteler.service.ISnowflakeService;
import org.daming.hoteler.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
@CacheConfig(cacheNames = {"UserCache"})
public class UserServiceImpl implements IUserService {

    private ISnowflakeService snowflakeService;

    private IErrorService errorService;

    private UserMapper userMapper;

    private IUserDao userDao;

    @Override
    public List<User> list() {
        return this.userMapper.list();
    }

    @Override
    @Cacheable(cacheNames = { "user" }, key = "#username")
    public User getUserByUsername(String username) {
        Assert.hasText("username", "params 'username' is required");
        return userDao.getUserByUsername(username)
                // () -> ExceptionBuilder.buildException(600005, "用户名或者密码错误.")
                .orElseThrow(() -> this.errorService.createHotelerException(600005));
    }

    @Override
    @Cacheable(cacheNames = { "user" }, key = "#id")
    public User get(long id) {
        Assert.isTrue(id > 0, "params 'id' is required");
        return userDao.get(id).get();
    }

    @Override
    public void create(User user) {
        Assert.notNull(user,"params 'user' is required");
        var id = snowflakeService.nextId();
        user.setId(id);
        this.userMapper.create(user.getId(), user.getUsername(), user.getPassword());
    }

    @Autowired
    public void setSnowflakeService(ISnowflakeService snowflakeService) {
        this.snowflakeService = snowflakeService;
    }

    @Autowired
    public void setErrorService(IErrorService errorService) {
        this.errorService = errorService;
    }

    public UserServiceImpl(UserMapper userMapper, IUserDao userDao) {
        super();
        this.userMapper = userMapper;
        this.userDao = userDao;
    }
}
