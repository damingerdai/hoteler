package org.daming.hoteler.service;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.Permission;

import java.util.List;

public interface IPermissionService {

    List<Permission> listByRoleId(long roleId) throws HotelerException;

    List<Permission> listByRoleId(long... roleIds) throws HotelerException;
}
