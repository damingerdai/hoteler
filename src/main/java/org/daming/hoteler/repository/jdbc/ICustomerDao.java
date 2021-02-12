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

    /**
     * 创建用户
     * @param customer 用户的基本信息
     * @throws HotelerException hoteler错误
     */
    void create(Customer customer) throws HotelerException;

    /**
     * 获取指定用户
     * @param id 用户的唯一凭证
     * @return 用户信息
     * @throws HotelerException hoteler错误
     */
    Customer get(long id) throws HotelerException;

    /**
     * 获取所有的用户
     * @return 用户信息集合
     * @throws HotelerException hoteler错误
     */
    List<Customer> list() throws HotelerException;

    /**
     * 删除指定的customer
     * @param id customer的id
     * @throws HotelerException hoteler错误
     */
    void delete(long id) throws HotelerException;

    /**
     * 
     * @param customer
     * @throws HotelerException
     */
    void update(Customer customer) throws HotelerException;
}

