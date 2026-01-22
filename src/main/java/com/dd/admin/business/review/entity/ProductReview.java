package com.dd.admin.business.review.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商品评价
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-01-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("business_product_review")
@ApiModel(value="ProductReview对象", description="商品评价")
public class ProductReview implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评价ID")
    @TableId(value = "REVIEW_ID", type = IdType.ASSIGN_UUID)
    private String reviewId;

    @ApiModelProperty(value = "订单ID")
    @TableField("ORDER_ID")
    private String orderId;

    @ApiModelProperty(value = "订单项ID")
    @TableField("ITEM_ID")
    private String itemId;

    @ApiModelProperty(value = "商品ID")
    @TableField("PRODUCT_ID")
    private String productId;

    @ApiModelProperty(value = "用户ID")
    @TableField("AUTHOR_ID")
    private String authorId;

    @ApiModelProperty(value = "评价内容")
    @TableField("CONTENT")
    private String content;

    @ApiModelProperty(value = "评分(1-5)")
    @TableField("RATING")
    private Integer rating;

    @ApiModelProperty(value = "评价图片id")
    @TableField("IMG_ID")
    private String imgId;

    @ApiModelProperty(value = "评价图片Url")
    @TableField("IMG_URL")
    private String imgUrl;

    @ApiModelProperty(value = "乐观锁字段")
    @TableField("VERSION")
    @Version
    private Long version;

    @ApiModelProperty(value = "0-正常 1-删除")
    @TableField("DELETED")
    @TableLogic
    private Integer deleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "UPDATE_TIME", fill = FieldFill.UPDATE)
    private Date updateTime;

    // 非数据库字段
    @TableField(exist = false)
    private String productName;

    @TableField(exist = false)
    private String productImage;

    @TableField(exist = false)
    private String authorName;
}