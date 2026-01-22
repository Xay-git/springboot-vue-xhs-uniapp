package com.dd.admin.business.history.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.business.history.entity.History;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.admin.business.history.domain.HistoryVo;
import com.dd.admin.business.history.domain.HistoryDto;

import java.util.List;

/**
 * <p>
 * 搜索历史表 Mapper 接口
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-01-16
 */
@Mapper
public interface HistoryMapper extends BaseMapper<History> {

    IPage<HistoryVo> selectHistoryPage(Page<HistoryVo> page, @Param("historyDto") HistoryDto historyDto);

    List<HistoryVo> selectHistoryList(@Param("historyDto") HistoryDto historyDto);
    List<HistoryVo> selectHotHistoryList();
}
