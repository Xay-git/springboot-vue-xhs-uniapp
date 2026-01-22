package com.dd.admin.system.setting.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.common.exception.ApiException;
import com.dd.admin.common.model.result.ResultBean;
import com.dd.admin.system.setting.domain.SystemSettingDto;
import com.dd.admin.system.setting.domain.SystemSettingVo;
import com.dd.admin.system.setting.entity.SystemSetting;
import com.dd.admin.system.setting.service.SystemSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统设置 前端控制器
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-01
 */
@RestController
@RequestMapping("/system/setting")
@Api(tags = "系统设置管理")
public class SystemSettingController {

    @Autowired
    private SystemSettingService systemSettingService;

    /**
     * 系统设置-分页列表
     */
    @PostMapping("/page")
    @ApiOperation(value = "系统设置-分页列表")
    public ResultBean<IPage<SystemSettingVo>> page(@RequestBody SystemSettingDto systemSettingDto) {
        IPage<SystemSettingVo> page = systemSettingService.selectSystemSettingPage(systemSettingDto);
        return ResultBean.success(page);
    }

    /**
     * 系统设置-列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "系统设置-列表")
    public ResultBean<List<SystemSettingVo>> list(@RequestBody SystemSettingDto systemSettingDto) {
        List<SystemSettingVo> list = systemSettingService.selectSystemSettingList(systemSettingDto);
        return ResultBean.success(list);
    }

    /**
     * 系统设置-添加
     */
    @PostMapping("/add")
    @ApiOperation(value = "系统设置-添加")
    public ResultBean add(@RequestBody SystemSetting systemSetting) {
        boolean save = systemSettingService.save(systemSetting);
        if (save) {
            return ResultBean.success();
        } else {
            throw new ApiException("添加失败");
        }
    }

    /**
     * 系统设置-查询
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "系统设置-查询")
    public ResultBean<SystemSetting> get(@PathVariable String id) {
        SystemSetting systemSetting = systemSettingService.getById(id);
        return ResultBean.success(systemSetting);
    }

    /**
     * 系统设置-修改
     */
    @PostMapping("/edit")
    @ApiOperation(value = "系统设置-修改")
    public ResultBean edit(@RequestBody SystemSetting systemSetting) {
        boolean update = systemSettingService.updateById(systemSetting);
        if (update) {
            return ResultBean.success();
        } else {
            throw new ApiException("修改失败");
        }
    }

    /**
     * 系统设置-删除
     */
    @PostMapping("/delete")
    @ApiOperation(value = "系统设置-删除")
    public ResultBean delete(@RequestBody List<String> ids) {
        boolean delete = systemSettingService.removeByIds(ids);
        if (delete) {
            return ResultBean.success();
        } else {
            throw new ApiException("删除失败");
        }
    }

    /**
     * 获取存储模式配置
     */
    @GetMapping("/storage-mode")
    @ApiOperation(value = "获取存储模式配置")
    public ResultBean<String> getStorageMode() {
        String storageMode = systemSettingService.getStorageMode();
        return ResultBean.success(storageMode);
    }

    @PostMapping("/storage-mode")
    @ApiOperation(value = "设置存储模式配置")
    public ResultBean setStorageMode(@RequestParam String storageMode) {
        boolean success = systemSettingService.validateAndSetStorageMode(storageMode);
        if (success) {
            return ResultBean.success();
        } else {
            throw new ApiException("设置失败，MinIO连通性检查未通过");
        }
    }

    @GetMapping("/sms-mode")
    @ApiOperation(value = "获取短信发送模式配置")
    public ResultBean<String> getSmsMode() {
        String smsMode = systemSettingService.getSmsMode();
        return ResultBean.success(smsMode);
    }

    @PostMapping("/sms-mode")
    @ApiOperation(value = "设置短信发送模式配置")
    public ResultBean setSmsMode(@RequestParam String smsMode) {
        boolean success = systemSettingService.setSmsMode(smsMode);
        if (success) {
            return ResultBean.success();
        } else {
            throw new ApiException("设置失败");
        }
    }

    /**
     * 获取MinIO配置
     */
    @GetMapping("/minio-config")
    @ApiOperation(value = "获取MinIO配置")
    public ResultBean getMinioConfig() {
        Map<String, String> config = systemSettingService.getMinioConfig();
        return ResultBean.success(config);
    }

    /**
     * 设置MinIO配置
     */
    @PostMapping("/minio-config")
    @ApiOperation(value = "设置MinIO配置")
    public ResultBean setMinioConfig(@RequestBody Map<String, String> config) {
        systemSettingService.setMinioConfig(config);
        return ResultBean.success();
    }

    /**
     * 获取七牛云配置
     */
    @GetMapping("/qiniu-config")
    @ApiOperation(value = "获取七牛云配置")
    public ResultBean getQiniuConfig() {
        Map<String, String> config = systemSettingService.getQiniuConfig();
        return ResultBean.success(config);
    }

    /**
     * 设置七牛云配置
     */
    @PostMapping("/qiniu-config")
    @ApiOperation(value = "设置七牛云配置")
    public ResultBean setQiniuConfig(@RequestBody Map<String, String> config) {
        systemSettingService.setQiniuConfig(config);
        return ResultBean.success();
    }

}