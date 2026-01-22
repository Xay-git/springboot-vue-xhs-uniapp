package com.dd.admin.business.receive.entity;

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
 * 接收消息表
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("business_receive")
@ApiModel(value="Receive对象", description="接收消息表")
public class Receive implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "接收id")
    @TableId(value = "RECEIVE_ID", type = IdType.ASSIGN_UUID)
    private String receiveId;

    @ApiModelProperty(value = "博主id")
    @TableField("AUTHOR_ID")
    private String authorId;

    @ApiModelProperty(value = "博主名")
    @TableField("AUTHOR_NAME")
    private String authorName;

    @ApiModelProperty(value = "通知id")
    @TableField("NOTICE_ID")
    private String noticeId;

    @ApiModelProperty(value = "通知标题")
    @TableField("NOTICE_TITLE")
    private String noticeTitle;

    @ApiModelProperty(value = "0正常 1删除")
    @TableField("DELETED")
    @TableLogic
    private Integer deleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "UPDATE_TIME", fill = FieldFill.UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "0 发送 1已读")
    @TableField("RECEIVE_STATUS")
    private Integer receiveStatus;


}
