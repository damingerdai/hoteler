package org.daming.hoteler.repository.jdbc;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.Order;
import org.daming.hoteler.pojo.Pageable;
import org.daming.hoteler.pojo.Room;
import org.daming.hoteler.pojo.Sortable;
import org.daming.hoteler.pojo.request.OrderListRequest;

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

    @Deprecated
    List<Order> list(OrderListRequest request) throws HotelerException;

    List<Order> list(Pageable pageable) throws HotelerException;

    List<Order> list(Pageable pageable, Sortable sortable) throws HotelerException;

    List<Order> list(LocalDate date) throws HotelerException;

    List<Order> listByRoom(long roomId, LocalDate date) throws HotelerException;
}
