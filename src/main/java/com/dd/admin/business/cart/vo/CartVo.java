package com.dd.admin.business.cart.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 购物车返回VO
 *
 * @author 727869402@qq.com
 * @since 2025-09-11
 */
@Data
@ApiModel("购物车返回VO")
public class CartVo {

    @ApiModelProperty("购物车ID")
    private String cartId;

    @ApiModelProperty("用户ID")
    private String authorId;

    @ApiModelProperty("商品ID")
    private String productId;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("商品图片")
    private String productImage;

    @ApiModelProperty("数量")
    private Integer quantity;

    @ApiModelProperty("单价")
    private BigDecimal price;

    @ApiModelProperty("商品价格")
    private BigDecimal productPrice;

    @ApiModelProperty("总价")
    private BigDecimal totalPrice;

    @ApiModelProperty("是否选中")
    private Integer selected;

    @ApiModelProperty("店铺ID")
    private String shopId;

    @ApiModelProperty("店铺名称")
    private String shopName;

    @ApiModelProperty("运费")
    private BigDecimal shippingFee;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("备注")
    private String remark;
}