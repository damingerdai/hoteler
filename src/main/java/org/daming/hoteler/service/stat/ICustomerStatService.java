package org.daming.hoteler.service.stat;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.stat.PastWeekCustomerCountStat;

/**
 * 客户统计服务
 *
 * @author gming001
 * @create 2021-06-17 22:01
 **/
public interface ICustomerStatService {

    PastWeekCustomerCountStat countPastWeekCustomerCountStat() throws HotelerException;
}
