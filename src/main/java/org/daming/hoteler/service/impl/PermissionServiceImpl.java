package org.daming.hoteler.service.impl;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.Permission;
import org.daming.hoteler.repository.jdbc.IPermissionDao;
import org.daming.hoteler.service.IPermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements IPermissionService {

    private IPermissionDao permissionDao;

    @Override
    public List<Permission> listByRoleId(long roleId) throws HotelerException {
        return this.permissionDao.listByRoleId(roleId);
    }

    @Override
    public List<Permission> listByRoleId(long... roleIds) throws HotelerException {
        return this.permissionDao.listByRoleId(roleIds);
    }

    public PermissionServiceImpl(IPermissionDao permissionDao) {
        this.permissionDao = permissionDao;
    }
}
