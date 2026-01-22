package com.dd.admin.business.balance.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 余额变动记录
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-09-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("business_balance_log")
@ApiModel(value="BalanceLog对象", description="余额变动记录")
public class BalanceLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "日志ID")
    @TableId(value = "LOG_ID", type = IdType.ASSIGN_UUID)
    private String logId;

    @ApiModelProperty(value = "用户ID")
    @TableField("AUTHOR_ID")
    private String authorId;

    @ApiModelProperty(value = "变动金额")
    @TableField("AMOUNT")
    private BigDecimal amount;

    @ApiModelProperty(value = "变动前余额")
    @TableField("BEFORE_BALANCE")
    private BigDecimal beforeBalance;

    @ApiModelProperty(value = "变动后余额")
    @TableField("AFTER_BALANCE")
    private BigDecimal afterBalance;

    @ApiModelProperty(value = "类型 0-充值 1-消费 2-退款 3-管理员增加 4-管理员减少")
    @TableField("TYPE")
    private Integer type;

    @ApiModelProperty(value = "关联ID(订单ID)")
    @TableField("RELATED_ID")
    private String relatedId;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

    @ApiModelProperty(value = "操作人ID")
    @TableField("OPERATOR_ID")
    private String operatorId;

    @ApiModelProperty(value = "操作人姓名")
    @TableField("OPERATOR_NAME")
    private String operatorName;

    @ApiModelProperty(value = "乐观锁字段")
    @TableField("VERSION")
    @Version
    private Long version;

    @ApiModelProperty(value = "0-正常 1-删除")
    @TableField("DELETED")
    @TableLogic
    private Integer deleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "UPDATE_TIME", fill = FieldFill.UPDATE)
    private Date updateTime;
}




