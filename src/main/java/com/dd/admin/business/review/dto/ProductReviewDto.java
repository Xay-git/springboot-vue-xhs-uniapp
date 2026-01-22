package com.dd.admin.business.review.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 商品评价DTO
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-01-15
 */
@Data
@ApiModel(value="ProductReviewDto对象", description="商品评价DTO")
public class ProductReviewDto {

    @ApiModelProperty(value = "评价ID")
    private String reviewId;

    @ApiModelProperty(value = "订单ID")
    private String orderId;

    @ApiModelProperty(value = "订单项ID")
    private String itemId;

    @ApiModelProperty(value = "商品ID")
    private String productId;

    @ApiModelProperty(value = "商品名称")
    private String productName;

    @ApiModelProperty(value = "用户ID")
    private String authorId;

    @ApiModelProperty(value = "用户名称")
    private String authorName;

    @ApiModelProperty(value = "评价内容")
    private String content;

    @ApiModelProperty(value = "评分(1-5)")
    @Min(value = 1, message = "评分不能小于1")
    @Max(value = 5, message = "评分不能大于5")
    private Integer rating;

    @ApiModelProperty(value = "回复图片id")
    private String imgId;

    @ApiModelProperty(value = "回复图片Url")
    private String imgUrl;

    // 分页参数
    @ApiModelProperty(value = "当前页码")
    private Long current = 1L;

    @ApiModelProperty(value = "每页大小")
    private Long size = 10L;
}