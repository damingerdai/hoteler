package org.daming.hoteler.repository.jdbc;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.Customer;

import java.util.List;

/**
 * customer dao
 *
 * @author gming001
 * @create 2020-12-25 15:52
 **/
public interface ICustomerDao {

    Customer get(long id) throws HotelerException;

    List<Customer> list() throws HotelerException;
}
