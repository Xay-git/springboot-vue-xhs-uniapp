package com.dd.admin.common.service;

public interface SmsService {
    /**
     * 发送短信验证码
     * @param phoneNumber 手机号码
     * @param code 验证码
     * @throws Exception 发送异常
     */
    void sendMessage(String phoneNumber, String code) throws Exception;
} 