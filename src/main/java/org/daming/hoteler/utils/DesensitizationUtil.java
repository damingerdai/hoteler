package org.daming.hoteler.utils;

/**
 * 数据脱敏工具类
 * 实现类似Hutool中DesensitizedUtil的功能
 */
public class DesensitizationUtil {

    /**
     * 手机号脱敏
     * 显示前3位和后4位，中间4位脱敏
     * 例如：182****5678
     * @param phone 手机号
     * @return 脱敏后的手机号
     */
    public static String mobilePhone(String phone) {
        if (phone == null || phone.length() < 7) {
            return phone;
        }
        return hide(phone, 3, phone.length() - 4);
    }

    /**
     * 身份证号脱敏
     * 显示前3位和后4位，其余脱敏
     * 例如：410***********0007
     * @param idCard 身份证号
     * @return 脱敏后的身份证号
     */
    public static String idCardNum(String idCard) {
        return idCardNum(idCard, 3, 4);
    }

    /**
     * 身份证号脱敏(自定义显示位数)
     * @param idCard 身份证号
     * @param frontKeep 前面保留位数
     * @param endKeep 后面保留位数
     * @return 脱敏后的身份证号
     */
    public static String idCardNum(String idCard, int frontKeep, int endKeep) {
        if (idCard == null || idCard.length() < (frontKeep + endKeep)) {
            return idCard;
        }
        return hide(idCard, frontKeep, idCard.length() - endKeep);
    }

    /**
     * 银行卡号脱敏
     * 显示前4位和后4位，其余脱敏，每4位用空格分隔
     * 例如：6214 **** **** 8533
     * @param bankCard 银行卡号
     * @return 脱敏后的银行卡号
     */
    public static String bankCard(String bankCard) {
        if (bankCard == null || bankCard.length() < 8) {
            return bankCard;
        }

        // 去除所有空格
        String cardNo = bankCard.replaceAll("\\s+", "");

        // 前4位和后4位保留
        String front = cardNo.substring(0, 4);
        String end = cardNo.substring(cardNo.length() - 4);

        // 中间部分用*替换
        StringBuilder sb = new StringBuilder(front);
        for (int i = 4; i < cardNo.length() - 4; i++) {
            if (i % 4 == 0) {
                sb.append(" ");
            }
            sb.append("*");
        }
        sb.append(" ").append(end);

        return sb.toString();
    }

    /**
     * 中文姓名脱敏
     * 只显示姓氏，名字用*代替
     * 例如：张**
     * @param chineseName 中文姓名
     * @return 脱敏后的姓名
     */
    public static String chineseName(String chineseName) {
        if (chineseName == null || chineseName.isEmpty()) {
            return chineseName;
        }

        if (chineseName.length() == 1) {
            return chineseName + "*";
        }

        return hide(chineseName, 1, chineseName.length());
    }

    /**
     * 地址脱敏
     * 只显示到地区，详细地址脱敏
     * @param address 地址
     * @param sensitiveSize 敏感信息长度
     * @return 脱敏后的地址
     */
    public static String address(String address, int sensitiveSize) {
        if (address == null || address.length() <= sensitiveSize) {
            return address;
        }

        int length = address.length();
        return hide(address, length - sensitiveSize, length);
    }

    /**
     * 邮箱脱敏
     * 显示邮箱前缀第一个字符和@符号后的完整内容
     * 例如：t***@example.com
     * @param email 邮箱地址
     * @return 脱敏后的邮箱
     */
    public static String email(String email) {
        if (email == null || !email.contains("@")) {
            return email;
        }

        String[] parts = email.split("@");
        if (parts[0].length() <= 1) {
            return "*@" + parts[1];
        }

        return hide(parts[0], 1, parts[0].length()) + "@" + parts[1];
    }

    /**
     * 密码脱敏
     * 全部用*代替
     * @param password 密码
     * @return 脱敏后的密码
     */
    public static String password(String password) {
        if (password == null) {
            return null;
        }
        return password.replaceAll(".", "*");
    }

    /**
     * 车牌号脱敏
     * 显示前3位和后1位，其余脱敏
     * 例如：沪B0***8
     * @param carLicense 车牌号
     * @return 脱敏后的车牌号
     */
    public static String carLicense(String carLicense) {
        if (carLicense == null || carLicense.length() < 4) {
            return carLicense;
        }
        return hide(carLicense, 3, carLicense.length() - 1);
    }

    /**
     * 座机号脱敏
     * 显示前4位和后2位，其余脱敏
     * 例如：010-******78
     * @param fixedPhone 座机号
     * @return 脱敏后的座机号
     */
    public static String fixedPhone(String fixedPhone) {
        if (fixedPhone == null || fixedPhone.length() < 6) {
            return fixedPhone;
        }

        // 处理带区号的座机号
        if (fixedPhone.contains("-")) {
            String[] parts = fixedPhone.split("-");
            if (parts.length == 2) {
                return parts[0] + "-" + hide(parts[1], 0, parts[1].length() - 2);
            }
        }

        return hide(fixedPhone, 4, fixedPhone.length() - 2);
    }

    /**
     * 自定义脱敏
     * @param str 原始字符串
     * @param startInclude 开始位置(包含)
     * @param endExclude 结束位置(不包含)
     * @return 脱敏后的字符串
     */
    public static String hide(String str, int startInclude, int endExclude) {
        if (str == null) {
            return null;
        }

        if (startInclude < 0) {
            startInclude = 0;
        }

        if (endExclude > str.length()) {
            endExclude = str.length();
        }

        if (startInclude > endExclude) {
            return str;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (i >= startInclude && i < endExclude) {
                sb.append("*");
            } else {
                sb.append(str.charAt(i));
            }
        }

        return sb.toString();
    }
}
