package org.daming.hoteler.utils;

import org.daming.hoteler.pojo.time.WeekDuration;

import java.time.Duration;
import java.time.LocalDate;

/**
 * 时间工具类
 *
 * @author gming001
 * @create 2021-06-08 22:50
 **/
public class DateUtils {

    public static WeekDuration getWeekDate(LocalDate date) {
        var doyOfWeek = date.getDayOfWeek().getValue() ;
        return new WeekDuration()
                .setSunday(date.minusDays(doyOfWeek))
                .setSaturday(date.plusDays(8 - doyOfWeek));
    }
}
