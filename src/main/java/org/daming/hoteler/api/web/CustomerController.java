package org.daming.hoteler.api.web;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.constants.ErrorCodeConstants;
import org.daming.hoteler.pojo.Customer;
import org.daming.hoteler.pojo.request.CreateCustomerRequest;
import org.daming.hoteler.pojo.response.CommonResponse;
import org.daming.hoteler.pojo.response.DataResponse;
import org.daming.hoteler.pojo.response.ListResponse;
import org.daming.hoteler.service.ICustomerService;
import org.daming.hoteler.service.IErrorService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    private IErrorService errorService;

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

    @ApiOperation(value = "delete customer", notes = "delete customer api")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "customer id", required = true, paramType = "path", dataTypeClass = String.class)
    })
    @DeleteMapping("customer/{id}")
    public CommonResponse delete(@PathVariable("id") String customerId) throws HotelerException {
        try {
            var id = Long.valueOf(customerId);
            this.customerService.delete(id);
        } catch (NumberFormatException nfe) {
            var params = new Object[] { nfe.getMessage() };
            throw errorService.createHotelerException(ErrorCodeConstants.BAD_REQUEST_ERROR_CODEE, params, nfe);
        } catch (Exception ex) {
            throw errorService.createHotelerException(ErrorCodeConstants.SYSTEM_ERROR_CODEE, ex);
        }

        return new CommonResponse();
    }

    public CustomerController(
            ICustomerService customerService,
            IErrorService errorService) {
        super();
        this.customerService = customerService;
        this.errorService = errorService;
    }
}
