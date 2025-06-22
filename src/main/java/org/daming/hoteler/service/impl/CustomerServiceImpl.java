package org.daming.hoteler.service.impl;

import org.daming.hoteler.base.context.ThreadLocalContextHolder;
import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.base.logger.LoggerManager;
import org.daming.hoteler.config.service.ISecretPropService;
import org.daming.hoteler.constants.CustomerErrorCodeConstants;
import org.daming.hoteler.constants.ErrorCodeConstants;
import org.daming.hoteler.pojo.Customer;
import org.daming.hoteler.pojo.Role;
import org.daming.hoteler.repository.jdbc.ICustomerDao;
import org.daming.hoteler.service.ICustomerService;
import org.daming.hoteler.service.IErrorService;
import org.daming.hoteler.service.ISnowflakeService;
import org.daming.hoteler.utils.AesUtil;
import org.daming.hoteler.utils.DesensitizationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

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

    private IErrorService errorService;

    private ISnowflakeService snowflakeService;

    private ISecretPropService secretPropService;

    @Autowired
    public void setErrorService(IErrorService errorService) {
        this.errorService = errorService;
    }

    @Autowired
    public void setSnowflakeService(ISnowflakeService snowflakeService) {
        this.snowflakeService = snowflakeService;
    }

    @Autowired
    public void setSecretPropService(ISecretPropService secretPropService) {
        this.secretPropService = secretPropService;
    }

    @Override
    // @CachePut(cacheNames = { "customer" }, key = "#customer.id")//写入缓存，key为user.id,一般该注解标注在新增方法上
    public long create(Customer customer) throws HotelerException {
        long customerId = this.snowflakeService.nextId();
        customer.setId(customerId);
        try {
            var cardId = customer.getCardId();
            if (cardId.length() == 18) {
                var crpytoCardId = this.cryptoCardId(cardId);
                customer.setCardId(crpytoCardId);
            } else {
                throw this.errorService.createHotelerException(CustomerErrorCodeConstants.CREATE_CUSTOMER_ERROR_CODE);
            }
            this.customerDao.create(customer);
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
    @CachePut(cacheNames = {"customer"}, key = "#customer.id", unless="#result == null")
    public void update(Customer customer) throws HotelerException {
        try {
            var cardId = StringUtils.trimAllWhitespace(customer.getCardId());
            if (cardId.length() == 18) {
                var crpytoCardId = this.cryptoCardId(cardId);
                customer.setCardId(crpytoCardId);
            } else {
                throw this.errorService.createHotelerException(CustomerErrorCodeConstants.UPDATE_CUSTOMER_ERROR_CODE);
            }
            this.customerDao.update(customer);
        } catch (HotelerException he) {
            LoggerManager.getCommonLogger().error(() -> "fail to update customer:" + customer.getId(), he);
            throw he;
        } catch (Exception ex) {
            LoggerManager.getCommonLogger().error(() -> "fail to update a customer: " + customer.getId() + ", error: " + ex.getMessage(), ex);
            throw errorService.createHotelerException(CustomerErrorCodeConstants.UPDATE_CUSTOMER_ERROR_CODE, ex);
        }
    }

    @Override
    @Cacheable(cacheNames = {"customer", "customerList"}, key = "#id")//如果缓存存在，直接读取缓存值；如果缓存不存在，则调用目标方法，并将结果放入缓存
    public Customer get(long id) throws HotelerException {
        try {
            var customer = this.customerDao.get(id);
            var user = ThreadLocalContextHolder.get().getUser();
            var isAdmin = user.getRoles().stream().map(Role::getName).anyMatch("admin"::equals);
            var cardId = StringUtils.trimAllWhitespace(customer.getCardId());
            if (cardId.length() == 64) {
                cardId = this.unCryptoCardId(cardId);
            }
            customer.setCardId(isAdmin ? cardId : DesensitizationUtil.idCardNum(cardId, 3, 4));
            return customer;
        } catch (HotelerException he) {
            LoggerManager.getCommonLogger().error(() -> "fail to get a customer whose id is " + id, he);
            throw he;
        } catch (Exception ex) {
            LoggerManager.getCommonLogger().error(() -> "fail to get a customer whose id is " + id + ", error: " + ex.getMessage(), ex);
            throw errorService.createHotelerException(CustomerErrorCodeConstants.GET_CUSTOMER_ERROR_CODE, ex);
        }
    }

    @Override
    //@Cacheable("customerList") // 标志读取缓存操作，如果缓存不存在，则调用目标方法，并将结果放入缓存
    public List<Customer> list() throws HotelerException {
        try {
            var user = ThreadLocalContextHolder.get().getUser();
            var isAdmin = Objects.nonNull(user) && user.getRoles().stream().map(Role::getName).anyMatch("admin"::equals);
            var customers = this.customerDao.list()
                    .stream()
                    .peek(customer -> {
                        var cardId = StringUtils.trimAllWhitespace(customer.getCardId());
                        if (cardId.length() == 64) {
                            cardId = this.unCryptoCardId(cardId);
                        }
                        customer.setCardId(isAdmin ? cardId : DesensitizationUtil.idCardNum(cardId, 3, 4));
                    }).toList();
            return customers;
        } catch (HotelerException he) {
            LoggerManager.getCommonLogger().error("fail to list customer", he);
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

    private String cryptoCardId(String cardId) throws HotelerException {
        var personSalt = this.secretPropService.getPersonSalt();

        return AesUtil.encode(personSalt, cardId);
    }

    private String unCryptoCardId(String cryptoCardId) throws HotelerException {
        var personSalt = this.secretPropService.getPersonSalt();

        return AesUtil.decode(personSalt, cryptoCardId);
    }

    public CustomerServiceImpl(ICustomerDao customerDao) {
        super();
        this.customerDao = customerDao;
    }
}
