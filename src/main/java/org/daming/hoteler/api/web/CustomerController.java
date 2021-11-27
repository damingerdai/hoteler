package org.daming.hoteler.api.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.constants.ErrorCodeConstants;
import org.daming.hoteler.pojo.Customer;
import org.daming.hoteler.pojo.request.CreateCustomerRequest;
import org.daming.hoteler.pojo.request.UpdateCustomerRequest;
import org.daming.hoteler.pojo.response.CommonResponse;
import org.daming.hoteler.pojo.response.DataResponse;
import org.daming.hoteler.pojo.response.ListResponse;
import org.daming.hoteler.service.ICustomerService;
import org.daming.hoteler.service.IErrorService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * customer constoller
 *
 * @author gming001
 * @create 2020-12-25 22:13
 **/
@Tag(name = "user", description = "the user API")
@RestController
@RequestMapping("api/v1")
public class CustomerController {

    private ICustomerService customerService;

    private IErrorService errorService;

    @Operation(
            summary = "创建客户信息", security = { @SecurityRequirement(name = "bearer-key") },
            parameters = {
                    @Parameter(name = "body", description = "创建用户信息的请求体")
            }
    )
    @PostMapping("customer")
    public CommonResponse create(@RequestBody CreateCustomerRequest request) {
        var customer = new Customer().setName(request.getName()).setGender(request.getGender()).setCardId(request.getCardId()).setPhone(request.getPhone());
        var id =  this.customerService.create(customer);
        return new DataResponse<>(id);
    }

    @Operation(summary = "更新客户信息", security = { @SecurityRequirement(name = "bearer-key") })
    @PutMapping("customer")
    public CommonResponse update(@RequestBody UpdateCustomerRequest request) {
        var customer = new Customer().setId(request.getId()).setName(request.getName()).setGender(request.getGender()).setCardId(request.getCardId()).setPhone(request.getPhone());
        this.customerService.update(customer);
        return new CommonResponse();
    }

    @Operation(summary = "获取所有的客户信息", security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("customers")
    public CommonResponse list() {
        var list = this.customerService.list();
        return new ListResponse<>(list);
    }


    @Operation(summary = "删除客户信息", security = { @SecurityRequirement(name = "bearer-key") })
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
