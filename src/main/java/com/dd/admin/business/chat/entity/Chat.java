package com.dd.admin.business.chat.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("business_chat")
@ApiModel(value="Chat对象", description="")
public class Chat implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "消息id")
    @TableId(value = "CHAT_ID", type = IdType.ASSIGN_UUID)
    private String chatId;

    @ApiModelProperty(value = "消息发送者")
    @TableField("FROM_ID")
    private String fromId;

    @ApiModelProperty(value = "消息发送者")
    @TableField("FROM_NAME")
    private String fromName;

    @ApiModelProperty(value = "接收者")
    @TableField("TO_ID")
    private String toId;

    @ApiModelProperty(value = "接收者")
    @TableField("TO_NAME")
    private String toName;

    @ApiModelProperty(value = "0文字 1图片 2语音 3视频")
    @TableField("MESSAGE_TYPE")
    private Integer messageType;

    @ApiModelProperty(value = "0发送 1已读")
    @TableField("MESSAGE_STATUS")
    private Integer messageStatus;

    @ApiModelProperty(value = "内容")
    @TableField("CONTENT")
    private String content;

    @ApiModelProperty(value = "资源地址")
    @TableField("RESOURCE_URL")
    private String resourceUrl;

    @ApiModelProperty(value = "0正常 1删除")
    @TableField("DELETED")
    @TableLogic
    private Integer deleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "ip地址")
    @TableField("IP_ADDRESS")
    private String ipAddress;

    @ApiModelProperty(value = "真实ip地址")
    @TableField("IP_REAL_ADDRESS")
    private String ipRealAddress;


}
