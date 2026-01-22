package com.dd.admin.business.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.business.order.entity.Order;
import com.dd.admin.business.order.vo.OrderVo;
import com.dd.admin.business.order.dto.OrderDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 订单 Mapper 接口
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-09-11
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    //订单-分页列表
    IPage<OrderVo> selectOrderPage(Page page, @Param("orderDto") OrderDto orderDto);

    //订单-列表
    IPage<OrderVo> selectOrderList(Page page, @Param("orderDto") OrderDto orderDto);

    //订单-详情
    OrderVo selectOrderById(@Param("orderId") String orderId);
    
    //根据订单号获取订单详情
    OrderVo selectOrderByOrderNo(@Param("orderNo") String orderNo);

}

