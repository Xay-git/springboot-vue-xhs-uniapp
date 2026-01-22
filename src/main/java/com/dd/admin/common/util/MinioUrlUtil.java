package com.dd.admin.common.util;

import com.dd.admin.common.service.MinioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * MinIO URL处理工具类
 * 提供统一的URL刷新和处理方法
 */
@Slf4j
@Component
public class MinioUrlUtil {

    @Autowired
    private MinioService minioService;

    /**
     * 从MinIO URL中提取objectName
     * @param url MinIO URL
     * @return objectName，如果提取失败返回null
     */
    public String extractObjectName(String url) {
        if (url == null || url.isEmpty()) {
            return null;
        }
        
        try {
            String[] parts = url.split("/");
            if (parts.length >= 4) {
                String objectName = String.join("/", java.util.Arrays.copyOfRange(parts, 4, parts.length));
                // 去除查询参数
                if (objectName.contains("?")) {
                    objectName = objectName.substring(0, objectName.indexOf("?"));
                }
                return objectName;
            }
        } catch (Exception e) {
            log.warn("提取objectName失败: {}", e.getMessage());
        }
        
        return null;
    }

    /**
     * 刷新MinIO URL
     * 如果URL包含查询参数，说明是MinIO URL，需要刷新
     * @param url 原始URL
     * @return 刷新后的URL，如果刷新失败返回原URL
     */
    public String refreshMinioUrl(String url) {
        if (url == null || url.isEmpty() || !url.contains("?")) {
            return url;
        }
        
        // 不需要try-catch，因为MinioService.getFileUrl现在会在MinIO不可用时返回原始URL
        String objectName = extractObjectName(url);
        if (objectName != null && !objectName.isEmpty()) {
            String newUrl = minioService.getFileUrl(objectName);
            if (newUrl != null && !newUrl.isEmpty()) {
                return newUrl;
            }
        }
        
        // 刷新失败时返回原URL
        return url;
    }

    /**
     * 批量刷新MinIO URL
     * @param urls URL列表
     * @return 刷新后的URL列表
     */
    public java.util.List<String> refreshMinioUrls(java.util.List<String> urls) {
        if (urls == null || urls.isEmpty()) {
            return urls;
        }
        
        return urls.stream()
                .map(this::refreshMinioUrl)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * 设置首图URL
     * 如果首图为空且图片列表不为空，则设置为第一张图片
     * 如果首图是MinIO URL，则刷新URL
     * @param firstPicture 当前首图URL
     * @param imgUrls 图片URL列表
     * @return 处理后的首图URL
     */
    public String setFirstPictureUrl(String firstPicture, java.util.List<String> imgUrls) {
        if ((firstPicture == null || firstPicture.isEmpty()) && imgUrls != null && !imgUrls.isEmpty()) {
            return imgUrls.get(0);
        } else if (firstPicture != null && firstPicture.contains("?")) {
            return refreshMinioUrl(firstPicture);
        }
        return firstPicture;
    }

    /**
     * 检查URL是否为MinIO URL
     * @param url URL字符串
     * @return 如果包含查询参数则认为是MinIO URL
     */
    public boolean isMinioUrl(String url) {
        return url != null && !url.isEmpty() && url.contains("?");
    }
}