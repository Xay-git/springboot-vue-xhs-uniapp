package com.dd.admin.business.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 订单添加DTO
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-01-17
 */
@Data
@ApiModel(value="OrderAddDto对象", description="订单添加DTO")
public class OrderAddDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private String authorId;

    @ApiModelProperty(value = "地址ID")
    @NotBlank(message = "地址ID不能为空")
    private String addressId;

    @ApiModelProperty(value = "订单总金额")
    @NotNull(message = "订单总金额不能为空")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "商品金额")
    private BigDecimal goodsAmount;

    @ApiModelProperty(value = "运费")
    private BigDecimal shippingFee;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "支付方式")
    private String paymentMethod;

    @ApiModelProperty(value = "订单来源 cart-购物车下单 direct-立即购买")
    private String orderSource;

    @ApiModelProperty(value = "订单项列表")
    private List<OrderItemDto> items;

    @Data
    @ApiModel(value="OrderItemDto对象", description="订单项DTO")
    public static class OrderItemDto implements Serializable {

        private static final long serialVersionUID = 1L;

        @ApiModelProperty(value = "商品ID")
        @NotBlank(message = "商品ID不能为空")
        private String productId;

        @ApiModelProperty(value = "商品名称")
        private String productName;

        @ApiModelProperty(value = "商品价格")
        @NotNull(message = "商品价格不能为空")
        private BigDecimal productPrice;

        @ApiModelProperty(value = "商品图片")
        private String productImage;

        @ApiModelProperty(value = "购买数量")
        @NotNull(message = "购买数量不能为空")
        private Integer quantity;

        @ApiModelProperty(value = "小计金额")
        private BigDecimal subtotal;
    }
}