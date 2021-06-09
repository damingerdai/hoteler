package org.daming.hoteler.service.stat;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.stat.RoomNumsStat;
import org.daming.hoteler.pojo.stat.RoomStatusStat;

/**
 * 房间状态统计处理服务
 *
 * @author gming001
 * @create 2021-06-09 20:07
 **/
public interface IRoomStatusStatService {

    RoomStatusStat countRoomStatusStatistics() throws HotelerException;

    RoomNumsStat countRoomNumStatistics() throws HotelerException;
}
