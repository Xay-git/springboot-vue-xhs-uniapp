package com.dd.admin.business.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 订单项
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-09-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("business_order_item")
@ApiModel(value="OrderItem对象", description="订单项")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单项ID")
    @TableId(value = "ITEM_ID", type = IdType.ASSIGN_UUID)
    private String itemId;

    @ApiModelProperty(value = "订单ID")
    @TableField("ORDER_ID")
    private String orderId;

    @ApiModelProperty(value = "商品ID")
    @TableField("PRODUCT_ID")
    private String productId;

    @ApiModelProperty(value = "商品名称")
    @TableField("PRODUCT_NAME")
    private String productName;

    @ApiModelProperty(value = "商品价格")
    @TableField("PRODUCT_PRICE")
    private BigDecimal productPrice;

    @ApiModelProperty(value = "商品图片")
    @TableField("PRODUCT_IMAGE")
    private String productImage;

    @ApiModelProperty(value = "数量")
    @TableField("QUANTITY")
    private Integer quantity;

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
}

