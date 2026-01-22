package com.dd.admin.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 七牛云配置属性
 */
@Data
@Component
@ConfigurationProperties(prefix = "qiniu")
public class QiniuProperties {
    
    /**
     * 访问密钥
     */
    private String accessKey;
    
    /**
     * 秘密密钥
     */
    private String secretKey;
    
    /**
     * 存储空间名称
     */
    private String bucketName;
    
    /**
     * 访问域名
     */
    private String domain;
    
    /**
     * 存储区域
     */
    private String region = "huanan";
}