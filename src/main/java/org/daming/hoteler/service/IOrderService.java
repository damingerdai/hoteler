package org.daming.hoteler.service;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.Order;
import org.daming.hoteler.pojo.request.OrderListRequest;
import org.daming.hoteler.pojo.vo.OrderVO;

import java.time.LocalDate;
import java.util.List;

/**
 * 用户和房间关联
 *
 * @author gming001
 * @create 2021-03-05 15:38
 **/
public interface IOrderService {

    void create(Order customerCheckinRecord) throws HotelerException;

    void update(Order customerCheckinRecord) throws HotelerException;

    Order get(long id) throws HotelerException;

    void delete(long id) throws HotelerException;

    int count() throws HotelerException;

    List<Order> list() throws HotelerException;

    List<OrderVO> list(OrderListRequest request) throws HotelerException;

    List<Order> listCurrentDate() throws HotelerException;

    List<Order> listByRoomIdAndDate(long roomId, LocalDate date);
}
