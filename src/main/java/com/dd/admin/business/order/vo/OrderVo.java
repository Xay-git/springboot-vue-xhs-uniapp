package com.dd.admin.business.order.vo;

import com.dd.admin.business.order.entity.OrderItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 订单返回VO
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-01-17
 */
@Data
@ApiModel(value="OrderVo对象", description="订单返回VO")
public class OrderVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单ID")
    private String orderId;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "用户ID")
    private String authorId;

    @ApiModelProperty(value = "用户名称")
    private String authorName;

    @ApiModelProperty(value = "地址ID")
    private String addressId;

    @ApiModelProperty(value = "收货人姓名")
    private String receiverName;

    @ApiModelProperty(value = "收货人电话")
    private String receiverPhone;

    @ApiModelProperty(value = "收货地址")
    private String receiverAddress;

    @ApiModelProperty(value = "订单总金额")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "商品金额")
    private BigDecimal goodsAmount;

    @ApiModelProperty(value = "运费")
    private BigDecimal shippingFee;

    @ApiModelProperty(value = "订单状态 0-待付款 1-已付款 2-已发货 3-已完成 4-已取消")
    private Integer orderStatus;

    @ApiModelProperty(value = "订单状态描述")
    private String statusDesc;

    @ApiModelProperty(value = "支付时间")
    private Date payTime;

    @ApiModelProperty(value = "发货时间")
    private Date shipTime;

    @ApiModelProperty(value = "快递单号")
    private String trackingNumber;

    @ApiModelProperty(value = "完成时间")
    private Date completeTime;

    @ApiModelProperty(value = "取消时间")
    private Date cancelTime;

    @ApiModelProperty(value = "取消原因")
    private String cancelReason;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "退款金额")
    private BigDecimal refundAmount;

    @ApiModelProperty(value = "退款原因")
    private String refundReason;

    @ApiModelProperty(value = "退款申请时间")
    private Date refundApplyTime;

    @ApiModelProperty(value = "退款处理时间")
    private Date refundProcessTime;

    @ApiModelProperty(value = "退款处理备注")
    private String refundProcessRemark;

    @ApiModelProperty(value = "退货单号")
    private String returnTrackingNumber;

    @ApiModelProperty(value = "退款拒绝原因")
    private String refundRejectReason;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "订单项列表")
    private List<OrderItem> items;

    @ApiModelProperty(value = "订单评价列表")
    private List<com.dd.admin.business.review.vo.ProductReviewVo> reviews;
}