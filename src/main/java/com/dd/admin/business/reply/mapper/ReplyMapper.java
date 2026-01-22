package com.dd.admin.business.reply.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.business.reply.entity.Reply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.admin.business.reply.domain.ReplyVo;
import com.dd.admin.business.reply.domain.ReplyDto;

import java.util.List;

/**
 * <p>
 * 回复表 Mapper 接口
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-25
 */
@Mapper
public interface ReplyMapper extends BaseMapper<Reply> {

    IPage<ReplyVo> selectReplyPage(Page<ReplyVo> page, @Param("replyDto") ReplyDto replyDto);

    List<ReplyVo> selectReplyList(@Param("replyDto") ReplyDto replyDto);

}
