package org.daming.hoteler.pojo.time;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.StringJoiner;

/**
 * 一周日期的时间
 *
 * @author gming001
 * @create 2021-06-08 23:04
 **/
public class WeekDuration implements Serializable {

    private static final long serialVersionUID = 2333261754960921980L;

    /**
     * 周日
     */
    private LocalDate sunday;

    /**
     * 周一
     */
    private LocalDate monday;

    /**
     * 周二
     */
    private LocalDate tuesday;

    /**
     * 周三
     */
    private LocalDate wednesday;

    /**
     * 周四
     */
    private LocalDate thursday;

    /**
     * 周五
     */
    private LocalDate friday;

    /**
     * 周六
     */
    private LocalDate saturday;

    public LocalDate getSunday() {
        return sunday;
    }

    public WeekDuration setSunday(LocalDate sunday) {
        this.sunday = sunday;
        return this;
    }

    public LocalDate getMonday() {
        return monday;
    }

    public WeekDuration setMonday(LocalDate monday) {
        this.monday = monday;
        return this;
    }

    public LocalDate getTuesday() {
        return tuesday;
    }

    public WeekDuration setTuesday(LocalDate tuesday) {
        this.tuesday = tuesday;
        return this;
    }

    public LocalDate getWednesday() {
        return wednesday;
    }

    public WeekDuration setWednesday(LocalDate wednesday) {
        this.wednesday = wednesday;
        return this;
    }

    public LocalDate getThursday() {
        return thursday;
    }

    public WeekDuration setThursday(LocalDate thursday) {
        this.thursday = thursday;
        return this;
    }

    public LocalDate getFriday() {
        return friday;
    }

    public WeekDuration setFriday(LocalDate friday) {
        this.friday = friday;
        return this;
    }

    public LocalDate getSaturday() {
        return saturday;
    }

    public WeekDuration setSaturday(LocalDate saturday) {
        this.saturday = saturday;
        return this;
    }

    public WeekDuration(LocalDate sunday, LocalDate monday, LocalDate tuesday, LocalDate wednesday, LocalDate thursday, LocalDate friday, LocalDate saturday) {
        super();
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
    }

    public WeekDuration() {
        super();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", WeekDuration.class.getSimpleName() + "[", "]")
                .add("sunday=" + sunday)
                .add("monday=" + monday)
                .add("tuesday=" + tuesday)
                .add("wednesday=" + wednesday)
                .add("thursday=" + thursday)
                .add("friday=" + friday)
                .add("saturday=" + saturday)
                .toString();
    }
}
