package org.daming.hoteler.service.impl;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.base.logger.LoggerManager;
import org.daming.hoteler.constants.CustomerErrorCodeConstants;
import org.daming.hoteler.constants.ErrorCodeConstants;
import org.daming.hoteler.pojo.Customer;
import org.daming.hoteler.repository.jdbc.ICustomerDao;
import org.daming.hoteler.repository.mapper.CustomerMapper;
import org.daming.hoteler.service.ICustomerService;
import org.daming.hoteler.service.IErrorService;
import org.daming.hoteler.service.ISnowflakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CustomerService
 *
 * @author gming001
 * @create 2020-12-25 21:59
 **/
@Service
@CacheConfig(cacheNames = {"CustomerCache"})
public class CustomerServiceImpl implements ICustomerService {

    private ICustomerDao customerDao;

    private CustomerMapper customerMapper;

    private IErrorService errorService;

    private ISnowflakeService snowflakeService;

    @Autowired
    public void setErrorService(IErrorService errorService) {
        this.errorService = errorService;
    }

    @Autowired
    public void setSnowflakeService(ISnowflakeService snowflakeService) {
        this.snowflakeService = snowflakeService;
    }

    @Override
    // @CachePut(cacheNames = { "customer" }, key = "#customer.id")//写入缓存，key为user.id,一般该注解标注在新增方法上
    public long create(Customer customer) throws HotelerException {
        long customerId = this.snowflakeService.nextId();
        customer.setId(customerId);
        try {
            // this.customerMapper.create(customer);
            customerDao.create(customer);
        } catch (HotelerException he) {
            LoggerManager.getCommonLogger().error("fail to create a customer", he);
            throw he;
        } catch (Exception ex) {
            LoggerManager.getCommonLogger().error("fail to create a customer", ex);
            throw errorService.createHotelerException(CustomerErrorCodeConstants.CREATE_CUSTOMER_ERROR_CODE, ex);
        }
        return customerId;
    }

    @Override
    @CacheEvict(cacheNames = { "customer" }, key = "#customer.id", allEntries = true)//根据key清除缓存，一般该注解标注在修改和删除方法上
    public void update(Customer customer) throws HotelerException {
        try {
            customerDao.update(customer);
        } catch (HotelerException he) {
            LoggerManager.getCommonLogger().error(() -> "fail to update customer:" + customer.getId(), he);
            throw he;
        } catch (Exception ex) {
            LoggerManager.getCommonLogger().error(() -> "fail to update a customer: " + customer.getId() + ", error: " + ex.getMessage(), ex);
            throw errorService.createHotelerException(CustomerErrorCodeConstants.CREATE_CUSTOMER_ERROR_CODE, ex);
        }
    }

    @Override
    @Cacheable(cacheNames = { "customer" }, key = "#id")//如果缓存存在，直接读取缓存值；如果缓存不存在，则调用目标方法，并将结果放入缓存
    public Customer get(long id) throws HotelerException {
        try {
            return this.customerDao.get(id);
        } catch (HotelerException he) {
            LoggerManager.getCommonLogger().error(() -> "fail to get a customer whose id is " + id, he);
        } catch (Exception ex) {
            // TODO add more error
        }
        return null;
    }

    @Override
    @Cacheable("customerList") // 标志读取缓存操作，如果缓存不存在，则调用目标方法，并将结果放入缓存
    public List<Customer> list() throws HotelerException {
        try {
            return this.customerDao.list();
        } catch (HotelerException he) {
            LoggerManager.getCommonLogger().error( "fail to list customer", he);
            throw he;
        } catch (Exception ex) {
            LoggerManager.getCommonLogger().error(() -> "fail to list customer: " + ex.getMessage(), ex);
            throw errorService.createHotelerException(ErrorCodeConstants.SYSTEM_ERROR_CODEE, ex);
        }
    }

    @Override
    public void delete(long id) throws HotelerException {
        try {
            this.customerDao.delete(id);
        } catch (HotelerException he) {
            LoggerManager.getCommonLogger().error(() -> "fail to delete customer [" + id + "]", he);
            throw he;
        } catch (Exception ex) {
            LoggerManager.getCommonLogger().error(() -> "fail to delete customer [" + id + "]: " + ex.getMessage(), ex);
            throw errorService.createHotelerException(ErrorCodeConstants.SYSTEM_ERROR_CODEE, ex);
        }
    }

    public CustomerServiceImpl(ICustomerDao customerDao, CustomerMapper customerMapper) {
        this.customerDao = customerDao;
        this.customerMapper = customerMapper;
    }
}
