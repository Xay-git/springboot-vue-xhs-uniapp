package com.dd.admin.business.webSocket.handler;

import com.dd.admin.business.chat.service.ChatService;
import com.dd.admin.business.webSocket.MsgHandlerInterface;
import com.dd.admin.business.webSocket.util.TioUtil;
import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tio.core.ChannelContext;

import java.util.Map;

import static com.dd.admin.business.webSocket.WsConst.HANDLER_CHAT_COUNT;


@Slf4j
@Service("8")
@ApiModel("获取未读总数Handler")
public class GetReadCountMessageHandler implements MsgHandlerInterface {
    @Autowired
    ChatService chatService;
    @Override
    public Object handler(Map map, ChannelContext context) {
        String authorId = String.valueOf(map.get("authorId"));
        Integer unReadCount = chatService.selectUnReadCount(authorId);
        TioUtil.sendChatMessageToUser(context.getGroupContext(),authorId,HANDLER_CHAT_COUNT,unReadCount);
        return null;
    }
}
