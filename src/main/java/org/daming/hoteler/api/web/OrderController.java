package org.daming.hoteler.api.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.constants.ErrorCodeConstants;
import org.daming.hoteler.pojo.Order;
import org.daming.hoteler.pojo.request.CreateCustomerCheckinRecordRequest;
import org.daming.hoteler.pojo.request.OrderListRequest;
import org.daming.hoteler.pojo.request.UpdateCustomerCheckinRecordRequest;
import org.daming.hoteler.pojo.response.CommonResponse;
import org.daming.hoteler.pojo.response.DataResponse;
import org.daming.hoteler.pojo.response.ListPageResponse;
import org.daming.hoteler.service.IErrorService;
import org.daming.hoteler.service.IOrderService;
import org.springframework.web.bind.annotation.*;

/**
 * 用户房间关联
 *
 * @author gming001
 * @create 2021-03-04 16:44
 **/
@RestController
@RequestMapping("api/v1")
public class OrderController {

    private final IOrderService orderService;
    private final IErrorService errorService;

    @Operation(summary = "创建用户入住记录", security = { @SecurityRequirement(name = "bearer-key") })
    @PostMapping(path = "order")
    public DataResponse<Long> create(@RequestBody CreateCustomerCheckinRecordRequest request) {
        var ur = new Order()
                .setCustomerId(request.getCustomerId())
                .setRoomId(request.getRoomId())
                .setBeginDate(request.getBeginDate())
                .setEndDate(request.getEndDate());
        this.orderService.create(ur);
        return new DataResponse<>(1L);
    }

    @Operation(summary = "更新用户入住记录", security = { @SecurityRequirement(name = "bearer-key") })
    @PutMapping("order")
    public CommonResponse update(@RequestBody UpdateCustomerCheckinRecordRequest request) {
        var ur = new Order()
                .setId(request.getId())
                .setCustomerId(request.getCustomerId())
                .setRoomId(request.getRoomId())
                .setBeginDate(request.getBeginDate())
                .setEndDate(request.getEndDate());
        this.orderService.update(ur);
        return new DataResponse<>(1L);
    }

    @Operation(summary = "获取用户入住记录", security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("order/:id")
    public CommonResponse get(@PathVariable("id") long userRoomId) {
        try {
            var id = Long.valueOf(userRoomId);
            var ur = this.orderService.get(id);
            return new DataResponse<>(ur);
        } catch (NumberFormatException nfe) {
            var params = new Object[] { nfe.getMessage() };
            throw errorService.createHotelerException(ErrorCodeConstants.BAD_REQUEST_ERROR_CODEE, params, nfe);
        } catch (Exception ex) {
            throw errorService.createHotelerException(ErrorCodeConstants.SYSTEM_ERROR_CODEE, ex);
        }
    }

    @Operation(summary = "删除用户入住记录", security = { @SecurityRequirement(name = "bearer-key") })
    @DeleteMapping("order/:id")
    public CommonResponse delete(@PathVariable("id") String userRoomId) {
        try {
            var id = Long.parseLong(userRoomId);
            this.orderService.delete(id);
            return new CommonResponse();
        } catch (NumberFormatException nfe) {
            var params = new Object[] { nfe.getMessage() };
            throw errorService.createHotelerException(ErrorCodeConstants.BAD_REQUEST_ERROR_CODEE, params, nfe);
        } catch (Exception ex) {
            throw errorService.createHotelerException(ErrorCodeConstants.SYSTEM_ERROR_CODEE, ex);
        }
    }

    @Operation(summary = "获取所有用户入住记录", security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("orders")
    public CommonResponse list(@RequestParam(name = "page", required = false)Integer page,
                               @RequestParam(name = "pageSize", required = false)Integer pageSize,
                               @RequestParam(name = "sort", required = false)String sort) throws HotelerException {
        try {
            var request = new OrderListRequest();
            request.setPage(page);
            request.setPageSize(pageSize);
            request.setSort(sort);
            var count = this.orderService.count();
            var orders = this.orderService.list(request);
            return new ListPageResponse<>(orders, count);
        } catch (Exception ex) {
            throw errorService.createHotelerException(ErrorCodeConstants.SYSTEM_ERROR_CODEE, ex);
        }
    }

    public OrderController(IOrderService orderService, IErrorService errorService) {
        super();
        this.orderService = orderService;
        this.errorService = errorService;
    }
}
