package com.dd.admin.business.webSocket.handler;

import cn.hutool.core.bean.BeanUtil;
import com.dd.admin.business.chat.domain.ChatVo;
import com.dd.admin.business.chat.domain.MessageBean;
import com.dd.admin.business.chat.entity.Chat;
import com.dd.admin.business.chat.service.ChatService;
import com.dd.admin.business.webSocket.MsgHandlerInterface;
import com.dd.admin.business.webSocket.util.TioUtil;
import com.dd.admin.common.utils.AddressUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.tio.core.ChannelContext;

import java.util.*;

import static com.dd.admin.business.webSocket.WsConst.HANDLER_CHAT;
import static com.dd.admin.business.webSocket.WsConst.HANDLER_SERVE;

@Component
@Slf4j
@Service("5")
public class P2PMessageHandler implements MsgHandlerInterface {
    
    public static P2PMessageHandler handler;
    
    @Autowired
    ChatService chatService;
    
    public static long convertToShanghaiTimeZoneTimestamp(Date createTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(createTime);
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return calendar.getTimeInMillis();
    }
    
    @Override
    public Object handler(Map map, ChannelContext context) {
        Chat chat = BeanUtil.toBean(map, Chat.class);
        chat.setIpAddress(context.getClientNode().getIp());
        chat.setIpRealAddress(AddressUtils.getRealAddress(chat.getIpAddress())); // ip真实地址
        
        // 保存聊天消息到数据库
        chatService.save(chat);
        
        // 获取保存后的聊天消息详情
        ChatVo chatVo = chatService.selectChat(chat.getChatId());
        
        // 如果是发送给客服(ID为8)的消息
        if (chat.getToId().equals("8")) {
            MessageBean messageBean = new MessageBean();
            messageBean.setId(chatVo.getChatId());
            messageBean.setContent(chatVo.getContent());
            messageBean.setSendTime(convertToShanghaiTimeZoneTimestamp(chatVo.getCreateTime()));
            messageBean.setStatus("succeed");
            
            // 根据消息类型设置不同的type
            switch (chatVo.getMessageType()) {
                case 0:
                    messageBean.setType("text");
                    break;
                case 1:
                    messageBean.setType("image");
                    break;
                case 2:
                    messageBean.setType("voice");
                    break;
                case 3:
                    messageBean.setType("video");
                    break;
                default:
                    messageBean.setType("text");
                    break;
            }
            
            messageBean.setToContactId(chatVo.getFromId());
            
            MessageBean.FromUser fromUser = new MessageBean.FromUser();
            fromUser.setAvatar(chatVo.getFromAvatar());
            fromUser.setDisplayName(chatVo.getFromName());
            fromUser.setId(chatVo.getFromId());
            messageBean.setFromUser(fromUser);
            
            System.out.println(messageBean);
            TioUtil.sendChatMessageToUser(context.getGroupContext(), chat.getToId(), HANDLER_SERVE, messageBean);
        } else {
            // 普通用户间的消息
            TioUtil.sendChatMessageToUser(context.getGroupContext(), chat.getToId(), HANDLER_CHAT, chatVo);
        }
        
        // 发送消息给发送方（确认消息已发送）
        TioUtil.sendChatMessageToUser(context.getGroupContext(), chat.getFromId(), HANDLER_CHAT, chatVo);
        
        return null;
    }
}
