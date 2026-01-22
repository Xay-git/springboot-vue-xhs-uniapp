package com.dd.admin.config;

import com.dd.admin.common.utils.StringUtil;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.dd.admin.system.setting.service.SystemSettingService;

/**
 * MinIO配置类
 *
 * @author 727869402@qq.com
 * @since 2024-05-23
 */
@Configuration
public class MinioConfig {

    @Value("${minio.endpoint:http://localhost:9000}")
    private String defaultEndpoint;

    @Value("${minio.accessKey:minioadmin}")
    private String defaultAccessKey;

    @Value("${minio.secretKey:minioadmin}")
    private String defaultSecretKey;

    @Value("${minio.bucketName:}")
    private String defaultBucketName;

    @Autowired(required = false)
    private SystemSettingService systemSettingService;

    @Bean
    public MinioClient minioClient() {
        String endpoint = getMinioConfig("MINIO_ENDPOINT", defaultEndpoint);
        String accessKey = getMinioConfig("MINIO_ACCESS_KEY", defaultAccessKey);
        String secretKey = getMinioConfig("MINIO_SECRET_KEY", defaultSecretKey);
        
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }

    private String getMinioConfig(String key, String defaultValue) {
        if (systemSettingService != null) {
            String value = systemSettingService.getSettingValue(key);
            // 如果数据库中的值不为空且不是空字符串，则使用数据库的值
            if (value != null && !value.trim().isEmpty()) {
                return value;
            }
        }
        // 否则使用application.yml中的默认值
        return defaultValue;
    }
}
