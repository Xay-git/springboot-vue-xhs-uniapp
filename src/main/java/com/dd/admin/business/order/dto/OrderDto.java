package com.dd.admin.business.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 订单查询DTO
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-01-17
 */
@Data
@ApiModel(value="OrderDto对象", description="订单查询DTO")
public class OrderDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单ID")
    private String orderId;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "用户ID")
    private String authorId;

    @ApiModelProperty(value = "地址ID")
    private String addressId;

    @ApiModelProperty(value = "订单总金额")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "订单状态 0-待付款 1-已付款 2-已发货 3-已完成 4-已取消")
    private Integer orderStatus;

    @ApiModelProperty(value = "支付时间开始")
    private String payTimeStart;

    @ApiModelProperty(value = "支付时间结束")
    private String payTimeEnd;

    @ApiModelProperty(value = "创建时间开始")
    private String createTimeStart;

    @ApiModelProperty(value = "创建时间结束")
    private String createTimeEnd;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "页码")
    private Integer page = 1;

    @ApiModelProperty(value = "每页条数")
    private Integer limit = 10;
}