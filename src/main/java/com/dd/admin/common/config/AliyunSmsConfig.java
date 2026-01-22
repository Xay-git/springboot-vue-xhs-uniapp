package com.dd.admin.common.config;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云短信服务配置类
 */
@Configuration
public class AliyunSmsConfig {

    @Autowired
    private SmsProperties smsProperties;

    /**
     * 创建短信服务的客户端
     * @return 阿里云短信服务客户端
     * @throws Exception 创建客户端异常
     */
    public Client createClient() throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(smsProperties.getAccessKeyId())
                // 您的AccessKey Secret
                .setAccessKeySecret(smsProperties.getAccessKeySecret());
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new Client(config);
    }
} 