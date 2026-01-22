package com.dd.admin.business.view.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.business.view.entity.View;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.admin.business.view.domain.ViewVo;
import com.dd.admin.business.view.domain.ViewDto;

import java.util.List;

/**
 * <p>
 * 查看 Mapper 接口
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-01-15
 */
@Mapper
public interface ViewMapper extends BaseMapper<View> {

    IPage<ViewVo> selectViewPage(Page<ViewVo> page, @Param("viewDto") ViewDto viewDto);

    List<ViewVo> selectViewList(@Param("viewDto") ViewDto viewDto);
}
