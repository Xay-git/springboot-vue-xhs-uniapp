package com.dd.admin.business.topics.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.common.model.PageFactory;
import com.dd.admin.business.topics.entity.Topics;
import com.dd.admin.business.topics.mapper.TopicsMapper;
import com.dd.admin.business.topics.service.TopicsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.dd.admin.business.topics.domain.TopicsVo;
import com.dd.admin.business.topics.domain.TopicsDto;
import java.util.List;

/**
 * <p>
 * 话题 服务实现类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-04-14
 */
@Service
public class TopicsServiceImpl extends ServiceImpl<TopicsMapper, Topics> implements TopicsService {

    @Override
    public IPage<TopicsVo> selectTopicsPage(TopicsDto topicsDto) {
        Page page = PageFactory.defaultPage();
        return baseMapper.selectTopicsPage(page,topicsDto);
    }

    @Override
    public List<TopicsVo> selectTopicsList(TopicsDto topicsDto) {
        return baseMapper.selectTopicsList(topicsDto);
    }
}
