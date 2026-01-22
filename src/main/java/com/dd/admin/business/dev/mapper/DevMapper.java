package com.dd.admin.business.dev.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.business.dev.entity.Dev;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.admin.business.dev.domain.DevVo;
import com.dd.admin.business.dev.domain.DevDto;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-02-11
 */
@Mapper
public interface DevMapper extends BaseMapper<Dev> {

    IPage<DevVo> selectDevPage(Page<DevVo> page, @Param("devDto") DevDto devDto);

    List<DevVo> selectDevList(@Param("devDto") DevDto devDto);
}
