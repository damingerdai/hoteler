package org.daming.hoteler.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author gming001
 * @version 2023-05-23 12:20
 */
public class IDCard {

    public static boolean isIDCardValid(String id) {
        if (id == null || id.length() != 18) {
            return false;
        }
        char[] charArray = id.toCharArray();
        // 判断前17位是否都是数字
        for (int i = 0; i < 17; i++) {
            if (charArray[i] < '0' || charArray[i] > '9') {
                return false;
            }
        }
        // 判断最后一位是否为数字或者X
        if (charArray[17] < '0' || charArray[17] > '9') {
            if (charArray[17] != 'X' && charArray[17] != 'x') {
                return false;
            }
        }
        // 判断省份代码是否有效
        String provinceCode = id.substring(0, 2);
        if (!isValidProvinceCode(provinceCode)) {
            return false;
        }
        // 判断出生日期是否有效
        String birthday = id.substring(6, 14);
        if (!isValidDate(birthday)) {
            return false;
        }
        // 判断校验码是否有效
        return isValidCheckCode(id);
    }

    private static boolean isValidProvinceCode(String provinceCode) {
        // 省份代码列表
        String[] provinceCodes = {"11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65", "71", "81", "82"};
        for (String code : provinceCodes) {
            if (code.equals(provinceCode)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isValidDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            // TODO log e
            return false;
        }
    }

    private static boolean isValidCheckCode(String id) {
        // 加权因子
        int[] weightFactors = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
        // 校验码对应值
        char[] checkCodeValues = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            int num = Character.digit(id.charAt(i), 10);
            sum += num * weightFactors[i];
        }
        int mod = sum % 11;
        char checkCode = checkCodeValues[mod];
        return checkCode == id.charAt(17);
    }

}
