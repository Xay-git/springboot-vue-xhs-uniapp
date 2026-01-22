package com.dd.admin.business.notice.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 通知公告表
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("business_notice")
@ApiModel(value="Notice对象", description="通知公告表")
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "通知id")
    @TableId(value = "NOTICE_ID", type = IdType.ASSIGN_UUID)
    private String noticeId;

    @ApiModelProperty(value = "通知标题")
    @TableField("NOTICE_TITLE")
    private String noticeTitle;

    @ApiModelProperty(value = "通知内容")
    @TableField("NOTICE_CONTENT")
    private String noticeContent;

    @ApiModelProperty(value = "跳转url")
    @TableField("REDIRECT_URL")
    private String redirectUrl;

    @ApiModelProperty(value = "乐观锁字段")
    @TableField("VERSION")
    @Version
    private Long version;

    @ApiModelProperty(value = "0正常 1删除")
    @TableField("DELETED")
    @TableLogic
    private Integer deleted;

    @ApiModelProperty(value = "门店id")
    @TableField(value = "SHOP_ID", fill = FieldFill.INSERT)
    private String shopId;

    @ApiModelProperty(value = "门店名")
    @TableField(value = "SHOP_NAME", fill = FieldFill.INSERT)
    private String shopName;

    @ApiModelProperty(value = "创建人")
    @TableField(value = "CREATE_NAME", fill = FieldFill.INSERT)
    private String createName;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "创建人id")
    @TableField(value = "CREATE_ID", fill = FieldFill.INSERT)
    private String createId;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "UPDATE_TIME", fill = FieldFill.UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "修改人")
    @TableField(value = "UPDATE_NAME", fill = FieldFill.UPDATE)
    private String updateName;

    @ApiModelProperty(value = "修改人id")
    @TableField(value = "UPDATE_ID", fill = FieldFill.UPDATE)
    private String updateId;

    @ApiModelProperty(value = "通知类型")
    @TableField("NOTICE_TYPE")
    private Integer noticeType;


}
