package com.dd.admin.business.topics.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.business.topics.entity.Topics;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.admin.business.topics.domain.TopicsVo;
import com.dd.admin.business.topics.domain.TopicsDto;

import java.util.List;

/**
 * <p>
 * 话题 Mapper 接口
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-04-14
 */
@Mapper
public interface TopicsMapper extends BaseMapper<Topics> {

    IPage<TopicsVo> selectTopicsPage(Page<TopicsVo> page, @Param("topicsDto") TopicsDto topicsDto);

    List<TopicsVo> selectTopicsList(@Param("topicsDto") TopicsDto topicsDto);
}
