package com.dd.admin.business.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.business.order.entity.Order;
import com.dd.admin.business.order.entity.OrderItem;
import com.dd.admin.business.order.mapper.OrderMapper;
import com.dd.admin.business.order.mapper.OrderItemMapper;
import com.dd.admin.business.order.service.OrderService;
import com.dd.admin.business.order.vo.OrderVo;
import com.dd.admin.business.order.dto.OrderDto;
import com.dd.admin.business.order.dto.OrderAddDto;
import com.dd.admin.common.model.PageFactory;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.dd.admin.business.product.service.ProductService;
import com.dd.admin.business.author.service.AuthorService;
import com.dd.admin.business.balance.service.BalanceLogService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-09-11
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderItemMapper orderItemMapper;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private AuthorService authorService;
    
    @Autowired
    private BalanceLogService balanceLogService;
    
    @Autowired
    private com.dd.admin.business.review.service.ProductReviewService productReviewService;
    
    @Override
    public IPage<OrderVo> selectOrderPage(OrderDto orderDto) {
        Page page = PageFactory.defaultPage();
        if (orderDto != null) {
            return baseMapper.selectOrderPage(page, orderDto);
        } else {
            return baseMapper.selectOrderPage(page, new OrderDto());
        }
    }
    
    @Override
    public IPage<OrderVo> selectOrderList(OrderDto orderDto) {
        Page page;
        if (orderDto != null && orderDto.getPage() != null && orderDto.getLimit() != null) {
            // 使用传入的分页参数
            page = new Page(orderDto.getPage(), orderDto.getLimit());
        } else {
            // 使用默认分页参数
            page = PageFactory.defaultPage();
        }
        
        if (orderDto != null) {
            return baseMapper.selectOrderList(page, orderDto);
        } else {
            return baseMapper.selectOrderList(page, new OrderDto());
        }
    }

    @Override
    public OrderVo selectOrderById(String orderId) {
        OrderVo orderVo = baseMapper.selectOrderById(orderId);
        if (orderVo != null && orderVo.getOrderStatus() != null && orderVo.getOrderStatus() == 7) {
            // 如果订单状态为已评价，获取评价信息
            List<com.dd.admin.business.review.vo.ProductReviewVo> reviews = productReviewService.getReviewsByOrderId(orderId);
            orderVo.setReviews(reviews);
        }
        return orderVo;
    }
    
    @Override
    public OrderVo selectOrderByOrderNo(String orderNo) {
        return baseMapper.selectOrderByOrderNo(orderNo);
    }
    
    @Override
    @Transactional
    public Order addOrder(OrderAddDto orderAddDto) {
        Order order = new Order();
        BeanUtil.copyProperties(orderAddDto, order);
        
        // 生成订单号，订单ID由框架自动生成
        order.setOrderNo(generateOrderNo());
        // 订单ID由框架自动生成，无需手动设置
        order.setOrderStatus(0); // 待支付
        
        this.save(order);
        return order;
    }
    
    @Override
    @Transactional
    public Order createOrder(OrderAddDto orderAddDto) {
        Order order = new Order();
        BeanUtil.copyProperties(orderAddDto, order);
        
        // 生成订单号和ID
        // 订单ID由框架自动生成，无需手动设置
        order.setOrderNo(generateOrderNo());
        
        // 设置运费和商品金额
        if (orderAddDto.getShippingFee() != null) {
            order.setShippingFee(orderAddDto.getShippingFee());
        } else {
            order.setShippingFee(BigDecimal.ZERO);
        }
        
        if (orderAddDto.getGoodsAmount() != null) {
            order.setGoodsAmount(orderAddDto.getGoodsAmount());
        }
        
        // 如果使用余额支付，下单时间就是支付时间
        if ("balance".equals(orderAddDto.getPaymentMethod())) {
            order.setOrderStatus(1); // 已付款
            order.setPayTime(new Date()); // 支付时间等于下单时间
        } else {
            order.setOrderStatus(0); // 待支付
        }
        
        // 保存订单
        this.save(order);
        
        // 保存订单项
        if (orderAddDto.getItems() != null && !orderAddDto.getItems().isEmpty()) {
            for (OrderAddDto.OrderItemDto itemDto : orderAddDto.getItems()) {
                OrderItem orderItem = new OrderItem();
                // 订单项ID由框架自动生成，无需手动设置
                orderItem.setOrderId(order.getOrderId());
                orderItem.setProductId(itemDto.getProductId());
                orderItem.setProductName(itemDto.getProductName());
                orderItem.setProductPrice(itemDto.getProductPrice());
                orderItem.setProductImage(itemDto.getProductImage());
                orderItem.setQuantity(itemDto.getQuantity());
                
                orderItemMapper.insert(orderItem);
            }
        }
        
        return order;
    }
    
    @Override
    @Transactional
    public boolean payOrder(String orderId) {
        Order order = this.getById(orderId);
        if (order != null && order.getOrderStatus() == 0) {
            order.setOrderStatus(1); // 已付款
            order.setPayTime(new Date());
            return this.updateById(order);
        }
        return false;
    }
    
    @Override
    @Transactional
    public boolean shipOrder(String orderId, String trackingNumber) {
        Order order = this.getById(orderId);
        if (order != null && order.getOrderStatus() == 1) {
            order.setOrderStatus(2); // 已发货
            order.setShipTime(new Date());
            order.setTrackingNumber(trackingNumber);
            return this.updateById(order);
        }
        return false;
    }
    
    @Override
    @Transactional
    public boolean updateTrackingNumber(String orderId, String trackingNumber) {
        Order order = this.getById(orderId);
        if (order != null && order.getOrderStatus() == 2) {
            order.setTrackingNumber(trackingNumber);
            return this.updateById(order);
        }
        return false;
    }
    
    @Override
    @Transactional
    public boolean completeOrder(String orderId) {
        Order order = this.getById(orderId);
        if (order != null ) {
            order.setOrderStatus(3); // 已完成
            order.setCompleteTime(new Date());
            return this.updateById(order);
        }
        return false;
    }
    
    @Override
    @Transactional
    public boolean cancelOrder(String orderId, String cancelReason) {
        Order order = this.getById(orderId);
        if (order != null && order.getOrderStatus() < 2) {
            try {
                // 1. 恢复商品库存（仅对已付款订单进行库存回退）
                if (order.getOrderStatus() == 1) { // 已付款状态才需要回退库存
                    LambdaQueryWrapper<OrderItem> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(OrderItem::getOrderId, orderId);
                    List<OrderItem> orderItems = orderItemMapper.selectList(queryWrapper);
                    
                    for (OrderItem item : orderItems) {
                        // 恢复库存：增加商品库存数量
                        productService.restoreStock(item.getProductId(), item.getQuantity());
                    }
                    
                    // 2. 返还用户余额（已付款订单取消时返还订单金额）
                    String balanceRemark = "订单取消退款，订单号：" + order.getOrderNo();
                    if (cancelReason != null && !cancelReason.trim().isEmpty()) {
                        balanceRemark += ", 取消原因: " + cancelReason;
                    }
                    // 返还订单总金额给用户（操作类型3表示管理员增加余额）
                    authorService.updateAuthorBalance(order.getAuthorId(), 3, order.getTotalAmount(), balanceRemark);
                }
                
                // 3. 更新订单状态
                order.setOrderStatus(4); // 已取消
                order.setCancelReason(cancelReason);
                order.setCancelTime(new Date());
                return this.updateById(order);
            } catch (Exception e) {
                throw new RuntimeException("订单取消处理失败: " + e.getMessage(), e);
            }
        }
        return false;
    }
    
    @Override
    @Transactional
    public boolean updateOrderAmount(String orderId, BigDecimal totalAmount) {
        Order order = this.getById(orderId);
        if (order != null && order.getOrderStatus() == 0) {
            order.setTotalAmount(totalAmount);
            return this.updateById(order);
        }
        return false;
    }
    
    @Override
    @Transactional
    public boolean confirmRefund(String orderId, String refundAmount, String refundProcessRemark) {
        Order order = this.getById(orderId);
        if (order != null && order.getOrderStatus() == 5) { // 退款申请状态
            try {
                // 1. 恢复商品库存
                LambdaQueryWrapper<OrderItem> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(OrderItem::getOrderId, orderId);
                List<OrderItem> orderItems = orderItemMapper.selectList(queryWrapper);
                
                for (OrderItem item : orderItems) {
                    // 恢复库存：增加商品库存数量
                    productService.restoreStock(item.getProductId(), item.getQuantity());
                }
                
                // 2. 返还用户余额
                BigDecimal refundAmountDecimal = new BigDecimal(refundAmount);
                // 返还用户余额（操作类型3表示管理员增加余额，该方法内部会自动记录余额变动日志）
                String balanceRemark = "订单退款，订单号：" + order.getOrderNo();
                if (order.getRefundReason() != null && !order.getRefundReason().trim().isEmpty()) {
                    balanceRemark += ", 用户退款原因: " + order.getRefundReason();
                }
                if (refundProcessRemark != null && !refundProcessRemark.trim().isEmpty()) {
                    balanceRemark += ", 处理备注: " + refundProcessRemark;
                }
                authorService.updateAuthorBalance(order.getAuthorId(), 3, refundAmountDecimal, balanceRemark);
                
                // 注意：authorService.updateAuthorBalance 方法内部已经记录了余额变动日志，无需重复调用 addRefundLog
                
                // 4. 更新订单状态
                // 退款成功后设置为已取消状态（等同于已退款）
                order.setOrderStatus(4); // 已取消/已退款
                order.setRefundAmount(refundAmountDecimal);
                order.setRefundProcessRemark(refundProcessRemark);
                order.setRefundProcessTime(new Date());
                
                return this.updateById(order);
            } catch (Exception e) {
                throw new RuntimeException("退款处理失败: " + e.getMessage(), e);
            }
        }
        return false;
    }
    
    @Override
    @Transactional
    public boolean rejectRefund(String orderId, String rejectReason) {
        Order order = this.getById(orderId);
        if (order != null && order.getOrderStatus() == 5) { // 退款申请状态
            order.setOrderStatus(6); // 申请被拒绝
            order.setRefundRejectReason(rejectReason);
            order.setRefundProcessTime(new Date());
            return this.updateById(order);
        }
        return false;
    }
    
    /**
     * 生成订单号
     * @return 订单号
     */
    private String generateOrderNo() {
        return "ORD" + System.currentTimeMillis();
    }
}






