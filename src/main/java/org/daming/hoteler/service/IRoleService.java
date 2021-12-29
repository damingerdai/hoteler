package org.daming.hoteler.service;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.Role;

import java.util.List;

/**
 * @author gming001
 * @since 2021-12-29 20:12
 **/
public interface IRoleService {

    List<Role> list() throws HotelerException;

    Role get(Long id) throws HotelerException;
}
