package org.daming.hoteler.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.Order;
import org.daming.hoteler.pojo.HotelerMessage;
import org.daming.hoteler.pojo.enums.HotelerEvent;
import org.daming.hoteler.pojo.enums.RoomStatus;
import org.daming.hoteler.repository.jdbc.IOrderDao;
import org.daming.hoteler.service.IErrorService;
import org.daming.hoteler.service.IEventService;
import org.daming.hoteler.service.IRoomService;
import org.daming.hoteler.service.ISnowflakeService;
import org.daming.hoteler.service.IOrderService;
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

    private IOrderDao customerCheckinRecordDao;

    private IRoomService roomService;

    private ISnowflakeService snowflakeService;

    private IEventService eventService;

    private ObjectMapper jsonMapper;

    private IErrorService errorService;

    @Override
    public void create(Order customerCheckinRecord) throws HotelerException {
        var beginDate = customerCheckinRecord.getBeginDate().toLocalDate();
        var endDate =  customerCheckinRecord.getEndDate().toLocalDate();
        var durations = DateUtils.getDates(beginDate, endDate);
        for (LocalDate begin : durations) {
            var end = begin.plusDays(1L);
            var beginDateTime = begin.atTime(12, 0, 0);
            var endDateTime = end.atTime(12, 0, 0);
            var record = new Order();
            record.setBeginDate(beginDateTime);
            record.setEndDate(endDateTime);
            record.setRoomId(customerCheckinRecord.getRoomId());
            record.setCustomerId(customerCheckinRecord.getCustomerId());

            if (DateUtils.isToday(begin)) {
                this.doCreate(record);
            } else {
                this.dispatchCustomerCheckinRecord(record);
            }
        }
    }

    private void doCreate(Order customerCheckinRecord) throws HotelerException {
        var id  = this.snowflakeService.nextId();
        customerCheckinRecord.setId(id);
        this.processBeginDateAndEndDate(customerCheckinRecord);
        this.customerCheckinRecordDao.create(customerCheckinRecord);
        this.roomService.updateStatus(customerCheckinRecord.getRoomId(), RoomStatus.InUsed);
    }

    private void dispatchCustomerCheckinRecord(Order customerCheckinRecord) throws HotelerException {
        try {
            var message = new HotelerMessage();
            message.setEvent(HotelerEvent.CHECK_IN_TIME);
            var content = this.jsonMapper.writeValueAsString(customerCheckinRecord);
            message.setContent(content);
            this.eventService.publishEvent(message);
        } catch (JsonProcessingException ex) {
            throw this.errorService.createHotelerSystemException(ex.getMessage(), ex);
        }
    }

    @Override
    public void update(Order customerCheckinRecord) throws HotelerException {
        this.processBeginDateAndEndDate(customerCheckinRecord);
        this.customerCheckinRecordDao.update(customerCheckinRecord);
        this.roomService.updateStatus(customerCheckinRecord.getRoomId(), RoomStatus.InUsed);
    }

    @Override
    public Order get(long id) throws HotelerException {
        return this.customerCheckinRecordDao.get(id);
    }

    @Override
    public void delete(long id) throws HotelerException {
        var record = this.customerCheckinRecordDao.get(id);
        if (Objects.nonNull(record)) {
            this.roomService.updateStatus(record.getRoomId(), RoomStatus.InUsed);
            this.customerCheckinRecordDao.delete(id);
        }

    }

    private void processBeginDateAndEndDate(Order customerCheckinRecord) {
        var beginDate = customerCheckinRecord.getBeginDate();
        customerCheckinRecord.setBeginDate(LocalDateTime.of(beginDate.toLocalDate(), LocalTime.of(12, 0)));
        var endDate = customerCheckinRecord.getEndDate();
        customerCheckinRecord.setEndDate(LocalDateTime.of(endDate.toLocalDate(), LocalTime.of(12, 0)));
    }

    @Override
    public List<Order> list() throws HotelerException {
        return this.customerCheckinRecordDao.list();
    }

    @Override
    public List<Order> listCurrentDate() throws HotelerException {
        return this.customerCheckinRecordDao.list(LocalDate.now());
    }

    @Override
    public List<Order> listByRoomIdAndDate(long roomId, LocalDate date) {
        return this.customerCheckinRecordDao.listByRoom(roomId, date);
    }

    public OrderServiceImpl(
            IOrderDao customerCheckinRecordDao,
            IRoomService roomService,
            ISnowflakeService snowflakeService,
            IEventService eventService,
            ObjectMapper jsonMapper,
            IErrorService errorService) {
        super();
        this.customerCheckinRecordDao = customerCheckinRecordDao;
        this.roomService = roomService;
        this.snowflakeService = snowflakeService;
        this.eventService = eventService;
        this.jsonMapper = jsonMapper;
        this.errorService = errorService;
    }
}
