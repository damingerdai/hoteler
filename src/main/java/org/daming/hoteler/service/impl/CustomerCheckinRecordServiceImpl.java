package org.daming.hoteler.service.impl;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.CustomerCheckinRecord;
import org.daming.hoteler.pojo.enums.RoomStatus;
import org.daming.hoteler.repository.jdbc.ICustomerCheckinRecordDao;
import org.daming.hoteler.service.IRoomService;
import org.daming.hoteler.service.ISnowflakeService;
import org.daming.hoteler.service.ICustomerCheckinRecordService;
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
public class CustomerCheckinRecordServiceImpl implements ICustomerCheckinRecordService {

    private ICustomerCheckinRecordDao customerCheckinRecordDao;

    private IRoomService roomService;

    private ISnowflakeService snowflakeService;

    @Override
    public long create(CustomerCheckinRecord customerCheckinRecord) throws HotelerException {
        var id  = this.snowflakeService.nextId();
        customerCheckinRecord.setId(id);
        this.processBeginDateAndEndDate(customerCheckinRecord);
        this.customerCheckinRecordDao.create(customerCheckinRecord);
        this.roomService.updateStatus(customerCheckinRecord.getRoomId(), RoomStatus.InUsed);
        return id;
    }

    @Override
    public void update(CustomerCheckinRecord customerCheckinRecord) throws HotelerException {
        this.processBeginDateAndEndDate(customerCheckinRecord);
        this.customerCheckinRecordDao.update(customerCheckinRecord);
        this.roomService.updateStatus(customerCheckinRecord.getRoomId(), RoomStatus.InUsed);
    }

    @Override
    public CustomerCheckinRecord get(long id) throws HotelerException {
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

    private void processBeginDateAndEndDate(CustomerCheckinRecord customerCheckinRecord) {
        var beginDate = customerCheckinRecord.getBeginDate();
        customerCheckinRecord.setBeginDate(LocalDateTime.of(beginDate.toLocalDate(), LocalTime.of(12, 0)));
        var endDate = customerCheckinRecord.getEndDate();
        customerCheckinRecord.setEndDate(LocalDateTime.of(endDate.toLocalDate(), LocalTime.of(12, 0)));
    }

    @Override
    public List<CustomerCheckinRecord> list() throws HotelerException {
        return this.customerCheckinRecordDao.list();
    }

    @Override
    public List<CustomerCheckinRecord> listCurrentDate() throws HotelerException {
        return this.customerCheckinRecordDao.list(LocalDate.now());
    }

    @Override
    public List<CustomerCheckinRecord> listByRoomIdAndDate(long roomId, LocalDate date) {
        return this.customerCheckinRecordDao.listByRoom(roomId, date);
    }

    public CustomerCheckinRecordServiceImpl(ICustomerCheckinRecordDao customerCheckinRecordDao,IRoomService roomService, ISnowflakeService snowflakeService) {
        super();
        this.customerCheckinRecordDao = customerCheckinRecordDao;
        this.roomService = roomService;
        this.snowflakeService = snowflakeService;
    }
}
