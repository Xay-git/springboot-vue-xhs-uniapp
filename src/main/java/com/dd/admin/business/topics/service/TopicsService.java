package com.dd.admin.business.topics.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.business.topics.entity.Topics;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.admin.business.topics.domain.TopicsVo;
import com.dd.admin.business.topics.domain.TopicsDto;
import java.util.List;

/**
 * <p>
 * 话题 服务类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-04-14
 */
public interface TopicsService extends IService<Topics> {

    //话题-分页列表
    IPage<TopicsVo> selectTopicsPage(TopicsDto topicsDto);

    //话题-列表
    List<TopicsVo> selectTopicsList(TopicsDto topicsDto);

}
