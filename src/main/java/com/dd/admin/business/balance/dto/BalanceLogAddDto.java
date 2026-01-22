package com.dd.admin.business.balance.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * <p>
 * 余额日志添加 DTO
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-09-11
 */
@Data
@ApiModel(value = "BalanceLogAddDto对象", description = "余额日志添加参数")
public class BalanceLogAddDto {

    @ApiModelProperty(value = "用户ID", required = true)
    @NotBlank(message = "用户ID不能为空")
    private String userId;

    @ApiModelProperty(value = "变动类型：1-充值，2-消费，3-退款", required = true)
    @NotNull(message = "变动类型不能为空")
    private Integer changeType;

    @ApiModelProperty(value = "变动金额", required = true)
    @NotNull(message = "变动金额不能为空")
    private BigDecimal changeAmount;

    @ApiModelProperty(value = "变动原因")
    private String changeReason;

    @ApiModelProperty(value = "关联订单ID")
    private String relatedOrderId;

}