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
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 订单
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-09-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("business_order")
@ApiModel(value="Order对象", description="订单")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单ID")
    @TableId(value = "ORDER_ID", type = IdType.ASSIGN_UUID)
    private String orderId;

    @ApiModelProperty(value = "订单号")
    @TableField("ORDER_NO")
    private String orderNo;

    @ApiModelProperty(value = "用户ID")
    @TableField("AUTHOR_ID")
    private String authorId;

    @ApiModelProperty(value = "地址ID")
    @TableField("ADDRESS_ID")
    private String addressId;

    @ApiModelProperty(value = "订单总金额")
    @TableField("TOTAL_AMOUNT")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "商品金额")
    @TableField("GOODS_AMOUNT")
    private BigDecimal goodsAmount;

    @ApiModelProperty(value = "运费")
    @TableField("SHIPPING_FEE")
    private BigDecimal shippingFee;

    @ApiModelProperty(value = "订单状态 0-待付款 1-已付款 2-已发货 3-已完成 4-已取消 5-申请退款 6-申请被拒绝 7-已评价")
    @TableField("ORDER_STATUS")
    private Integer orderStatus;

    @ApiModelProperty(value = "退货单号")
    @TableField("RETURN_TRACKING_NUMBER")
    private String returnTrackingNumber;

    @ApiModelProperty(value = "退款金额")
    @TableField("REFUND_AMOUNT")
    private BigDecimal refundAmount;

    @ApiModelProperty(value = "退款原因")
    @TableField("REFUND_REASON")
    private String refundReason;

    @ApiModelProperty(value = "退款申请时间")
    @TableField("REFUND_APPLY_TIME")
    private Date refundApplyTime;

    @ApiModelProperty(value = "退款处理时间")
    @TableField("REFUND_PROCESS_TIME")
    private Date refundProcessTime;

    @ApiModelProperty(value = "退款处理备注")
    @TableField("REFUND_PROCESS_REMARK")
    private String refundProcessRemark;

    @ApiModelProperty(value = "退款拒绝原因")
    @TableField("REFUND_REJECT_REASON")
    private String refundRejectReason;

    @ApiModelProperty(value = "支付时间")
    @TableField("PAY_TIME")
    private Date payTime;

    @ApiModelProperty(value = "发货时间")
    @TableField("SHIP_TIME")
    private Date shipTime;

    @ApiModelProperty(value = "快递单号")
    @TableField("TRACKING_NUMBER")
    private String trackingNumber;

    @ApiModelProperty(value = "完成时间")
    @TableField("COMPLETE_TIME")
    private Date completeTime;

    @ApiModelProperty(value = "取消时间")
    @TableField("CANCEL_TIME")
    private Date cancelTime;

    @ApiModelProperty(value = "取消原因")
    @TableField("CANCEL_REASON")
    private String cancelReason;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

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
    
    @TableField(exist = false)
    private List<OrderItem> items;
    
    @TableField(exist = false)
    private String statusDesc;
    
    @TableField(exist = false)
    private String authorName;
    
    @TableField(exist = false)
    private String receiverName;
}

