package com.dd.admin.business.reply.entity;

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
 * 回复表
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("business_reply")
@ApiModel(value="Reply对象", description="回复表")
public class Reply implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "回复id")
    @TableId(value = "REPLY_ID", type = IdType.ASSIGN_UUID)
    private String replyId;

    @ApiModelProperty(value = "笔记id")
    @TableField("NOTE_ID")
    private String noteId;

    @ApiModelProperty(value = "笔记标题")
    @TableField("NOTE_TITLE")
    private String noteTitle;

    @ApiModelProperty(value = "作者id")
    @TableField("AUTHOR_ID")
    private String authorId;

    @ApiModelProperty(value = "作者名")
    @TableField("AUTHOR_NAME")
    private String authorName;

    @ApiModelProperty(value = "上级id")
    @TableField("PARENT_ID")
    private String parentId;

    @ApiModelProperty(value = "作者id")
    private String parentAuthorId;

    @ApiModelProperty(value = "作者名")
    private String parentAuthorName;

    @ApiModelProperty(value = "第一层回复的id")
    private String topParentId;


    @ApiModelProperty(value = "回复内容")
    @TableField("REPLAY_CONTENT")
    private String replayContent;

    @ApiModelProperty(value = "回复图片id")
    @TableField("REPLAY_IMG_ID")
    private String replayImgId;

    @ApiModelProperty(value = "回复图片Url")
    @TableField("REPLAY_IMG_URL")
    private String replayImgUrl;

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

    @ApiModelProperty(value = "是首评 0不是 1是")
    @TableField("FIRST_REPLAY")
    private Integer firstReplay;

    @ApiModelProperty(value = "是作者 0不是 1是")
    @TableField("AUTHOR_REPLAY")
    private Integer authorReplay;

    @ApiModelProperty(value = "0正常 1折叠")
    @TableField("REPLAY_STATUS")
    private Integer replayStatus;

    @ApiModelProperty(value = "0文字 1图片")
    @TableField("REPLAY_TYPE")
    private Integer replayType;

    @ApiModelProperty(value = "点赞数")
    @TableField("UP_COUNT")
    private Long upCount;

    @ApiModelProperty(value = "头像地址")
    @TableField("AVATAR_URL")
    private String avatarUrl;


    @ApiModelProperty(value = "0发送 1已读")
    @TableField("MESSAGE_STATUS")
    private Integer messageStatus;
}
