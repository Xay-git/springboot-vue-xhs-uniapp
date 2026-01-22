package com.dd.admin.business.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商品
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-09-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("business_product")
@ApiModel(value="Product对象", description="商品")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品ID")
    @TableId(value = "PRODUCT_ID", type = IdType.ASSIGN_UUID)
    private String productId;

    @ApiModelProperty(value = "商品名称")
    @TableField("PRODUCT_NAME")
    private String productName;

    @ApiModelProperty(value = "商品编号")
    @TableField("PRODUCT_NO")
    private String productNo;

    @ApiModelProperty(value = "分类ID")
    @TableField("CATEGORY_ID")
    private String categoryId;

    @ApiModelProperty(value = "商品价格")
    @TableField("PRODUCT_PRICE")
    private BigDecimal productPrice;

    @ApiModelProperty(value = "原价")
    @TableField("PRODUCT_ORIGINAL_PRICE")
    private BigDecimal productOriginalPrice;

    @ApiModelProperty(value = "运费")
    @TableField("SHIPPING_FEE")
    private BigDecimal shippingFee;

    @ApiModelProperty(value = "商品描述")
    @TableField("PRODUCT_DESC")
    private String productDesc;

    @ApiModelProperty(value = "库存数量")
    @TableField("PRODUCT_STOCK")
    private Integer productStock;

    @ApiModelProperty(value = "商品状态 0-在售 1-下架 2-草稿")
    @TableField("PRODUCT_STATUS")
    private Integer productStatus;

    @ApiModelProperty(value = "首图ID")
    @TableField("FIRST_PICTURE_ID")
    private String firstPictureId;

    @ApiModelProperty(value = "首图URL")
    @TableField("FIRST_PICTURE_URL")
    private String firstPictureUrl;

    @ApiModelProperty(value = "浏览量")
    @TableField("PRODUCT_VIEWS")
    private Long productViews;

    @ApiModelProperty(value = "销量")
    @TableField("SALES_COUNT")
    private Long salesCount;

    @ApiModelProperty(value = "乐观锁字段")
    @TableField("VERSION")
    @Version
    private Long version;

    @ApiModelProperty(value = "0-正常 1-删除")
    @TableField("DELETED")
    @TableLogic
    private Integer deleted;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("UPDATE_TIME")
    private Date updateTime;
    
    @TableField(exist = false)
    private String categoryName;
    
    @TableField(exist = false)
    private List<ProductImage> images;
}






