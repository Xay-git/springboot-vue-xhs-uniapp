package com.dd.admin.business.chat.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.business.chat.domain.AuthorChat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.business.chat.entity.Chat;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.admin.business.chat.domain.ChatVo;
import com.dd.admin.business.chat.domain.ChatDto;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-28
 */
@Mapper
public interface ChatMapper extends BaseMapper<Chat> {

    IPage<ChatVo> selectChatPage(Page<ChatVo> page, @Param("chatDto") ChatDto chatDto);
    ChatVo selectChat(@Param("chatId") String chatId);
    List<ChatVo> selectChatDetail(@Param("chatDto") ChatDto chatDto);
    //查询我的聊天记录列表 当我作为收发方都需要考虑
    List<ChatVo> getMessageList(@Param("authorId")String authorId);


    //admin下面是后台使用的接口
    //查询客服聊天列表 当我作为收发方都需要查询最后一条
    List<AuthorChat> selectAuthorChatList(@Param("authorId")String authorId);
}
