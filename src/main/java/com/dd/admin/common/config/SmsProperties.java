package com.dd.admin.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 短信服务属性配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "aliyun.sms")
public class SmsProperties {

    /**
     * 是否启用真实短信发送
     */
    private boolean enabled;
    
    /**
     * 阿里云账号的accessKeyId
     */
    private String accessKeyId;
    
    /**
     * 阿里云账号的accessKeySecret
     */
    private String accessKeySecret;
    
    /**
     * 短信签名名称
     */
    private String signName;
    
    /**
     * 短信模板CODE
     */
    private String templateCode;
    
    /**
     * 短信验证码有效期（分钟）
     */
    private Integer expireMinutes = 5;
} 