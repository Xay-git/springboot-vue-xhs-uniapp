package com.dd.admin.common.service;

import io.minio.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.dd.admin.system.setting.service.SystemSettingService;
import com.dd.admin.common.utils.RedisUtil;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * MinIO服务类
 *
 * @author 727869402@qq.com
 * @since 2024-05-23
 */
@Slf4j
@Service
public class MinioService implements ApplicationRunner {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private SystemSettingService systemSettingService;

    @Autowired
    private RedisUtil redisUtil;
    
    // 动态MinIO客户端，用于配置更新后的连接测试
    private MinioClient dynamicMinioClient;

    @Value("${minio.bucketName:}")
    private String defaultBucketName;
    
    // 使用AtomicBoolean保证线程安全
    private final AtomicBoolean connectionStatus = new AtomicBoolean(false);
    
    // Redis缓存键
    private static final String BUCKET_NAME_CACHE_KEY = "minio:bucket_name";
    // 缓存过期时间（秒）
    private static final long CACHE_EXPIRE_TIME = 300; // 5分钟
    
    /**
     * 获取存储桶名称，优先从缓存获取，然后从系统设置获取
     */
    private String getBucketName() {
        // 先从Redis缓存获取
        try {
            Object cachedBucketName = redisUtil.get(BUCKET_NAME_CACHE_KEY);
            if (cachedBucketName != null) {
                return cachedBucketName.toString();
            }
        } catch (Exception e) {
            // Redis异常时继续从数据库获取
            log.warn("从Redis获取存储桶名称失败: {}", e.getMessage());
        }
        
        // 缓存未命中，从系统设置获取
        String bucketName = systemSettingService.getSettingValue("MINIO_BUCKET_NAME");
        String finalBucketName = bucketName != null ? bucketName : defaultBucketName;
        
        // 将结果缓存到Redis
        try {
            if (finalBucketName != null) {
                redisUtil.set(BUCKET_NAME_CACHE_KEY, finalBucketName, CACHE_EXPIRE_TIME);
            }
        } catch (Exception e) {
            // Redis异常不影响业务逻辑
            log.warn("缓存存储桶名称到Redis失败: {}", e.getMessage());
        }
        
        return finalBucketName;
    }
    
    /**
     * 清除存储桶名称缓存
     */
    public void clearBucketNameCache() {
        try {
            redisUtil.del(BUCKET_NAME_CACHE_KEY);
            log.info("已清除存储桶名称缓存");
        } catch (Exception e) {
            log.warn("清除存储桶名称缓存失败: {}", e.getMessage());
        }
    }

    /**
     * 检查存储桶是否存在
     *
     * @param bucketName 存储桶名称
     * @return true存在，false不存在
     */
    public boolean bucketExists(String bucketName) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            log.error("检查存储桶是否存在失败: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 创建存储桶
     *
     * @param bucketName 存储桶名称
     */
    public void createBucket(String bucketName) {
        try {
            if (!bucketExists(bucketName)) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                log.info("创建存储桶成功: {}", bucketName);
            }
        } catch (Exception e) {
            log.error("创建存储桶失败: {}", e.getMessage());
            throw new RuntimeException("创建存储桶失败", e);
        }
    }

    /**
     * 上传文件
     *
     * @param file 文件
     * @param objectName 对象名称
     * @return 文件访问URL，如果MinIO服务不可用则返回原始URL
     */
    public String uploadFile(MultipartFile file, String objectName) {
        // 使用缓存的连接状态，避免每次都检查连接
        if (!connectionStatus.get()) {
            log.warn("MinIO服务不可用，无法上传文件，返回原始URL: {}", objectName);
            return objectName; // 返回原始URL
        }
        
        try {
            // 确保存储桶存在
            String bucketName = getBucketName();
            createBucket(bucketName);

            // 上传文件
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            log.info("文件上传成功: {}", objectName);
            return getFileUrl(objectName);
        } catch (Exception e) {
            log.error("文件上传失败: {}", e.getMessage());
            // 发生异常时返回原始URL，而不是抛出异常
            return objectName;
        }
    }

    /**
     * 上传文件流
     *
     * @param inputStream 文件流
     * @param objectName 对象名称
     * @param contentType 文件类型
     * @param size 文件大小
     * @return 文件访问URL，如果MinIO服务不可用则返回原始URL
     */
    public String uploadFile(InputStream inputStream, String objectName, String contentType, long size) {
        // 使用缓存的连接状态，避免每次都检查连接
        if (!connectionStatus.get()) {
            log.warn("MinIO服务不可用，无法上传文件，返回原始URL: {}", objectName);
            return objectName; // 返回原始URL
        }
        
        try {
            // 确保存储桶存在
            String bucketName = getBucketName();
            createBucket(bucketName);

            // 上传文件
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(inputStream, size, -1)
                            .contentType(contentType)
                            .build()
            );

            log.info("文件上传成功: {}", objectName);
            return getFileUrl(objectName);
        } catch (Exception e) {
            log.error("文件上传失败: {}", e.getMessage());
            // 发生异常时返回原始URL，而不是抛出异常
            return objectName;
        }
    }

    /**
     * 删除文件
     *
     * @param objectName 对象名称
     */
    public void deleteFile(String objectName) {
        try {
            String bucketName = getBucketName();
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
            log.info("文件删除成功: {}", objectName);
        } catch (Exception e) {
            log.error("文件删除失败: {}", e.getMessage());
            throw new RuntimeException("文件删除失败", e);
        }
    }

    /**
     * 获取文件访问URL
     *
     * @param objectName 对象名称
     * @return 文件访问URL，如果MinIO服务不可用则返回原始URL
     */
    public String getFileUrl(String objectName) {
        // 使用缓存的连接状态，避免每次都检查连接
        if (!connectionStatus.get()) {
            log.warn("MinIO服务不可用，返回原始URL: {}", objectName);
            return objectName; // 返回原始URL
        }
        
        try {
            String bucketName = getBucketName();
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(objectName)
                            .expiry(7, TimeUnit.DAYS) // 7天有效期
                            .build()
            );
        } catch (Exception e) {
            log.error("获取文件URL失败: {}", e.getMessage());
            // 发生异常时返回原始URL，而不是抛出异常
            return objectName;
        }
    }

    /**
     * 获取文件流
     *
     * @param objectName 对象名称
     * @return 文件流
     */
    public InputStream getFileStream(String objectName) {
        try {
            String bucketName = getBucketName();
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
        } catch (Exception e) {
            log.error("获取文件流失败: {}", e.getMessage());
            throw new RuntimeException("获取文件流失败", e);
        }
    }

    /**
     * 重新初始化MinIO客户端（使用最新配置）
     */
    public void reinitializeMinioClient() {
        try {
            String endpoint = systemSettingService.getSettingValue("MINIO_ENDPOINT");
            String accessKey = systemSettingService.getSettingValue("MINIO_ACCESS_KEY");
            String secretKey = systemSettingService.getSettingValue("MINIO_SECRET_KEY");
            
            if (endpoint != null && accessKey != null && secretKey != null) {
                this.dynamicMinioClient = MinioClient.builder()
                        .endpoint(endpoint)
                        .credentials(accessKey, secretKey)
                        .build();
                log.info("MinIO客户端重新初始化成功");
            }
        } catch (Exception e) {
            log.error("MinIO客户端重新初始化失败: {}", e.getMessage());
        }
    }
    
    /**
     * 检查MinIO连通性并更新连接状态
     *
     * @return true连通，false不连通
     */
    public boolean checkConnection() {
        return checkConnection(false);
    }
    
    /**
     * 检查MinIO连通性并更新连接状态
     *
     * @param useLatestConfig 是否使用最新配置进行检查
     * @return true连通，false不连通
     */
    public boolean checkConnection(boolean useLatestConfig) {
        try {
            MinioClient clientToUse = minioClient;
            
            if (useLatestConfig) {
                // 重新初始化客户端以使用最新配置
                reinitializeMinioClient();
                clientToUse = dynamicMinioClient != null ? dynamicMinioClient : minioClient;
            }
            
            // 尝试列出存储桶来检查连通性
            clientToUse.listBuckets();
            log.info("MinIO连通性检查成功");
            // 更新连接状态
            connectionStatus.set(true);
            return true;
        } catch (Exception e) {
            log.error("MinIO连通性检查失败: {}", e.getMessage());
            // 更新连接状态
            connectionStatus.set(false);
            return false;
        }
    }
    
    /**
     * 获取当前MinIO连接状态
     *
     * @return true连通，false不连通
     */
    public boolean getConnectionStatus() {
        return connectionStatus.get();
    }
    
    /**
     * 手动设置MinIO连接状态
     *
     * @param status 连接状态
     */
    public void setConnectionStatus(boolean status) {
        connectionStatus.set(status);
        log.info("手动设置MinIO连接状态为: {}", status);
    }
    
    /**
     * 应用启动时初始化MinIO连接状态
     */
    @Override
    public void run(ApplicationArguments args) {
        log.info("应用启动，初始化MinIO连接状态...");
        checkConnection();
    }
}