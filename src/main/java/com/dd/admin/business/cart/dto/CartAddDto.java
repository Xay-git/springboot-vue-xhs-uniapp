package com.dd.admin.business.cart.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 购物车新增DTO
 *
 * @author 727869402@qq.com
 * @since 2025-09-11
 */
@Data
@ApiModel("购物车新增DTO")
public class CartAddDto {

    @ApiModelProperty(value = "用户ID")
    private String authorId;

    @ApiModelProperty(value = "商品ID", required = true)
    @NotBlank(message = "商品ID不能为空")
    private String productId;

    @ApiModelProperty(value = "商品名称", required = true)
    private String productName;

    @ApiModelProperty("商品图片")
    private String productImage;

    @ApiModelProperty(value = "数量", required = true)
    @NotNull(message = "数量不能为空")
    private Integer quantity;

    @ApiModelProperty(value = "单价", required = true)
    private BigDecimal price;

    @ApiModelProperty("是否选中")
    private Integer selected;

    @ApiModelProperty("备注")
    private String remark;
}