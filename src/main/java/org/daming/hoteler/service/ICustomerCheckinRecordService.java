package org.daming.hoteler.service;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.CustomerCheckinRecord;

import java.time.LocalDate;
import java.util.List;

/**
 * 用户和房间关联
 *
 * @author gming001
 * @create 2021-03-05 15:38
 **/
public interface ICustomerCheckinRecordService {

    void create(CustomerCheckinRecord customerCheckinRecord) throws HotelerException;

    void update(CustomerCheckinRecord customerCheckinRecord) throws HotelerException;

    CustomerCheckinRecord get(long id) throws HotelerException;

    void delete(long id) throws HotelerException;

    List<CustomerCheckinRecord> list() throws HotelerException;

    List<CustomerCheckinRecord> listCurrentDate() throws HotelerException;

    List<CustomerCheckinRecord> listByRoomIdAndDate(long roomId, LocalDate date);
}
