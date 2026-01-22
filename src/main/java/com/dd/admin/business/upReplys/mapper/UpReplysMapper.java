package com.dd.admin.business.upReplys.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.business.upReplys.entity.UpReplys;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.admin.business.upReplys.domain.UpReplysVo;
import com.dd.admin.business.upReplys.domain.UpReplysDto;

import java.util.List;

/**
 * <p>
 * 笔记点赞表 Mapper 接口
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-25
 */
@Mapper
public interface UpReplysMapper extends BaseMapper<UpReplys> {

    IPage<UpReplysVo> selectUpReplysPage(Page<UpReplysVo> page, @Param("upReplysDto") UpReplysDto upReplysDto);

    List<UpReplysVo> selectUpReplysList(@Param("upReplysDto") UpReplysDto upReplysDto);
}
