package com.dd.admin.business.upReplys.entity;

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
 * 笔记点赞表
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("business_up_replys")
@ApiModel(value="UpReplys对象", description="笔记点赞表")
public class UpReplys implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "REPLY_UP_ID", type = IdType.ASSIGN_UUID)
    private String replyUpId;

    @TableField("REPLY_ID")
    private String replyId;

    @TableField("REPLY_CONTENT")
    private String replyContent;

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


}
