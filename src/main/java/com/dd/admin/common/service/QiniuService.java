package com.dd.admin.common.service;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.dd.admin.common.config.QiniuProperties;
import com.dd.admin.system.setting.service.SystemSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 七牛云存储服务
 */
@Slf4j
@Service
public class QiniuService {

    @Autowired
    private SystemSettingService systemSettingService;

    /**
     * 上传文件到七牛云
     *
     * @param file 文件
     * @param directory 目录
     * @return 文件访问URL
     */
    public String uploadFile(MultipartFile file, String directory) {
        try {
            // 获取七牛云配置
            String accessKey = systemSettingService.getSettingValue("QINIU_ACCESS_KEY");
            String secretKey = systemSettingService.getSettingValue("QINIU_SECRET_KEY");
            String bucketName = systemSettingService.getSettingValue("QINIU_BUCKET_NAME");
            String domain = systemSettingService.getSettingValue("QINIU_DOMAIN");
            String region = systemSettingService.getSettingValue("QINIU_REGION", "z0");
            log.debug("qiniu accessKey: {}, secretKey: {}, bucketName: {}, domain: {}, region: {}", accessKey, secretKey, bucketName, domain, region);

            if (accessKey == null || secretKey == null || bucketName == null || domain == null) {
                throw new RuntimeException("七牛云配置不完整");
            }

            // 构造一个带指定 Region 对象的配置类
            Configuration cfg = new Configuration(getRegion(region));
            cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;

            UploadManager uploadManager = new UploadManager(cfg);

            // 生成文件名
            String originalFilename = file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            if (originalFilename != null && originalFilename.contains(".")) {
                String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                fileName += suffix;
            }

            // 确保目录以斜杠结尾
            if (directory != null && !directory.isEmpty() && !directory.endsWith("/")) {
                directory += "/";
            }

            String key = (directory != null ? directory : "") + fileName;

            // 生成上传凭证
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucketName);

            // 上传文件
            Response response = uploadManager.put(file.getInputStream(), key, upToken, null, null);

            if (response.isOK()) {
                // 解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                log.info("七牛云上传成功，文件key: {}", putRet.key);

                // 返回文件访问URL
                String fileUrl = "http://" + domain + "/" + key;
                return fileUrl;
            } else {
                log.error("七牛云上传失败，响应: {}", response.bodyString());
                throw new RuntimeException("七牛云上传失败: " + response.bodyString());
            }
        } catch (QiniuException e) {
            log.error("七牛云上传异常", e);
            throw new RuntimeException("七牛云上传异常: " + e.getMessage());
        } catch (IOException e) {
            log.error("文件读取异常", e);
            throw new RuntimeException("文件读取异常: " + e.getMessage());
        }
    }

    /**
     * 根据区域代码获取Region对象
     */
    private Region getRegion(String regionCode) {
        switch (regionCode) {
            case "z0":
                return Region.region0(); // 华东-浙江
            case "z1":
                return Region.region1(); // 华北-河北
            case "z2":
                return Region.region2(); // 华南-广东
            case "na0":
                return Region.regionNa0(); // 北美-洛杉矶
            case "as0":
                return Region.regionAs0(); // 亚太-新加坡
            default:
                return Region.region0(); // 默认华东-浙江
        }
    }

    /**
     * 删除文件
     */
    public void deleteFile(String key) {
        try {
            String accessKey = systemSettingService.getSettingValue("QINIU_ACCESS_KEY");
            String secretKey = systemSettingService.getSettingValue("QINIU_SECRET_KEY");
            String bucketName = systemSettingService.getSettingValue("QINIU_BUCKET_NAME");

            if (accessKey == null || secretKey == null || bucketName == null) {
                throw new RuntimeException("七牛云配置不完整");
            }

            Auth auth = Auth.create(accessKey, secretKey);
            com.qiniu.storage.BucketManager bucketManager = new com.qiniu.storage.BucketManager(auth, new Configuration());

            Response response = bucketManager.delete(bucketName, key);
            if (!response.isOK()) {
                log.error("七牛云删除文件失败，响应: {}", response.bodyString());
            }
        } catch (QiniuException e) {
            log.error("七牛云删除文件异常", e);
        }
    }
}
