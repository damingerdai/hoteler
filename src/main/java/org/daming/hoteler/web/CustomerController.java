package org.daming.hoteler.web;

import io.swagger.annotations.ApiOperation;
import org.daming.hoteler.pojo.Customer;
import org.daming.hoteler.pojo.Room;
import org.daming.hoteler.pojo.enums.RoomStatus;
import org.daming.hoteler.pojo.request.CreateCustomerRequest;
import org.daming.hoteler.service.ICustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * customer constoller
 *
 * @author gming001
 * @create 2020-12-25 22:13
 **/
@RestController
@RequestMapping("api/v1")
public class CustomerController {

    private ICustomerService customerService;

    @ApiOperation(value = "create customer", notes = "create a new customer api")
    @PostMapping("customer")
    public long createCustomer(@RequestBody CreateCustomerRequest request) {
        var customer = new Customer().setName(request.getName()).setGender(request.getGender()).setCardId(request.getCardId()).setPhone(request.getPhone());
        return this.customerService.create(customer);
    }

    @ApiOperation(value = "lisst customer", notes = "list all customers api")
    @GetMapping("customers")
    public List<Customer> listCustomers() {
        return this.customerService.list();
    }

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }
}
