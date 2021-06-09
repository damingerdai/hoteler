package org.daming.hoteler.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilsTest {

    @Test
    public void testGetWeekDate() {
        var localDate = LocalDate.of(2021, 6, 9);
        var weekDate = DateUtils.getWeekDate(localDate);
        var sunday = weekDate.getSunday();
        assertEquals(6, sunday.getDayOfMonth());
    }

}