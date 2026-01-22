package com.dd.admin.business.cart.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 购物车查询DTO
 *
 * @author 727869402@qq.com
 * @since 2025-09-11
 */
@Data
@ApiModel("购物车查询DTO")
public class CartDto {

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

    @ApiModelProperty("是否选中")
    private Integer selected;

    @ApiModelProperty("创建时间开始")
    private String createTimeStart;

    @ApiModelProperty("创建时间结束")
    private String createTimeEnd;

    @ApiModelProperty("创建人")
    private String createName;

    @ApiModelProperty("品牌ID")
    private String brandId;

    @ApiModelProperty("品牌名")
    private String brandName;

    @ApiModelProperty("门店ID")
    private String shopId;

    @ApiModelProperty("门店名")
    private String shopName;

    @ApiModelProperty("部门ID")
    private String deptId;

    @ApiModelProperty("部门名")
    private String deptName;

    @ApiModelProperty("备注")
    private String remark;
}