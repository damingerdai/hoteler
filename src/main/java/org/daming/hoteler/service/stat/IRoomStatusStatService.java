package org.daming.hoteler.service.stat;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.stat.RoomNumsStat;

/**
 * 房间状态统计处理服务
 *
 * @author gming001
 * @create 2021-06-09 20:07
 **/
public interface IRoomStatusStatService {

    RoomNumsStat countRoomNumStatistics() throws HotelerException;
}
