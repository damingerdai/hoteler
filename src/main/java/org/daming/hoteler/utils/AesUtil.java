package org.daming.hoteler.utils;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author gming001
 * @version 2022-06-01 11:12
 */
public class AesUtil {

    public static void main(String[] args) throws UnsupportedEncodingException {
        for (int i = 0; i < 10; i++) {
            String key = generateKey("12345678"); // 提前生成的一个key
            System.out.println("key: " + key);
            String ps = encode(key, "123");
            System.out.println("加密: " + ps);
            String res = decode(key, ps);
            System.out.println("解密: " + res);
            String ps2 = encode(key, "123");
            System.out.println("加密: " + ps2);
        }
    }


    /**
     * 生成key
     * @return
     */
    public static String generateKey() {
        try {
            var keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(new SecureRandom());
            var secretKey = keyGenerator.generateKey();
            var byteKey = secretKey.getEncoded();
            return Hex.encodeHexString(byteKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 生成key
     * @return
     */
    public static String generateKey(String key) {
        var byteArray = key.getBytes(StandardCharsets.UTF_8);
        var hex = Hex.encodeHexString(byteArray);
        var byteKey = hex.getBytes();
        return Hex.encodeHexString(byteKey);
    }

    /**
     * AES加密
     * @param thisKey
     * @param data
     * @return
     */
    public static String encode(String thisKey, String data) {
        try {
            // 转换KEY
            var key = new SecretKeySpec(Hex.decodeHex(thisKey),"AES");
            //System.out.println(thisKey);

            // 加密
            var cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            var result = cipher.doFinal(data.getBytes());
            return Hex.encodeHexString(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * AES解密
     * @param thisKey
     * @param data
     * @return
     */
    public static String decode(String thisKey, String data) {
        try {
            // 转换KEY
            var key = new SecretKeySpec(Hex.decodeHex(thisKey),"AES");
            // 解密
            var cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            var result = cipher.doFinal(Hex.decodeHex(data));
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
