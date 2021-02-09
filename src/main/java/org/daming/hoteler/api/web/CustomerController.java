package org.daming.hoteler.api.web;

import io.swagger.annotations.ApiOperation;
import org.daming.hoteler.pojo.Customer;
import org.daming.hoteler.pojo.request.CreateCustomerRequest;
import org.daming.hoteler.pojo.response.CommonResponse;
import org.daming.hoteler.pojo.response.DataResponse;
import org.daming.hoteler.pojo.response.ListResponse;
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
    public CommonResponse create(@RequestBody CreateCustomerRequest request) {
        var customer = new Customer().setName(request.getName()).setGender(request.getGender()).setCardId(request.getCardId()).setPhone(request.getPhone());
        var id =  this.customerService.create(customer);
        return new DataResponse<>(id);
    }

    @ApiOperation(value = "list customer", notes = "list all customers api")
    @GetMapping("customers")
    public CommonResponse list() {
        var list = this.customerService.list();
        return new ListResponse<>(list);
    }

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }
}
