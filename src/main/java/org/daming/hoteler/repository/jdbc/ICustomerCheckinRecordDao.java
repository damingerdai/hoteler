package org.daming.hoteler.repository.jdbc;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.CustomerCheckinRecord;

import java.time.LocalDate;
import java.util.List;

/**
 * @author gming001
 * @create 2021-03-05 15:59
 **/
public interface ICustomerCheckinRecordDao {

    void create(CustomerCheckinRecord customerCheckinRecord) throws HotelerException;

    void update(CustomerCheckinRecord customerCheckinRecord) throws HotelerException;

    CustomerCheckinRecord get(long id) throws HotelerException;

    void delete(long id) throws HotelerException;

    List<CustomerCheckinRecord> list() throws HotelerException;

    List<CustomerCheckinRecord> list(LocalDate date) throws HotelerException;

    List<CustomerCheckinRecord> listByRoom(long roomId, LocalDate date) throws HotelerException;
}
