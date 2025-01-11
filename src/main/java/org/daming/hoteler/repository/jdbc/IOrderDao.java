package org.daming.hoteler.repository.jdbc;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.Order;

import java.time.LocalDate;
import java.util.List;

/**
 * @author gming001
 * @create 2021-03-05 15:59
 **/
public interface IOrderDao {

    void create(Order order) throws HotelerException;

    void update(Order order) throws HotelerException;

    Order get(long id) throws HotelerException;

    void delete(long id) throws HotelerException;

    List<Order> list() throws HotelerException;

    List<Order> list(LocalDate date) throws HotelerException;

    List<Order> listByRoom(long roomId, LocalDate date) throws HotelerException;
}
