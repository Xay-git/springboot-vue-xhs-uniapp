package com.dd.admin.business.chat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorChat {
    // 消息的唯一标识id
    @ApiModelProperty(value = "用户id")
    private String id;

    // 显示名称，例如聊天对象的昵称等
    @ApiModelProperty(value = "显示名称")
    private String displayName;

    // 头像的网络地址，用于展示聊天对象的头像图片
    @ApiModelProperty(value = "头像")
    private String avatar;

    // 索引字段，可能用于排序、分组等功能，具体含义依业务而定
    @ApiModelProperty(value = "索引")
    private String index;

    // 未读消息的数量
    @ApiModelProperty(value = "未读消息数量")
    private Integer unread;

    // 最近一条消息的内容，经过相应的渲染处理（如表情替换等）
    @ApiModelProperty(value = "最近一条消息内容")
    private String lastContent;

    // 最近一条消息的发送时间，通常是时间戳形式（单位可能是毫秒）
    @ApiModelProperty(value = "最近一条消息发送时间")
    private Long lastSendTime;

    @JsonIgnore
    private Date CreateTime;
}
