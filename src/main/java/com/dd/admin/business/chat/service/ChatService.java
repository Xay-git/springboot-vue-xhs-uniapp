package com.dd.admin.business.chat.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.business.chat.domain.AuthorChat;
import com.dd.admin.business.chat.entity.Chat;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.admin.business.chat.domain.ChatVo;
import com.dd.admin.business.chat.domain.ChatDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-28
 */
public interface ChatService extends IService<Chat> {

    //-分页列表
    IPage<ChatVo> selectChatPage(ChatDto chatDto);
    ChatVo selectChat(String chatId);
    //-列表
    List<ChatVo> selectChatDetail(ChatDto chatDto);
    List<ChatVo> getMessageList(String authorId);

    void readMessage(String authorId,String loginId);
    //未读聊天消息的数量
    Integer selectUnReadCount(String authorId);

    //admin
    List<AuthorChat> selectAuthorChatList(String authorId);

}
