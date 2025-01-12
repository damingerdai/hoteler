package org.daming.hoteler.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.Order;
import org.daming.hoteler.pojo.HotelerMessage;
import org.daming.hoteler.pojo.enums.HotelerEvent;
import org.daming.hoteler.pojo.enums.RoomStatus;
import org.daming.hoteler.pojo.request.OrderListRequest;
import org.daming.hoteler.pojo.vo.OrderVO;
import org.daming.hoteler.repository.jdbc.IOrderDao;
import org.daming.hoteler.repository.mapper.OrderMapper;
import org.daming.hoteler.service.*;
import org.daming.hoteler.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

/**
 * UserRoomService的默认实现类
 *
 * @author gming001
 * @create 2021-03-05 15:56
 **/
@Service
public class OrderServiceImpl implements IOrderService {

    private final IOrderDao orderDao;
    
    private final OrderMapper orderMapper;

    private IRoomService roomService;

    private ICustomerService customerService;

    private ISnowflakeService snowflakeService;

    private IEventService eventService;

    private ObjectMapper jsonMapper;

    private IErrorService errorService;

    @Override
    public void create(Order order) throws HotelerException {
        var beginDate = order.getBeginDate().toLocalDate();
        var endDate =  order.getEndDate().toLocalDate();
        var durations = DateUtils.getDates(beginDate, endDate);
        for (LocalDate begin : durations) {
            var end = begin.plusDays(1L);
            var beginDateTime = begin.atTime(12, 0, 0);
            var endDateTime = end.atTime(12, 0, 0);
            var record = new Order();
            record.setBeginDate(beginDateTime);
            record.setEndDate(endDateTime);
            record.setRoomId(order.getRoomId());
            record.setCustomerId(order.getCustomerId());

            if (DateUtils.isToday(begin)) {
                this.doCreate(record);
            } else {
                this.dispatchorder(record);
            }
        }
    }

    private void doCreate(Order order) throws HotelerException {
        var id  = this.snowflakeService.nextId();
        order.setId(id);
        this.processBeginDateAndEndDate(order);
        this.orderDao.create(order);
        this.roomService.updateStatus(order.getRoomId(), RoomStatus.InUsed);
    }

    private void dispatchorder(Order order) throws HotelerException {
        try {
            var message = new HotelerMessage();
            message.setEvent(HotelerEvent.CHECK_IN_TIME);
            var content = this.jsonMapper.writeValueAsString(order);
            message.setContent(content);
            this.eventService.publishEvent(message);
        } catch (JsonProcessingException ex) {
            throw this.errorService.createHotelerSystemException(ex.getMessage(), ex);
        }
    }

    @Override
    public void update(Order order) throws HotelerException {
        this.processBeginDateAndEndDate(order);
        this.orderDao.update(order);
        this.roomService.updateStatus(order.getRoomId(), RoomStatus.InUsed);
    }

    @Override
    public Order get(long id) throws HotelerException {
        return this.orderDao.get(id);
    }

    @Override
    public void delete(long id) throws HotelerException {
        var record = this.orderDao.get(id);
        if (Objects.nonNull(record)) {
            this.roomService.updateStatus(record.getRoomId(), RoomStatus.InUsed);
            this.orderDao.delete(id);
        }

    }

    @Override
    public int count() throws HotelerException {
        return this.orderMapper.count();
    }

    private void processBeginDateAndEndDate(Order order) {
        var beginDate = order.getBeginDate();
        order.setBeginDate(LocalDateTime.of(beginDate.toLocalDate(), LocalTime.of(12, 0)));
        var endDate = order.getEndDate();
        order.setEndDate(LocalDateTime.of(endDate.toLocalDate(), LocalTime.of(12, 0)));
    }

    @Override
    public List<Order> list() throws HotelerException {
        return this.orderDao.list();
    }

    @Override
    public List<OrderVO> list(OrderListRequest request) throws HotelerException {
        if (Objects.nonNull(request.getPageSize())) {
            request.setPageSize(10);
        }
        if (Objects.nonNull(request.getPage())) {
            request.setPage(1);
        }
        if (Objects.nonNull(request.getSort())) {
            request.setSort("create_dt");
        }
        var orders = this.orderDao.list(request);
        return orders.stream().map(order -> {
            var orderVo = new OrderVO();
            orderVo.setId(order.getId());
            orderVo.setCustomerId(order.getCustomerId());
            orderVo.setRoomId(order.getRoomId());
            orderVo.setBeginDate(order.getBeginDate());
            orderVo.setEndDate(order.getEndDate());
            var customer = this.customerService.get(order.getCustomerId());
            if (Objects.nonNull(customer)) {
                orderVo.setCustomer(customer);
            }
            var room = this.roomService.get(order.getRoomId());
            if (Objects.nonNull(room)) {
                orderVo.setRoom(room);
            }
            return orderVo;
        }).toList();
    }

    @Override
    public List<Order> listCurrentDate() throws HotelerException {
        return this.orderDao.list(LocalDate.now());
    }

    @Override
    public List<Order> listByRoomIdAndDate(long roomId, LocalDate date) {
        return this.orderDao.listByRoom(roomId, date);
    }

    public OrderServiceImpl(
            IOrderDao orderDao,
            OrderMapper orderMapper,
            IRoomService roomService,
            ICustomerService customerService,
            ISnowflakeService snowflakeService,
            IEventService eventService,
            ObjectMapper jsonMapper,
            IErrorService errorService) {
        super();
        this.orderDao = orderDao;
        this.orderMapper = orderMapper;
        this.roomService = roomService;
        this.customerService = customerService;
        this.snowflakeService = snowflakeService;
        this.eventService = eventService;
        this.jsonMapper = jsonMapper;
        this.errorService = errorService;
    }
}
