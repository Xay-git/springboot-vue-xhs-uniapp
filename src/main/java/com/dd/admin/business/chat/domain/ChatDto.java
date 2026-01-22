package com.dd.admin.business.chat.domain;

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
import javax.validation.constraints.NotBlank;
import com.dd.admin.common.model.UpdateGroup;


/**
 * <p>
 * 返回对象
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-28
 */
@Data
@ApiModel(value="接收对象")
public class ChatDto {


    @ApiModelProperty(value = "消息id")
    @NotBlank(message = "id不能为空",groups = UpdateGroup.class)
    private String chatId;

    @ApiModelProperty(value = "消息发送者")
    private String fromId;

    @ApiModelProperty(value = "消息发送者")
    private String fromName;

    @ApiModelProperty(value = "接收者")
    private String toId;

    @ApiModelProperty(value = "接收者")
    private String toName;

    @ApiModelProperty(value = "0文字 1图片 2语音 3视频")
    private Integer messageType;

    @ApiModelProperty(value = "0发送 1已读")
    private Integer messageStatus;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "资源地址")
    private String resourceUrl;

    @ApiModelProperty(value = "0正常 1删除")
    private Integer deleted;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "ip地址")
    private String ipAddress;

    @ApiModelProperty(value = "真实ip地址")
    private String ipRealAddress;


}
