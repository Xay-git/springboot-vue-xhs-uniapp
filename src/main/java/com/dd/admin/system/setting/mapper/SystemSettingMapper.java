package com.dd.admin.system.setting.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.system.setting.domain.SystemSettingDto;
import com.dd.admin.system.setting.domain.SystemSettingVo;
import com.dd.admin.system.setting.entity.SystemSetting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统设置 Mapper 接口
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-01
 */
@Mapper
public interface SystemSettingMapper extends BaseMapper<SystemSetting> {

    //系统设置-分页列表
    IPage<SystemSettingVo> selectSystemSettingPage(Page page, @Param("systemSettingDto") SystemSettingDto systemSettingDto);

    //系统设置-列表
    List<SystemSettingVo> selectSystemSettingList(@Param("systemSettingDto") SystemSettingDto systemSettingDto);

}