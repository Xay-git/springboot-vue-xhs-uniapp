package com.dd.admin.system.setting.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dd.admin.common.model.PageFactory;
import com.dd.admin.system.setting.domain.SystemSettingDto;
import com.dd.admin.system.setting.domain.SystemSettingVo;
import com.dd.admin.system.setting.entity.SystemSetting;
import com.dd.admin.system.setting.mapper.SystemSettingMapper;
import com.dd.admin.system.setting.service.SystemSettingService;
import com.dd.admin.common.service.MinioService;
import com.dd.admin.common.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统设置 服务实现类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-01
 */
@Service
public class SystemSettingServiceImpl extends ServiceImpl<SystemSettingMapper, SystemSetting> implements SystemSettingService {

    @Value("${dd.storage-mode:local}")
    private String defaultStorageMode;

    @Autowired
    private MinioService minioService;
    
    @Autowired
    private RedisUtil redisUtil;
    
    // Redis缓存键前缀
    private static final String CACHE_PREFIX = "system_setting:";
    // 缓存过期时间（秒）
    private static final long CACHE_EXPIRE_TIME = 300; // 5分钟

    @Override
    public IPage<SystemSettingVo> selectSystemSettingPage(SystemSettingDto systemSettingDto) {
        Page page = PageFactory.defaultPage();
        return baseMapper.selectSystemSettingPage(page, systemSettingDto);
    }

    @Override
    public List<SystemSettingVo> selectSystemSettingList(SystemSettingDto systemSettingDto) {
        return baseMapper.selectSystemSettingList(systemSettingDto);
    }

    @Override
    public String getSettingValue(String settingKey) {
        return getSettingValue(settingKey, null);
    }

    @Override
    public String getSettingValue(String settingKey, String defaultValue) {
        // 先从Redis缓存获取
        String cacheKey = CACHE_PREFIX + settingKey;
        try {
            Object cachedValue = redisUtil.get(cacheKey);
            if (cachedValue != null) {
                return cachedValue.toString();
            }
        } catch (Exception e) {
            // Redis异常时继续从数据库获取
        }
        
        // 缓存未命中，从数据库获取
        QueryWrapper<SystemSetting> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SETTING_KEY", settingKey)
                   .eq("IS_ENABLED", true)
                   .last("LIMIT 1");
        
        SystemSetting setting = getOne(queryWrapper);
        String value = defaultValue;
        if (setting != null && setting.getSettingValue() != null) {
            value = setting.getSettingValue();
        }
        
        // 将结果缓存到Redis
        try {
            if (value != null) {
                redisUtil.set(cacheKey, value, CACHE_EXPIRE_TIME);
            }
        } catch (Exception e) {
            // Redis异常不影响业务逻辑
        }
        
        return value;
    }

    @Override
    public boolean setSetting(String settingKey, String settingValue, String settingDesc) {
        QueryWrapper<SystemSetting> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SETTING_KEY", settingKey);
        
        SystemSetting existingSetting = getOne(queryWrapper);
        boolean result;
        if (existingSetting != null) {
            // 更新现有设置
            existingSetting.setSettingValue(settingValue);
            if (settingDesc != null) {
                existingSetting.setSettingDesc(settingDesc);
            }
            existingSetting.setIsEnabled(true);
            result = updateById(existingSetting);
        } else {
            // 创建新设置
            SystemSetting newSetting = new SystemSetting();
            newSetting.setSettingKey(settingKey);
            newSetting.setSettingValue(settingValue);
            newSetting.setSettingDesc(settingDesc);
            newSetting.setSettingType("SYSTEM");
            newSetting.setIsEnabled(true);
            result = save(newSetting);
        }
        
        // 更新成功后清除对应的Redis缓存
        if (result) {
            try {
                String cacheKey = CACHE_PREFIX + settingKey;
                redisUtil.del(cacheKey);
            } catch (Exception e) {
                // Redis异常不影响业务逻辑
            }
        }
        
        return result;
    }

    @Override
    public String getStorageMode() {
        String storageMode = getSettingValue("STORAGE_MODE");
        return storageMode != null ? storageMode : defaultStorageMode;
    }

    @Override
    public boolean setStorageMode(String storageMode) {
        return setSetting("STORAGE_MODE", storageMode, "文件存储模式：local(本地存储) 或 minio(MinIO对象存储)");
    }

    @Override
    public String getSmsMode() {
        String smsMode = getSettingValue("SMS_MODE");
        return smsMode != null ? smsMode : "test";
    }

    @Override
    public boolean setSmsMode(String smsMode) {
        return setSetting("SMS_MODE", smsMode, "短信发送模式：test(测试模式) 或 real(真实发送)");
    }

    @Override
    public boolean checkMinioConnectivity() {
        return checkMinioConnectivity(false);
    }
    
    /**
     * 检查MinIO连通性
     * @param forceCheck 是否强制检查，true时忽略缓存状态直接检查连接
     * @return 连接状态
     */
    public boolean checkMinioConnectivity(boolean forceCheck) {
        try {
            // 如果强制检查，直接进行连接测试
            if (forceCheck) {
                // 如果是强制检查，使用最新配置重新连接
                return minioService.checkConnection(forceCheck);
            }
            
            // 先检查缓存的连接状态
            if (minioService.getConnectionStatus()) {
                return true; // 如果缓存状态为已连接，直接返回true
            }
            // 缓存状态为未连接，重新检查连接并更新状态
            return minioService.checkConnection();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean validateAndSetStorageMode(String storageMode) {
        // 如果要切换到MinIO，先检查连通性
        if ("minio".equalsIgnoreCase(storageMode)) {
            if (!checkMinioConnectivity()) {
                return false; // MinIO不可连通，不允许切换
            }
            // MinIO连通性检查通过，手动设置连接状态为true
            minioService.setConnectionStatus(true);
        } else if ("qiniu".equalsIgnoreCase(storageMode)) {
            // 切换到七牛云存储模式，检查配置完整性
            Map<String, String> qiniuConfig = getQiniuConfig();
            // 如果七牛云配置为空或不完整，抛出异常提示用户配置
            if (qiniuConfig == null || qiniuConfig.isEmpty() || 
                isQiniuConfigIncomplete(qiniuConfig)) {
                throw new RuntimeException("七牛云配置不完整，请先在系统设置中配置正确的七牛云参数（AccessKey、SecretKey、存储桶名称、访问域名）后再切换存储模式");
            }
            // 手动设置MinIO连接状态为false
            minioService.setConnectionStatus(false);
        } else {
            // 切换到本地存储模式，手动设置MinIO连接状态为false
            minioService.setConnectionStatus(false);
        }
        
        // 连通性检查通过或者是本地存储模式，执行设置
        return setStorageMode(storageMode);
    }

    @Override
    public Map<String, String> getMinioConfig() {
        Map<String, String> config = new HashMap<>();
        config.put("endpoint", getSettingValue("MINIO_ENDPOINT", ""));
        config.put("accessKey", getSettingValue("MINIO_ACCESS_KEY", ""));
        config.put("secretKey", getSettingValue("MINIO_SECRET_KEY", ""));
        config.put("bucketName", getSettingValue("MINIO_BUCKET_NAME", ""));
        return config;
    }

    @Override
    public void setMinioConfig(Map<String, String> config) {
        // 先保存配置
        if (config.containsKey("endpoint")) {
            setSetting("MINIO_ENDPOINT", config.get("endpoint"), "MinIO服务地址");
        }
        if (config.containsKey("accessKey")) {
            setSetting("MINIO_ACCESS_KEY", config.get("accessKey"), "MinIO访问密钥");
        }
        if (config.containsKey("secretKey")) {
            setSetting("MINIO_SECRET_KEY", config.get("secretKey"), "MinIO秘密密钥");
        }
        if (config.containsKey("bucketName")) {
            setSetting("MINIO_BUCKET_NAME", config.get("bucketName"), "MinIO存储桶名称");
            // 清除MinIO存储桶名称缓存
            minioService.clearBucketNameCache();
        }
        
        // 配置保存后强制重新测试连通性（忽略缓存状态）
        try {
            boolean isConnected = checkMinioConnectivity(true);
            if (!isConnected) {
                throw new RuntimeException("MinIO连接测试失败，请检查配置参数");
            }
        } catch (Exception e) {
            throw new RuntimeException("MinIO连接测试失败：" + e.getMessage());
        }
    }

    @Override
    public Map<String, String> getQiniuConfig() {
        Map<String, String> config = new HashMap<>();
        config.put("accessKey", getSettingValue("QINIU_ACCESS_KEY", ""));
        config.put("secretKey", getSettingValue("QINIU_SECRET_KEY", ""));
        config.put("bucketName", getSettingValue("QINIU_BUCKET_NAME", ""));
        config.put("domain", getSettingValue("QINIU_DOMAIN", ""));
        config.put("region", getSettingValue("QINIU_REGION", "z0"));
        return config;
    }

    @Override
    public void setQiniuConfig(Map<String, String> config) {
        if (config.containsKey("accessKey")) {
            setSetting("QINIU_ACCESS_KEY", config.get("accessKey"), "七牛云访问密钥");
        }
        if (config.containsKey("secretKey")) {
            setSetting("QINIU_SECRET_KEY", config.get("secretKey"), "七牛云秘密密钥");
        }
        if (config.containsKey("bucketName")) {
            setSetting("QINIU_BUCKET_NAME", config.get("bucketName"), "七牛云存储桶名称");
        }
        if (config.containsKey("domain")) {
            setSetting("QINIU_DOMAIN", config.get("domain"), "七牛云访问域名");
        }
        if (config.containsKey("region")) {
            setSetting("QINIU_REGION", config.get("region"), "七牛云存储区域");
        }
    }

    /**
     * 检查七牛云配置是否不完整
     */
    private boolean isQiniuConfigIncomplete(Map<String, String> qiniuConfig) {
        String accessKey = qiniuConfig.get("accessKey");
        String secretKey = qiniuConfig.get("secretKey");
        String bucketName = qiniuConfig.get("bucketName");
        String domain = qiniuConfig.get("domain");
        
        // 检查是否为空、空字符串或占位符值
        return accessKey == null || accessKey.trim().isEmpty() || "your_qiniu_access_key".equals(accessKey) ||
               secretKey == null || secretKey.trim().isEmpty() || "your_qiniu_secret_key".equals(secretKey) ||
               bucketName == null || bucketName.trim().isEmpty() || "your_bucket_name".equals(bucketName) ||
               domain == null || domain.trim().isEmpty() || "your_domain.com".equals(domain);
    }

}