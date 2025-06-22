package org.daming.hoteler.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DesensitizationUtilTest {

    // 手机号脱敏测试
    @Test
    public void testMobilePhone() {
        // 正常11位手机号
        assertEquals("182****6791", DesensitizationUtil.mobilePhone("18216556791"));

        // 短于7位的手机号
        assertEquals("12345", DesensitizationUtil.mobilePhone("12345"));

        // null值处理
        assertNull(DesensitizationUtil.mobilePhone(null));

        // 边界值测试
        assertEquals("123****8910", DesensitizationUtil.mobilePhone("12345678910"));
    }

    // 身份证号脱敏测试
    @Test
    public void testIdCardNum() {
        // 18位身份证号
        assertEquals("410***********0007", DesensitizationUtil.idCardNum("410328200001010007"));

        // 15位身份证号
        assertEquals("410*******0007", DesensitizationUtil.idCardNum("410328001010007"));

        // 自定义前后保留位数
        assertEquals("410328********07", DesensitizationUtil.idCardNum("410328200001010007", 6, 2));

        // 短于保留位数的身份证号
        assertEquals("123", DesensitizationUtil.idCardNum("123"));

        // null值处理
        assertNull(DesensitizationUtil.idCardNum(null));
    }

    // 银行卡号脱敏测试
    @Test
    public void testBankCard() {
        // 16位银行卡号
        assertEquals("6214 **** **** 8533", DesensitizationUtil.bankCard("6214856213978533"));

        // 带空格的银行卡号
        assertEquals("6214 **** **** 8533", DesensitizationUtil.bankCard("6214 8562 1397 8533"));

        // 短于8位的卡号
        assertEquals("1234567", DesensitizationUtil.bankCard("1234567"));

        // null值处理
        assertNull(DesensitizationUtil.bankCard(null));
    }

    // 中文姓名脱敏测试
    @Test
    public void testChineseName() {
        // 正常姓名
        assertEquals("张**", DesensitizationUtil.chineseName("张三郎"));

        // 单字姓名
        assertEquals("李*", DesensitizationUtil.chineseName("李"));

        // 两字姓名
        assertEquals("王*", DesensitizationUtil.chineseName("王五"));

        // null值处理
        assertNull(DesensitizationUtil.chineseName(null));

        // 空字符串
        assertEquals("", DesensitizationUtil.chineseName(""));
    }

    // 邮箱脱敏测试
    @Test
    public void testEmail() {
        // 正常邮箱
        assertEquals("t***@example.com", DesensitizationUtil.email("test@example.com"));

        // 前缀只有1个字符的邮箱
        assertEquals("*@example.com", DesensitizationUtil.email("t@example.com"));

        // 无@符号的邮箱
        assertEquals("invalid", DesensitizationUtil.email("invalid"));

        // null值处理
        assertNull(DesensitizationUtil.email(null));
    }

    // 密码脱敏测试
    @Test
    public void testPassword() {
        // 正常密码
        assertEquals("********", DesensitizationUtil.password("password"));

        // 空密码
        assertEquals("", DesensitizationUtil.password(""));

        // null值处理
        assertNull(DesensitizationUtil.password(null));
    }

    // 车牌号脱敏测试
    @Test
    public void testCarLicense() {
        // 正常车牌号
        assertEquals("沪B0***8", DesensitizationUtil.carLicense("沪B01238"));

        // 短车牌号
        assertEquals("沪B0", DesensitizationUtil.carLicense("沪B0"));

        // null值处理
        assertNull(DesensitizationUtil.carLicense(null));
    }

    // 座机号脱敏测试
    @Test
    public void testFixedPhone() {
        // 带区号的座机号
        assertEquals("010-******78", DesensitizationUtil.fixedPhone("010-12345678"));

        // 不带区号的座机号
        assertEquals("1234**78", DesensitizationUtil.fixedPhone("12345678"));

        // 短座机号
        assertEquals("123", DesensitizationUtil.fixedPhone("123"));

        // null值处理
        assertNull(DesensitizationUtil.fixedPhone(null));
    }

    // 自定义脱敏测试
    @Test
    public void testHide() {
        // 正常脱敏
        assertEquals("123****890", DesensitizationUtil.hide("1234567890", 3, 7));

        // 开始位置大于结束位置
        assertEquals("1234567890", DesensitizationUtil.hide("1234567890", 7, 3));

        // 开始位置为0
        assertEquals("****567890", DesensitizationUtil.hide("1234567890", 0, 4));

        // 结束位置超过字符串长度
        assertEquals("123****", DesensitizationUtil.hide("1234567890", 3, 15));

        // null值处理
        assertNull(DesensitizationUtil.hide(null, 1, 2));
    }
}