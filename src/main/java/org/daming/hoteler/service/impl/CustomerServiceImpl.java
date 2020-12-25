package org.daming.hoteler.service.impl;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.base.logger.LoggerManager;
import org.daming.hoteler.pojo.Customer;
import org.daming.hoteler.repository.jdbc.ICustomerDao;
import org.daming.hoteler.repository.mapper.CustomerMapper;
import org.daming.hoteler.service.ICustomerService;
import org.daming.hoteler.service.IErrorService;
import org.daming.hoteler.service.ISnowflakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CustomerService
 *
 * @author gming001
 * @create 2020-12-25 21:59
 **/
@Service
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
    public long create(Customer customer) throws HotelerException {
        long customerId = this.snowflakeService.nextId();
        customer.setId(customerId);
        try {
            this.customerMapper.create(customer);
        } catch (HotelerException he) {
            LoggerManager.getCommonLogger().error("fail to create a customer", he);
            throw he;
        } catch (Exception ex) {
             // TODO add more error
            ex.printStackTrace();
        }
        return customerId;
    }

    @Override
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
    public List<Customer> list() throws HotelerException {
        try {
            return this.customerDao.list();
        } catch (HotelerException he) {
            LoggerManager.getCommonLogger().error( "fail to list customer", he);
        } catch (Exception ex) {
            // TODO add more error
        }
        return null;
    }

    public CustomerServiceImpl(ICustomerDao customerDao, CustomerMapper customerMapper) {
        this.customerDao = customerDao;
        this.customerMapper = customerMapper;
    }
}
