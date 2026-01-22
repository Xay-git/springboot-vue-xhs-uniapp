package com.dd.admin.business.reply.domain;

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
import lombok.experimental.Accessors;


/**
 * <p>
 * 回复表返回对象
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-25
 */
@Data
@ApiModel(value="回复表接收对象")
@Accessors(chain = true)
public class ReplyDto {


    @ApiModelProperty(value = "回复id")
    @NotBlank(message = "回复表id不能为空",groups = UpdateGroup.class)
    private String replyId;

    @ApiModelProperty(value = "笔记id")
    private String noteId;

    @ApiModelProperty(value = "笔记标题")
    private String noteTitle;

    @ApiModelProperty(value = "作者id")
    private String authorId;

    @ApiModelProperty(value = "作者名")
    private String authorName;

    @ApiModelProperty(value = "作者id")
    private String parentAuthorId;

    @ApiModelProperty(value = "作者名")
    private String parentAuthorName;

    @ApiModelProperty(value = "第一层回复id")
    private String topParentId;

    @ApiModelProperty(value = "上级id")
    private String parentId;

    @ApiModelProperty(value = "回复内容")
    private String replayContent;

    @ApiModelProperty(value = "回复图片id")
    private String replayImgId;

    @ApiModelProperty(value = "回复图片Url")
    private String replayImgUrl;

    @ApiModelProperty(value = "0正常 1删除")
    private Integer deleted;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "ip地址")
    private String ipAddress;

    @ApiModelProperty(value = "真实ip地址")
    private String ipRealAddress;

    @ApiModelProperty(value = "是首评 0不是 1是")
    private Integer firstReplay;

    @ApiModelProperty(value = "是作者 0不是 1是")
    private Integer authorReplay;

    @ApiModelProperty(value = "0正常 1折叠")
    private Integer replayStatus;

    @ApiModelProperty(value = "0文字 1图片")
    private Integer replayType;

    @ApiModelProperty(value = "点赞数")
    private Long upCount;

    @ApiModelProperty(value = "头像地址")
    private String avatarUrl;

    @ApiModelProperty(value = "作者ID")
    //传入此参数 如果你点赞了会显示相应状态
    private String followId;

    @ApiModelProperty(value = "0发送 1已读")
    @TableField("MESSAGE_STATUS")
    private Integer messageStatus;
}
