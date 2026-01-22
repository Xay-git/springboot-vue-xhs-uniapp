package com.dd.admin.business.reply.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.business.starNotes.entity.StarNotes;
import com.dd.admin.business.upNotes.entity.UpNotes;
import com.dd.admin.common.model.PageFactory;
import com.dd.admin.business.reply.entity.Reply;
import com.dd.admin.business.reply.mapper.ReplyMapper;
import com.dd.admin.business.reply.service.ReplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.dd.admin.business.reply.domain.ReplyVo;
import com.dd.admin.business.reply.domain.ReplyDto;
import java.util.List;

/**
 * <p>
 * 回复表 服务实现类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-25
 */
@Service
public class ReplyServiceImpl extends ServiceImpl<ReplyMapper, Reply> implements ReplyService {

    @Override
    public IPage<ReplyVo> selectReplyPage(ReplyDto replyDto) {
        Page page = PageFactory.defaultPage();
        return baseMapper.selectReplyPage(page,replyDto);
    }

    @Override
    public List<ReplyVo> selectReplyList(ReplyDto replyDto) {
        return baseMapper.selectReplyList(replyDto);
    }

    @Override
    public Integer selectUnReadCount(String authorId) {
        LambdaQueryWrapper<Reply> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Reply::getParentAuthorId,authorId);
        queryWrapper.ne(Reply::getAuthorId,authorId);
        queryWrapper.eq(Reply::getMessageStatus,0);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public void readMessage(String authorId) {
        LambdaUpdateWrapper<Reply> queryWrapper = new LambdaUpdateWrapper();
        queryWrapper.eq(Reply::getParentAuthorId,authorId);
        queryWrapper.ne(Reply::getAuthorId,authorId);
        queryWrapper.set(Reply::getMessageStatus,1);
        this.update(queryWrapper);
    }

}
