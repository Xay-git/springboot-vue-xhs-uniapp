package com.dd.admin.business.chat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageBean {
    // 发送方用户信息
    private FromUser fromUser;
    // 消息处理类型，这里对应数字6，具体含义需根据业务确定
    private int handlerType;
    // 接收方联系人ID，这里是一个字符串形式的ID，具体格式由业务定义
    private String toContactId;
    // 消息的唯一标识ID，UUID格式，具体使用方式依业务而定
    private String id;
    // 消息类型，这里为text表示文本消息，可能还有其他类型如image、audio等
    private String type;
    // 消息内容，此处为文本内容“111”，根据不同消息类型会有不同格式
    private String content;
    // 消息状态，这里是going，具体状态值及含义需结合业务场景明确
    private String status;
    // 消息发送时间，这里是一个时间戳形式（可能是毫秒级时间戳，需根据业务确认）
    private long sendTime;

    // 内部类，用于表示发送方用户信息
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FromUser {
        // 显示名称，例如用户的昵称等
        private String displayName;
        // 用户的唯一标识ID
        private String id;
        // 用户头像的URL或者其他相关标识，这里为空字符串，具体使用方式由业务决定
        private String avatar;
    }
}
