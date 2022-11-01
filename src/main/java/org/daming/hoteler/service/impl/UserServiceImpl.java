package org.daming.hoteler.service.impl;

import org.daming.hoteler.repository.jdbc.IRoleDao;
import org.daming.hoteler.repository.jdbc.IUserDao;
import org.daming.hoteler.repository.mapper.UserMapper;
import org.daming.hoteler.pojo.User;
import org.daming.hoteler.security.service.IPasswordService;
import org.daming.hoteler.service.IErrorService;
import org.daming.hoteler.service.ISnowflakeService;
import org.daming.hoteler.service.IUserService;
import org.daming.hoteler.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * ApplicationObjectSupport 参考 <a href="https://sxpujs.github.io/2020/03/27/spring-get-bean-by-name/">...</a>
 */
@Service
@CacheConfig(cacheNames = {"UserCache"})
public class UserServiceImpl extends ApplicationObjectSupport implements IUserService {

    private ISnowflakeService snowflakeService;

    private IErrorService errorService;

    private UserMapper userMapper;

    private IUserDao userDao;

    private IRoleDao roleDao;

    @Override
    public List<User> list() {
        return this.userMapper.list();
    }

    @Override
    @Cacheable(cacheNames = { "user" }, key = "#username")
    public User getUserByUsername(String username) {
        Assert.hasText("username", "params 'username' is required");
        var user =  this.userDao.getUserByUsername(username)
                .orElseThrow(() -> this.errorService.createHotelerException(600005));
        var roles = this.roleDao.list();
        if (Objects.nonNull(roles) && !roles.isEmpty()) {
            user.setRoles(roles);
        }
        return user;
    }

    @Override
    @Cacheable(cacheNames = { "user" }, key = "#id")
    public User get(long id) {
        Assert.isTrue(id > 0, "params 'id' is required");
        var user =  userDao.get(id)
                .orElseThrow(() -> this.errorService.createHotelerException(600005));
        var roles = this.roleDao.list();
        if (Objects.nonNull(roles) && !roles.isEmpty()) {
            user.setRoles(roles);
        }
        return user;
    }

    @Override
    public void create(User user) {
        Assert.notNull(user,"params 'user' is required");
        var id = snowflakeService.nextId();
        user.setId(id);
        var role = this.roleDao.get("admin");
        var passwordType = CommonUtils.isNotEmpty(user.getPasswordType()) ? user.getPasswordType() : "noop";
        var passwordService = this.getPasswordService(passwordType);
        this.userMapper.create(user.getId(), user.getUsername(), passwordService.encodePassword(user.getPassword()), passwordType);
    }

    private IPasswordService getPasswordService(String passwordType) {
        return Objects.requireNonNull(super.getApplicationContext()).getBean(passwordType + "PasswordService", IPasswordService.class);
    }

    @Autowired
    public void setSnowflakeService(ISnowflakeService snowflakeService) {
        this.snowflakeService = snowflakeService;
    }

    @Autowired
    public void setErrorService(IErrorService errorService) {
        this.errorService = errorService;
    }

    public UserServiceImpl(UserMapper userMapper, IUserDao userDao, IRoleDao roleDao) {
        super();
        this.userMapper = userMapper;
        this.userDao = userDao;
        this.roleDao = roleDao;
    }
}
