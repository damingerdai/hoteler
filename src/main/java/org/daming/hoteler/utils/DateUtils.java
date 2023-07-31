package org.daming.hoteler.utils;

import org.daming.hoteler.pojo.time.WeekDuration;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static long getDaysNums(LocalDate beginDate, LocalDate endDate) {
        var duration = Duration.between(beginDate, endDate);
        return duration.toDays();
    }

    public static long getDaysNums(LocalDateTime beginDate, LocalDateTime endDate) {
        var duration = Duration.between(beginDate, endDate);
        return duration.toDays();
    }

    public static List<LocalDate> getDates(LocalDate beginDate, LocalDate endDate) {
        var stream = beginDate.datesUntil(endDate);
        return stream.collect( Collectors.toList() );
    }

    public static boolean isToday(LocalDate date) {
        return LocalDate.now(ZoneId.systemDefault()).isEqual(date);
    }

    public static void main(String[] args) {
        final LocalDate myBirthday = LocalDate.of(2023,7, 23);
        final LocalDate christmas = LocalDate.of(2023, 7, 29);

        System.out.println("Day-Stream:\n");
        Stream<LocalDate> daysUntil = myBirthday.datesUntil(christmas);
        daysUntil.forEach(System.out::println);
    }
}
