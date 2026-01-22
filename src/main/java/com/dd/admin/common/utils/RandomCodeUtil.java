package com.dd.admin.common.utils;

import java.util.Random;

/**
 * 随机验证码工具类
 */
public class RandomCodeUtil {

    /**
     * 生成指定长度的随机数字验证码
     * @param length 验证码长度
     * @return 随机数字验证码
     */
    public static String generateRandomCode(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }

    /**
     * 生成6位随机数字验证码
     * @return 6位随机数字验证码
     */
    public static String generateRandomCode() {
        return generateRandomCode(6);
    }
} 