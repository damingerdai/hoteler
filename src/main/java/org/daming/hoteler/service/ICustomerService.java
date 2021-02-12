package org.daming.hoteler.service;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.Customer;

import java.util.List;

/**
 * customer service
 *
 * @author gming001
 * @create 2020-12-25 21:58
 **/
public interface ICustomerService {

    long create(Customer customer) throws HotelerException;

    void update(Customer customer) throws HotelerException;

    Customer get(long id) throws HotelerException;

    List<Customer> list() throws HotelerException;

    void delete(long id) throws HotelerException;
}
