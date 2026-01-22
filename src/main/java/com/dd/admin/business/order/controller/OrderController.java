package com.dd.admin.business.order.controller;

import cn.hutool.core.bean.BeanUtil;
import com.dd.admin.common.aop.operationLog.aop.OperLog;
import com.dd.admin.common.aop.operationLog.aop.OperType;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.dd.admin.common.model.UpdateGroup;
import com.dd.admin.common.model.result.ResultBean;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotBlank;
import com.dd.admin.business.order.entity.Order;
import com.dd.admin.business.order.vo.OrderVo;
import com.dd.admin.business.order.dto.OrderDto;
import com.dd.admin.business.order.dto.OrderAddDto;
import com.dd.admin.business.order.service.OrderService;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-09-11
 */
@Api(tags = "订单")
@RestController
public class OrderController {
    @Autowired
    OrderService orderService;
    
    @ApiOperation(value = "订单-分页列表")
    @ApiOperationSupport(order = 1)
    @OperLog(operModule = "订单", operType = OperType.QUERY, operDesc = "订单-分页列表")
    @GetMapping("/admin/order/page")
    public ResultBean<IPage<OrderVo>> page(OrderDto orderDto) {
        IPage<OrderVo> pageInfo = orderService.selectOrderPage(orderDto);
        return ResultBean.success(pageInfo);
    }
    
    @ApiOperation(value = "订单-列表")
    @ApiOperationSupport(order = 2)
    @OperLog(operModule = "订单", operType = OperType.QUERY, operDesc = "订单-列表")
    @GetMapping("/admin/order/list")
    public ResultBean<IPage<OrderVo>> list(OrderDto orderDto) {
        IPage<OrderVo> pageInfo = orderService.selectOrderList(orderDto);
        return ResultBean.success(pageInfo);
    }

    @ApiOperation(value = "订单-添加")
    @ApiOperationSupport(order = 3)
    @OperLog(operModule = "订单", operType = OperType.ADD, operDesc = "订单-添加")
    @PostMapping("/admin/order/add")
    public ResultBean<Order> add(@RequestBody @Validated OrderAddDto orderDto) {
        Order order = orderService.createOrder(orderDto);
        return ResultBean.success(order);
    }

    @ApiOperation(value = "订单-查询")
    @ApiOperationSupport(order = 4)
    @OperLog(operModule = "订单", operType = OperType.QUERY, operDesc = "订单-查询")
    @GetMapping("/admin/order/{orderId}")
    public ResultBean<OrderVo> get(@PathVariable @NotBlank String orderId) {
        OrderVo orderVo = orderService.selectOrderById(orderId);
        return ResultBean.success(orderVo);
    }
    
    @ApiOperation(value = "订单-修改")
    @ApiOperationSupport(order = 5)
    @OperLog(operModule = "订单", operType = OperType.EDIT, operDesc = "订单-修改")
    @PostMapping("/admin/order/update")
    public ResultBean<Order> update(@RequestBody @Validated(UpdateGroup.class) OrderDto orderDto) {
        Order order = new Order();
        BeanUtil.copyProperties(orderDto, order);
        orderService.updateById(order);
        return ResultBean.success(order);
    }

    @ApiOperation(value = "订单-删除")
    @ApiOperationSupport(order = 6)
    @OperLog(operModule = "订单", operType = OperType.REMOVE, operDesc = "订单-删除")
    @GetMapping("/admin/order/delete/{orderId}")
    public ResultBean<Order> delete(@PathVariable @NotBlank String orderId) {
        orderService.removeById(orderId);
        return ResultBean.success();
    }

    @ApiOperation(value = "订单-支付")
    @ApiOperationSupport(order = 7)
    @OperLog(operModule = "订单", operType = OperType.EDIT, operDesc = "订单-支付")
    @PostMapping("/admin/order/pay")
    public ResultBean<?> pay(@RequestParam String orderId) {
        boolean result = orderService.payOrder(orderId);
        return result ? ResultBean.success() : ResultBean.error("支付失败");
    }

    @ApiOperation(value = "订单-发货")
    @ApiOperationSupport(order = 8)
    @OperLog(operModule = "订单", operType = OperType.EDIT, operDesc = "订单-发货")
    @PostMapping("/admin/order/ship")
    public ResultBean<?> ship(@RequestParam String orderId, @RequestParam String trackingNumber) {
        boolean result = orderService.shipOrder(orderId, trackingNumber);
        return result ? ResultBean.success() : ResultBean.error("发货失败");
    }

    @ApiOperation(value = "订单-修改快递单号")
    @ApiOperationSupport(order = 11)
    @OperLog(operModule = "订单", operType = OperType.EDIT, operDesc = "订单-修改快递单号")
    @PostMapping("/admin/order/updateTracking")
    public ResultBean<?> updateTracking(@RequestParam String orderId, @RequestParam String trackingNumber) {
        boolean result = orderService.updateTrackingNumber(orderId, trackingNumber);
        return result ? ResultBean.success("快递单号修改成功") : ResultBean.error("修改失败");
    }

    @ApiOperation(value = "订单-完成")
    @ApiOperationSupport(order = 9)
    @OperLog(operModule = "订单", operType = OperType.EDIT, operDesc = "订单-完成")
    @PostMapping("/admin/order/complete")
    public ResultBean<?> complete(@RequestParam String orderId) {
        boolean result = orderService.completeOrder(orderId);
        return result ? ResultBean.success() : ResultBean.error("完成订单失败");
    }

    @ApiOperation(value = "订单-取消")
    @ApiOperationSupport(order = 10)
    @OperLog(operModule = "订单", operType = OperType.EDIT, operDesc = "订单-取消")
    @PostMapping("/admin/order/cancel")
    public ResultBean<?> cancel(@RequestParam String orderId, @RequestParam(required = false) String cancelReason) {
        boolean result = orderService.cancelOrder(orderId, cancelReason);
        return result ? ResultBean.success() : ResultBean.error("取消订单失败");
    }

    @ApiOperation(value = "订单-确认退款")
    @ApiOperationSupport(order = 11)
    @OperLog(operModule = "订单", operType = OperType.EDIT, operDesc = "订单-确认退款")
    @PostMapping("/admin/order/confirmRefund")
    public ResultBean<?> confirmRefund(@RequestParam String orderId, 
                                     @RequestParam String refundAmount, 
                                     @RequestParam(required = false) String refundProcessRemark) {
        boolean result = orderService.confirmRefund(orderId, refundAmount, refundProcessRemark);
        return result ? ResultBean.success("退款成功") : ResultBean.error("退款失败");
    }

    @ApiOperation(value = "订单-拒绝退款")
    @ApiOperationSupport(order = 12)
    @OperLog(operModule = "订单", operType = OperType.EDIT, operDesc = "订单-拒绝退款")
    @PostMapping("/admin/order/rejectRefund")
    public ResultBean<?> rejectRefund(@RequestParam String orderId, 
                                    @RequestParam(required = false) String rejectReason) {
        boolean result = orderService.rejectRefund(orderId, rejectReason);
        return result ? ResultBean.success("已拒绝退款申请") : ResultBean.error("拒绝退款失败");
    }
}






