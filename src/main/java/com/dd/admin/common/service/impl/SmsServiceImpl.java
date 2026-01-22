package com.dd.admin.common.service.impl;

import com.alibaba.fastjson.JSON;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teautil.models.RuntimeOptions;
import com.dd.admin.common.config.AliyunSmsConfig;
import com.dd.admin.common.config.SmsProperties;
import com.dd.admin.common.exception.ApiException;
import com.dd.admin.common.service.SmsService;
import com.dd.admin.system.setting.service.SystemSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 短信服务实现类
 */
@Slf4j
@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private SmsProperties smsProperties;

    @Autowired
    private AliyunSmsConfig aliyunSmsConfig;

    @Autowired
    private SystemSettingService systemSettingService;

    @Override
    public void sendMessage(String phoneNumber, String code) throws Exception {
        // 获取短信发送模式
        String smsMode = systemSettingService.getSmsMode();
        
        if ("test".equals(smsMode)) {
            // 测试模式：只记录日志，不真实发送
            log.info("【测试模式】短信发送 - 手机号: {}, 验证码: {}", phoneNumber, code);
            return;
        }
        
        try {
            // 真实模式：发送短信
            log.info("【真实模式】开始发送短信 - 手机号: {}, 验证码: {}", phoneNumber, code);
            
            // 获取阿里云短信客户端
            Client client = aliyunSmsConfig.createClient();
            
            // 构建短信模板参数
            Map<String, String> templateParamMap = new HashMap<>();
            templateParamMap.put("code", code);
            String templateParam = JSON.toJSONString(templateParamMap);
            
            // 构建短信发送请求
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setPhoneNumbers(phoneNumber)
                    .setSignName(smsProperties.getSignName())
                    .setTemplateCode(smsProperties.getTemplateCode())
                    .setTemplateParam(templateParam);
            
            // 设置运行时选项
            RuntimeOptions runtime = new RuntimeOptions();
            
            // 发送短信
            SendSmsResponse sendSmsResponse = client.sendSmsWithOptions(sendSmsRequest, runtime);
            
            log.info("短信发送结果: Code={}, Message={}, RequestId={}, BizId={}", 
                    sendSmsResponse.getBody().getCode(), 
                    sendSmsResponse.getBody().getMessage(),
                    sendSmsResponse.getBody().getRequestId(),
                    sendSmsResponse.getBody().getBizId());
            
            // 检查发送结果
            if (sendSmsResponse.getBody() == null || !"OK".equals(sendSmsResponse.getBody().getCode())) {
                throw new ApiException("短信发送失败: " + 
                        (sendSmsResponse.getBody() != null ? sendSmsResponse.getBody().getMessage() : "未知错误"));
            }
        } catch (Exception e) {
            log.error("短信发送异常", e);
            throw new ApiException("短信发送异常: " + e.getMessage());
        }
    }
}