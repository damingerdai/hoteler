package org.daming.hoteler.repository.jdbc;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.Role;

import java.util.List;

/**
 * role dao
 *
 * @author gming001
 * @create 2021-12-28 19.15
 **/
public interface IRoleDao {

    List<Role> list() throws HotelerException;

    List<Role> list(List<Long> ids) throws HotelerException;

    Role get(long id) throws HotelerException;

    Role get(String name) throws HotelerException;
}
