package org.daming.hoteler.service.impl;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.Permission;
import org.daming.hoteler.repository.jdbc.IPermissionDao;
import org.daming.hoteler.service.IPermissionService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = {"PermissionCache"})
public class PermissionServiceImpl implements IPermissionService {

    private final IPermissionDao permissionDao;

    @Override
    @Cacheable(cacheNames = { "role-permission" }, key = "'role-permission-'+ #root.methodName + #roleId")
    public List<Permission> listByRoleId(long roleId) throws HotelerException {
        return this.permissionDao.listByRoleId(roleId);
    }

    @Override
    @Cacheable(cacheNames = { "role-permission" }, key = "'role-permissions-'+ #root.methodName + #roleId")
    public List<Permission> listByRoleId(long... roleIds) throws HotelerException {
        return this.permissionDao.listByRoleId(roleIds);
    }

    public PermissionServiceImpl(IPermissionDao permissionDao) {
        this.permissionDao = permissionDao;
    }
}
