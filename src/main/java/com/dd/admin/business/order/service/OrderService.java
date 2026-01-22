package com.dd.admin.business.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.business.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.admin.business.order.vo.OrderVo;
import com.dd.admin.business.order.dto.OrderDto;
import com.dd.admin.business.order.dto.OrderAddDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-09-11
 */
public interface OrderService extends IService<Order> {

    //订单-分页列表
    IPage<OrderVo> selectOrderPage(OrderDto orderDto);

    //订单-列表
    IPage<OrderVo> selectOrderList(OrderDto orderDto);

    //订单-详情
    OrderVo selectOrderById(String orderId);
    
    //根据订单号获取订单详情
    OrderVo selectOrderByOrderNo(String orderNo);

    //订单-添加
    Order addOrder(OrderAddDto orderAddDto);
    
    //订单-创建
    Order createOrder(OrderAddDto orderAddDto);
    
    /**
     * 支付订单
     * @param orderId 订单ID
     * @return 是否成功
     */
    boolean payOrder(String orderId);
    
    /**
     * 发货
     * @param orderId 订单ID
     * @param trackingNumber 快递单号
     * @return 是否成功
     */
    boolean shipOrder(String orderId, String trackingNumber);
    
    /**
     * 修改快递单号
     * @param orderId 订单ID
     * @param trackingNumber 快递单号
     * @return 是否成功
     */
    boolean updateTrackingNumber(String orderId, String trackingNumber);
    
    /**
     * 完成订单
     * @param orderId 订单ID
     * @return 是否成功
     */
    boolean completeOrder(String orderId);
    
    /**
     * 取消订单
     * @param orderId 订单ID
     * @param cancelReason 取消原因
     * @return 是否成功
     */
    boolean cancelOrder(String orderId, String cancelReason);
    
    /**
     * 更新订单金额
     * @param orderId 订单ID
     * @param totalAmount 总金额
     * @return 是否成功
     */
    boolean updateOrderAmount(String orderId, BigDecimal totalAmount);
    
    /**
     * 确认退款
     * @param orderId 订单ID
     * @param refundAmount 退款金额
     * @param refundProcessRemark 退款处理备注
     * @return 是否成功
     */
    boolean confirmRefund(String orderId, String refundAmount, String refundProcessRemark);
    
    /**
     * 拒绝退款
     * @param orderId 订单ID
     * @param rejectReason 拒绝原因
     * @return 是否成功
     */
    boolean rejectRefund(String orderId, String rejectReason);
}
