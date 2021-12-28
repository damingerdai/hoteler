package org.daming.hoteler.api.web;

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

    private ICustomerCheckinRecordService customerCheckinRecordService;
    private IErrorService errorService;

    @PostMapping("customer-checkin-record")
    public DataResponse<Long> createCustomerCheckinRecord(@RequestBody CreateCustomerCheckinRecordRequest request) {
        var ur = new CustomerCheckinRecord()
                .setUserId(request.getUserId())
                .setRoomId(request.getRoomId())
                .setBeginDate(request.getBeginDate())
                .setEndDate(request.getEndDate());
        this.customerCheckinRecordService.create(ur);
        return new DataResponse<>(1L);
    }

    @PutMapping("customer-checkin-record")
    public CommonResponse updateCustomerCheckinRecord(@RequestBody UpdateCustomerCheckinRecordRequest request) {
        var ur = new CustomerCheckinRecord()
                .setId(request.getId())
                .setUserId(request.getUserId())
                .setRoomId(request.getRoomId())
                .setBeginDate(request.getBeginDate())
                .setEndDate(request.getEndDate());
        this.customerCheckinRecordService.update(ur);
        return new DataResponse<>(1L);
    }

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

    public CustomerCheckinRecordController(ICustomerCheckinRecordService customerCheckinRecordService) {
        super();
        this.customerCheckinRecordService = customerCheckinRecordService;
    }
}
