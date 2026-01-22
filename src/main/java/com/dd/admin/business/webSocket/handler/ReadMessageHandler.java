package com.dd.admin.business.webSocket.handler;

import com.dd.admin.business.chat.service.ChatService;
import com.dd.admin.business.webSocket.MsgHandlerInterface;
import com.dd.admin.business.webSocket.util.TioUtil;
import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.tio.core.ChannelContext;

import java.util.Map;


@Slf4j
@Service("7")
@ApiModel("读取消息Handler")
public class ReadMessageHandler implements MsgHandlerInterface {
    @Autowired
    ChatService chatService;
    @Override
    public Object handler(Map map, ChannelContext context) {
        String authorId = String.valueOf(map.get("authorId"));
        String loginId = String.valueOf(map.get("loginId"));
        chatService.readMessage(authorId,loginId);
        return null;
    }
}
