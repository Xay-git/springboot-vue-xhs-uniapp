package com.dd.admin.business.history.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.common.model.PageFactory;
import com.dd.admin.business.history.entity.History;
import com.dd.admin.business.history.mapper.HistoryMapper;
import com.dd.admin.business.history.service.HistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.dd.admin.business.history.domain.HistoryVo;
import com.dd.admin.business.history.domain.HistoryDto;
import java.util.List;

/**
 * <p>
 * 搜索历史表 服务实现类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-01-16
 */
@Service
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History> implements HistoryService {

    @Override
    public IPage<HistoryVo> selectHistoryPage(HistoryDto historyDto) {
        Page page = PageFactory.defaultPage();
        return baseMapper.selectHistoryPage(page,historyDto);
    }

    @Override
    public List<HistoryVo> selectHistoryList(HistoryDto historyDto) {
        return baseMapper.selectHistoryList(historyDto);
    }

    @Override
    public List<HistoryVo> selectHotHistoryList() {
        return baseMapper.selectHotHistoryList();
    }
}
