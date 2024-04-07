package org.daming.hoteler.service.impl;

import org.daming.hoteler.pojo.Role;
import org.daming.hoteler.pojo.request.CreateUserRequest;
import org.daming.hoteler.repository.jdbc.IUserDao;
import org.daming.hoteler.repository.mapper.UserMapper;
import org.daming.hoteler.pojo.User;
import org.daming.hoteler.repository.mapper.UserRoleMapper;
import org.daming.hoteler.security.service.IPasswordService;
import org.daming.hoteler.service.IErrorService;
import org.daming.hoteler.service.IPermissionService;
import org.daming.hoteler.service.IRoleService;
import org.daming.hoteler.service.ISnowflakeService;
import org.daming.hoteler.service.IUserService;
import org.daming.hoteler.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ApplicationObjectSupport 参考 <a href="https://sxpujs.github.io/2020/03/27/spring-get-bean-by-name/">...</a>
 */
@Service
@CacheConfig(cacheNames = {"UserCache"})
public class UserServiceImpl extends ApplicationObjectSupport implements IUserService {

    private ISnowflakeService snowflakeService;

    private IErrorService errorService;

    private IRoleService roleService;

    private IPermissionService permissionService;

    private UserMapper userMapper;

    private IUserDao userDao;

    private UserRoleMapper userRoleMapper;

    @Override
    public List<User> list() {
        return this.userMapper.list().stream().peek(user -> {
            user.setPassword(null);
            user.setPasswordType(null);
            var roleIds = Objects.requireNonNullElseGet(
                    user.getRoles(), () -> new ArrayList<Role>(0))
                    .stream().mapToLong(Role::getId).toArray();
            var permission = this.permissionService.listByRoleId(roleIds);
            user.setPermissions(permission);
        }).toList();
    }

    @Override
    //@Cacheable(cacheNames = { "user" }, key = "#username")
    public User getUserByUsername(String username) {
        Assert.hasText("username", "params 'username' is required");
        var user =  this.userDao.getUserByUsername(username)
                .orElseThrow(() -> this.errorService.createHotelerException(600005));
        var roles = this.roleService.getRolesByUserId(user.getId());
        if (Objects.nonNull(roles) && !roles.isEmpty()) {
            user.setRoles(roles);
            var roleIds = roles.stream().mapToLong(Role::getId).toArray();
            var permission = this.permissionService.listByRoleId(roleIds);
            user.setPermissions(permission);
        }
        return user;
    }

    @Override
   // @Cacheable(cacheNames = { "user" }, key = "#id")
    public User get(long id) {
        Assert.isTrue(id > 0, "params 'id' is required");
        var user =  userDao.get(id)
                .orElseThrow(() -> this.errorService.createHotelerException(600005));
        var roles = this.roleService.getRolesByUserId(user.getId());
        if (Objects.nonNull(roles) && !roles.isEmpty()) {
            user.setRoles(roles);
            var roleIds = roles.stream().mapToLong(Role::getId).toArray();
            var permission = this.permissionService.listByRoleId(roleIds);
            user.setPermissions(permission);

        }
        return user;
    }

    @Override
    @Transactional
    public void create(User user) {
        Assert.notNull(user,"params 'user' is required");
        var existUser = this.userDao.getUserByUsername(user.getUsername());
        if (existUser.isPresent()) {
            throw this.errorService.createHotelerException(600012);
        }
        var isFirstUser = this.userMapper.count() == 0;
        var id = snowflakeService.nextId();
        user.setId(id);
        var passwordType = CommonUtils.isNotEmpty(user.getPasswordType()) ? user.getPasswordType() : "noop";
        var passwordService = this.getPasswordService(passwordType);
        this.userMapper.create(user.getId(), user.getUsername(), passwordService.encodePassword(user.getPassword()), passwordType);
        var roles = isFirstUser
                ? List.of(this.roleService.getByName("admin"), this.roleService.getByName("users"))
                : List.of(this.roleService.getByName("users"));
        roles.forEach((role ->  this.userRoleMapper.create(id, role.getId())));
    }

    @Override
    @Transactional
    public User create(CreateUserRequest createUserRequest) {
        Assert.notNull(createUserRequest,"params 'createUserRequest' is required");
        var user = new User()
                .setId(this.snowflakeService.nextId())
                .setUsername(createUserRequest.getUsername())
                .setPassword(createUserRequest.getPassword())
                .setPasswordType(createUserRequest.getPasswordType());
        var existUser = this.userDao.getUserByUsername(user.getUsername());
        if (existUser.isPresent()) {
            throw this.errorService.createHotelerException(600012);
        }
        var passwordType = CommonUtils.isNotEmpty(user.getPasswordType()) ? user.getPasswordType() : "noop";
        var passwordService = this.getPasswordService(passwordType);
        this.userMapper.create(user.getId(), user.getUsername(), passwordService.encodePassword(user.getPassword()), passwordType);
        var isFirstUser = this.userMapper.count() == 0;
        var roleNames = createUserRequest.getRoles();
        if (isFirstUser && !roleNames.contains("admin")) {
            roleNames.add("admin");
        }
        roleNames.forEach(System.out::println);
        var roles = roleNames.stream().map(roleName -> this.roleService.getByName(roleName)).toList();
        roles.forEach(role ->  {
           this.userRoleMapper.create(user.getId(), role.getId());
        });
        user.setRoles(roles);
        return user;
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

    @Autowired
    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setPermissionService(IPermissionService permissionService) {
        this.permissionService = permissionService;
    }

    public UserServiceImpl(UserMapper userMapper, IUserDao userDao, UserRoleMapper userRoleMapper) {
        super();
        this.userMapper = userMapper;
        this.userDao = userDao;
        this.userRoleMapper = userRoleMapper;
    }
}
