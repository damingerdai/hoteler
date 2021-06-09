package org.daming.hoteler.pojo.stat;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * 房间状态统计
 *
 * @author gming001
 * @create 2021-06-08 22:21
 **/
public class RoomStatusStat implements Serializable {

    private static final long serialVersionUID = 479858204822054552L;

    private int lastWeekInUsedRoomNum;

    private int currentWeekInUsedRoomNum;

    public int getLastWeekInUsedRoomNum() {
        return lastWeekInUsedRoomNum;
    }

    public RoomStatusStat setLastWeekInUsedRoomNum(int lastWeekInUsedRoomNum) {
        this.lastWeekInUsedRoomNum = lastWeekInUsedRoomNum;
        return this;
    }

    public int getCurrentWeekInUsedRoomNum() {
        return currentWeekInUsedRoomNum;
    }

    public RoomStatusStat setCurrentWeekInUsedRoomNum(int currentWeekInUsedRoomNum) {
        this.currentWeekInUsedRoomNum = currentWeekInUsedRoomNum;
        return this;
    }

    public RoomStatusStat(int lastWeekInUsedRoomNum, int currentWeekInUsedRoomNum) {
        super();
        this.lastWeekInUsedRoomNum = lastWeekInUsedRoomNum;
        this.currentWeekInUsedRoomNum = currentWeekInUsedRoomNum;
    }

    public RoomStatusStat() {
        super();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RoomStatusStat.class.getSimpleName() + "[", "]")
                .add("lastWeekInUsedRoomNum=" + lastWeekInUsedRoomNum)
                .add("currentWeekInUsedRoomNum=" + currentWeekInUsedRoomNum)
                .toString();
    }
}
