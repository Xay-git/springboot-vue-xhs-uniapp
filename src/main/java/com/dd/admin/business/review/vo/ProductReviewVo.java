package com.dd.admin.business.review.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 商品评价VO
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-01-15
 */
@Data
@ApiModel(value="ProductReviewVo对象", description="商品评价VO")
public class ProductReviewVo {

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

    @ApiModelProperty(value = "商品图片")
    private String productImage;

    @ApiModelProperty(value = "商品价格")
    private java.math.BigDecimal productPrice;

    @ApiModelProperty(value = "用户ID")
    private String authorId;

    @ApiModelProperty(value = "用户名称")
    private String authorName;

    @ApiModelProperty(value = "用户头像")
    private String avatarUrl;

    @ApiModelProperty(value = "评价内容")
    private String content;

    @ApiModelProperty(value = "评分(1-5)")
    private Integer rating;

    @ApiModelProperty(value = "评价图片ID")
    private String imgId;

    @ApiModelProperty(value = "评价图片URL")
    private String imgUrl;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
}