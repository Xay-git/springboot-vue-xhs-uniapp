package com.dd.admin.system.setting.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.admin.system.setting.domain.SystemSettingDto;
import com.dd.admin.system.setting.domain.SystemSettingVo;
import com.dd.admin.system.setting.entity.SystemSetting;

import java.util.List;

/**
 * <p>
 * 系统设置 服务类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-01
 */
public interface SystemSettingService extends IService<SystemSetting> {

    //系统设置-分页列表
    IPage<SystemSettingVo> selectSystemSettingPage(SystemSettingDto systemSettingDto);

    //系统设置-列表
    List<SystemSettingVo> selectSystemSettingList(SystemSettingDto systemSettingDto);

    //根据设置键获取设置值
    String getSettingValue(String settingKey);

    //根据设置键获取设置值，如果不存在则返回默认值
    String getSettingValue(String settingKey, String defaultValue);

    //设置系统配置
    boolean setSetting(String settingKey, String settingValue, String settingDesc);

    //获取存储模式配置
    String getStorageMode();

    //设置存储模式配置
    boolean setStorageMode(String storageMode);

    //检查MinIO连通性
    boolean checkMinioConnectivity();

    //验证并设置存储模式（包含连通性检查）
    boolean validateAndSetStorageMode(String storageMode);

    //获取MinIO配置
    java.util.Map<String, String> getMinioConfig();

    //设置MinIO配置
    void setMinioConfig(java.util.Map<String, String> config);

    //获取七牛云配置
    java.util.Map<String, String> getQiniuConfig();

    //设置七牛云配置
    void setQiniuConfig(java.util.Map<String, String> config);

    //获取短信发送模式
    String getSmsMode();

    //设置短信发送模式
    boolean setSmsMode(String smsMode);

}