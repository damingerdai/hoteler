package org.daming.hoteler.api.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.constants.ErrorCodeConstants;
import org.daming.hoteler.pojo.CustomerCheckinRecord;
import org.daming.hoteler.pojo.request.CreateCustomerCheckinRecordRequest;
import org.daming.hoteler.pojo.request.UpdateCustomerCheckinRecordRequest;
import org.daming.hoteler.pojo.response.CommonResponse;
import org.daming.hoteler.pojo.response.DataResponse;
import org.daming.hoteler.service.IErrorService;
import org.daming.hoteler.service.ICustomerCheckinRecordService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户房间关联
 *
 * @author gming001
 * @create 2021-03-04 16:44
 **/
@RestController
@RequestMapping("api/v1")
public class CustomerCheckinRecordController {

    private final ICustomerCheckinRecordService customerCheckinRecordService;
    private final IErrorService errorService;

    @Operation(summary = "创建用户入住记录", security = { @SecurityRequirement(name = "bearer-key") })
    @PostMapping("customer-checkin-record")
    public DataResponse<Long> createCustomerCheckinRecord(@RequestBody CreateCustomerCheckinRecordRequest request) {
        var ur = new CustomerCheckinRecord()
                .setCustomerId(request.getCustomerId())
                .setRoomId(request.getRoomId())
                .setBeginDate(request.getBeginDate())
                .setEndDate(request.getEndDate());
        this.customerCheckinRecordService.create(ur);
        return new DataResponse<>(1L);
    }

    @Operation(summary = "更新用户入住记录", security = { @SecurityRequirement(name = "bearer-key") })
    @PutMapping("customer-checkin-record")
    public CommonResponse updateCustomerCheckinRecord(@RequestBody UpdateCustomerCheckinRecordRequest request) {
        var ur = new CustomerCheckinRecord()
                .setId(request.getId())
                .setCustomerId(request.getCustomerId())
                .setRoomId(request.getRoomId())
                .setBeginDate(request.getBeginDate())
                .setEndDate(request.getEndDate());
        this.customerCheckinRecordService.update(ur);
        return new DataResponse<>(1L);
    }

    @Operation(summary = "获取用户入住记录", security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("customer-checkin-record/:id")
    public CommonResponse getUserRoomRelationship(@PathVariable("id") long userRoomId) {
        try {
            var id = Long.valueOf(userRoomId);
            var ur = this.customerCheckinRecordService.get(id);
            return new DataResponse<>(ur);
        } catch (NumberFormatException nfe) {
            var params = new Object[] { nfe.getMessage() };
            throw errorService.createHotelerException(ErrorCodeConstants.BAD_REQUEST_ERROR_CODEE, params, nfe);
        } catch (Exception ex) {
            throw errorService.createHotelerException(ErrorCodeConstants.SYSTEM_ERROR_CODEE, ex);
        }
    }

    @Operation(summary = "删除用户入住记录", security = { @SecurityRequirement(name = "bearer-key") })
    @DeleteMapping("customer-checkin-record/:id")
    public CommonResponse deleteUserRoomRelationship(@PathVariable("id") String userRoomId) {
        try {
            var id = Long.valueOf(userRoomId);
            this.customerCheckinRecordService.delete(id);
            return new CommonResponse();
        } catch (NumberFormatException nfe) {
            var params = new Object[] { nfe.getMessage() };
            throw errorService.createHotelerException(ErrorCodeConstants.BAD_REQUEST_ERROR_CODEE, params, nfe);
        } catch (Exception ex) {
            throw errorService.createHotelerException(ErrorCodeConstants.SYSTEM_ERROR_CODEE, ex);
        }
    }

    @Operation(summary = "获取所有用户入住记录", security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("customer-checkin-records")
    public CommonResponse listUserRoomRelationship() throws HotelerException {
        try {
            var ur = this.customerCheckinRecordService.list();
            return new DataResponse<>(ur);
        } catch (NumberFormatException nfe) {
            var params = new Object[] { nfe.getMessage() };
            throw errorService.createHotelerException(ErrorCodeConstants.BAD_REQUEST_ERROR_CODEE, params, nfe);
        } catch (Exception ex) {
            throw errorService.createHotelerException(ErrorCodeConstants.SYSTEM_ERROR_CODEE, ex);
        }
    }

    public CustomerCheckinRecordController(ICustomerCheckinRecordService customerCheckinRecordService, IErrorService errorService) {
        super();
        this.customerCheckinRecordService = customerCheckinRecordService;
        this.errorService = errorService;
    }
}
