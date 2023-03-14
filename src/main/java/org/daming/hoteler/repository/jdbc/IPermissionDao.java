package org.daming.hoteler.repository.jdbc;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.Permission;

import java.util.List;
import java.util.Optional;

public interface IPermissionDao {

    Optional<Permission> get(String id) throws HotelerException;

    List<Permission> list() throws HotelerException;

    List<Permission> listByRoleId(long roleId) throws HotelerException;

    List<Permission> listByRoleId(long... roleIds) throws HotelerException;
}
