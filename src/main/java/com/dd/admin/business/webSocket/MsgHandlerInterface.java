package com.dd.admin.business.webSocket;

import org.tio.core.ChannelContext;

import java.util.Map;

/**
 * 处理消息的抽象接口
 * @Author: 王旭磊
 */
public interface MsgHandlerInterface {
    Object handler(Map map, ChannelContext context);
}
