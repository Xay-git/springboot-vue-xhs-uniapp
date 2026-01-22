package com.dd.admin.business.chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.business.chat.domain.AuthorChat;
import com.dd.admin.common.model.PageFactory;
import com.dd.admin.business.chat.entity.Chat;
import com.dd.admin.business.chat.mapper.ChatMapper;
import com.dd.admin.business.chat.service.ChatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.dd.admin.business.chat.domain.ChatVo;
import com.dd.admin.business.chat.domain.ChatDto;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-28
 */
@Service
public class ChatServiceImpl extends ServiceImpl<ChatMapper, Chat> implements ChatService {

    @Override
    public IPage<ChatVo> selectChatPage(ChatDto chatDto) {
        Page page = PageFactory.defaultPage();
        return baseMapper.selectChatPage(page,chatDto);
    }

    @Override
    public ChatVo selectChat(String chatId) {
        return baseMapper.selectChat(chatId);
    }

    @Override
    public List<ChatVo> selectChatDetail(ChatDto chatDto) {
        return baseMapper.selectChatDetail(chatDto);
    }

    @Override
    public List<ChatVo> getMessageList(String authorId) {
        return baseMapper.getMessageList(authorId);
    }

    @Override
    //发送者是对方读取者是我
    public void readMessage(String authorId, String loginId) {
        LambdaUpdateWrapper<Chat> queryWrapper = new LambdaUpdateWrapper();
        queryWrapper.eq(Chat::getFromId,authorId);
        queryWrapper.eq(Chat::getToId,loginId);
        queryWrapper.set(Chat::getMessageStatus,1);
        this.update(queryWrapper);
    }

    @Override
    public Integer selectUnReadCount(String authorId) {
        LambdaQueryWrapper<Chat> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Chat::getToId,authorId);
        queryWrapper.eq(Chat::getMessageStatus,0);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public List<AuthorChat> selectAuthorChatList(String authorId) {
        return baseMapper.selectAuthorChatList(authorId);
    }
}
