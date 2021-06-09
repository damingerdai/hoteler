package org.daming.hoteler.pojo.stat;

import java.io.Serializable;

/**
 * room的数量统计
 *
 * @author gming001
 * @create 2021-06-09 23:18
 **/
public class RoomNumsStat implements Serializable {

    private static final long serialVersionUID = -7158935107949379179L;

    /**
     * 总数
     */
    private int totalNums;

    /**
     * 正在使用的数量
     */
    private int inUseNums;

    /**
     * 空闲的数量
     */
    private int notUsedNums;

    public int getTotalNums() {
        return totalNums;
    }

    public RoomNumsStat setTotalNums(int totalNums) {
        this.totalNums = totalNums;
        return this;
    }

    public int getInUseNums() {
        return inUseNums;
    }

    public RoomNumsStat setInUseNums(int inUseNums) {
        this.inUseNums = inUseNums;
        return this;
    }

    public int getNotUsedNums() {
        return this.notUsedNums;
    }

    public RoomNumsStat setNotUsedNums(int notUsedNums) {
        this.notUsedNums = notUsedNums;
        return this;
    }

    public RoomNumsStat(int totalNums, int inUseNums, int notUsedNums) {
        super();
        this.totalNums = totalNums;
        this.inUseNums = inUseNums;
        this.notUsedNums = notUsedNums;
    }

    public RoomNumsStat(int inUseNums, int notUsedNums) {
        super();
        this.totalNums = inUseNums + notUsedNums;
        this.inUseNums = inUseNums;
        this.notUsedNums = notUsedNums;
    }

    public RoomNumsStat() {
        super();
    }
}
