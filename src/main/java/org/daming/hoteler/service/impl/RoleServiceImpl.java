package org.daming.hoteler.service.impl;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.Role;
import org.daming.hoteler.repository.jdbc.IRoleDao;
import org.daming.hoteler.service.IRoleService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gming001
 * @since 2021-12-29 20:28
 **/
@Service
@CacheConfig(cacheNames = {"RoleCache"})
public class RoleServiceImpl implements IRoleService {

    private IRoleDao roleDao;

    @Override
    public List<Role> list() throws HotelerException {
        return this.roleDao.list();
    }

    @Override
    public Role get(Long id) throws HotelerException {
        return this.roleDao.get(id);
    }

    @Override
    public Role getByName(String name) throws HotelerException {
        return this.roleDao.get(name);
    }

    @Override
    @Cacheable(cacheNames = { "user-role" }, key = "'user-role-'+ #root.methodName + #id")
    public List<Role> getRolesByUserId(long userId) throws HotelerException {
        return this.roleDao.listRolesByUserId(userId);
    }

    public RoleServiceImpl(IRoleDao roleDao) {
        super();
        this.roleDao = roleDao;
    }
}
