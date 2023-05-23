package org.daming.hoteler.task;

import org.daming.hoteler.base.logger.LoggerManager;
import org.daming.hoteler.pojo.Customer;
import org.daming.hoteler.service.ICustomerService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author gming001
 * @version 2023-05-23 13:05
 */
@Component
public class CustomerTask {

    private final ICustomerService customerService;

    public void updateCrpytoCustomerId() {
        var customers = this.customerService.list();
        for(var customer: customers) {
            this.doUpdateCrpytoCustomerId(customer);
        }
    }

    private void doUpdateCrpytoCustomerId(Customer customer) {
        try {
            this.customerService.update(customer);
        } catch (Exception ex) {
            LoggerManager.getCommonLogger().info(ex.getMessage(), ex);
        }

    }

    public CustomerTask(ICustomerService customerService) {
        super();
        this.customerService = customerService;
    }
}
