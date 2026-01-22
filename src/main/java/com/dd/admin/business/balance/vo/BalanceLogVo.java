package com.dd.admin.business.balance.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 余额变动记录 VO
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-09-11
 */
@Data
@ApiModel(value = "BalanceLogVo对象", description = "余额变动记录")
public class BalanceLogVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "日志ID")
    private String logId;

    @ApiModelProperty(value = "用户ID")
    private String authorId;

    @ApiModelProperty(value = "变动金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "变动前余额")
    private BigDecimal beforeBalance;

    @ApiModelProperty(value = "变动后余额")
    private BigDecimal afterBalance;

    @ApiModelProperty(value = "类型 0-充值 1-消费 2-退款 3-管理员修改")
    private Integer type;

    @ApiModelProperty(value = "关联ID(订单ID)")
    private String relatedId;

    @ApiModelProperty(value = "操作人ID")
    private String operatorId;

    @ApiModelProperty(value = "操作人姓名")
    private String operatorName;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "乐观锁字段")
    private Long version;

    @ApiModelProperty(value = "0-正常 1-删除")
    private Integer deleted;

    @ApiModelProperty(value = "备注")
    private String remark;
}