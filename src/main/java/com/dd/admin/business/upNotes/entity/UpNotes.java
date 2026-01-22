package com.dd.admin.business.upNotes.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 点赞笔记列表
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("business_up_notes")
@ApiModel(value="UpNotes对象", description="点赞笔记列表")
public class UpNotes implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "UP_NOTE_ID", type = IdType.ASSIGN_UUID)
    private String upNoteId;

    @TableField("NOTE_ID")
    private String noteId;

    @TableField("NOTE_TITLE")
    private String noteTitle;

    @ApiModelProperty(value = "被关注id")
    @TableField("AUTHOR_ID")
    private String authorId;

    @ApiModelProperty(value = "被关注名字")
    @TableField("AUTHOR_NAME")
    private String authorName;

    @ApiModelProperty(value = "关注者")
    @TableField("FOLLOW_ID")
    private String followId;

    @ApiModelProperty(value = "关注者名字")
    @TableField("FOLLOW_NAME")
    private String followName;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;


    @ApiModelProperty(value = "0发送 1已读")
    @TableField("MESSAGE_STATUS")
    private Integer messageStatus;
}
