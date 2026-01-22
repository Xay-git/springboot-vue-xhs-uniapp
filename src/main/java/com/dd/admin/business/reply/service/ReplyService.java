package com.dd.admin.business.reply.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.business.reply.entity.Reply;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.admin.business.reply.domain.ReplyVo;
import com.dd.admin.business.reply.domain.ReplyDto;
import java.util.List;

/**
 * <p>
 * 回复表 服务类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-25
 */
public interface ReplyService extends IService<Reply> {

    //回复表-分页列表
    IPage<ReplyVo> selectReplyPage(ReplyDto replyDto);

    //回复表-列表
    List<ReplyVo> selectReplyList(ReplyDto replyDto);

    //未读聊天消息的数量
    Integer selectUnReadCount(String authorId);
    void readMessage(String authorId);

}
