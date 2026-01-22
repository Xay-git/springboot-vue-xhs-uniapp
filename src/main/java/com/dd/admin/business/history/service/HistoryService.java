package com.dd.admin.business.history.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.business.history.entity.History;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.admin.business.history.domain.HistoryVo;
import com.dd.admin.business.history.domain.HistoryDto;
import java.util.List;

/**
 * <p>
 * 搜索历史表 服务类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-01-16
 */
public interface HistoryService extends IService<History> {

    //搜索历史表-分页列表
    IPage<HistoryVo> selectHistoryPage(HistoryDto historyDto);

    //搜索历史表-列表
    List<HistoryVo> selectHistoryList(HistoryDto historyDto);

    List<HistoryVo> selectHotHistoryList();


}
