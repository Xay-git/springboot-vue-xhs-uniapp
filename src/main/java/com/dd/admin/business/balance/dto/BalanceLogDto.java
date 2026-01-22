package com.dd.admin.business.balance.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 余额日志 DTO
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-09-11
 */
@Data
@ApiModel(value = "BalanceLogDto对象", description = "余额日志查询参数")
public class BalanceLogDto {

    @ApiModelProperty(value = "日志ID")
    private String logId;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "作者ID")
    private String authorId;

    @ApiModelProperty(value = "变动类型：1-充值，2-消费，3-退款")
    private Integer changeType;

    @ApiModelProperty(value = "变动金额")
    private BigDecimal changeAmount;

    @ApiModelProperty(value = "变动前余额")
    private BigDecimal beforeBalance;

    @ApiModelProperty(value = "变动后余额")
    private BigDecimal afterBalance;

    @ApiModelProperty(value = "变动原因")
    private String changeReason;

    @ApiModelProperty(value = "关联订单ID")
    private String relatedOrderId;

    @ApiModelProperty(value = "变动类型")
    private Integer type;

    @ApiModelProperty(value = "关联ID")
    private String relatedId;

    @ApiModelProperty(value = "操作员ID")
    private String operatorId;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "当前页")
    private Integer current;

    @ApiModelProperty(value = "每页大小")
    private Integer size;

}
