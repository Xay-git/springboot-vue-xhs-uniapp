package com.dd.admin.business.review.service.impl;

import com.dd.admin.business.review.entity.ProductReview;
import com.dd.admin.business.review.dto.ProductReviewDto;
import com.dd.admin.business.review.vo.ProductReviewVo;
import com.dd.admin.business.review.mapper.ProductReviewMapper;
import com.dd.admin.business.review.service.ProductReviewService;
import com.dd.admin.business.order.entity.Order;
import com.dd.admin.business.order.entity.OrderItem;
import com.dd.admin.business.order.vo.OrderVo;
import com.dd.admin.business.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.hutool.core.bean.BeanUtil;
import com.dd.admin.common.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品评价 服务实现类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-01-15
 */
@Service
public class ProductReviewServiceImpl extends ServiceImpl<ProductReviewMapper, ProductReview> implements ProductReviewService {

    @Autowired
    private OrderService orderService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean submitReview(ProductReviewDto reviewDto, String authorId) {
        // 检查权限
        if (!checkReviewPermission(reviewDto.getOrderId(), authorId)) {
            throw new ApiException("无权限评价该订单");
        }

        // 检查是否已评价
        QueryWrapper<ProductReview> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ORDER_ID", reviewDto.getOrderId())
                   .eq("PRODUCT_ID", reviewDto.getProductId())
                   .eq("AUTHOR_ID", authorId)
                   .eq("DELETED", 0);
        ProductReview existReview = this.getOne(queryWrapper);
        if (existReview != null) {
            throw new ApiException("该商品已评价，不能重复评价");
        }

        // 创建评价记录
        ProductReview review = new ProductReview();
        BeanUtil.copyProperties(reviewDto, review);
        review.setAuthorId(authorId);

        return this.save(review);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean submitBatchReview(List<ProductReviewDto> reviewDtos, String authorId) {
        if (reviewDtos == null || reviewDtos.isEmpty()) {
            throw new ApiException("评价信息不能为空");
        }

        String orderId = reviewDtos.get(0).getOrderId();

        // 检查权限
        if (!checkReviewPermission(orderId, authorId)) {
            throw new ApiException("无权限评价该订单");
        }

        // 检查订单是否已评价
        if (isOrderReviewed(orderId)) {
            throw new ApiException("该订单已评价，不能重复评价");
        }

        // 批量创建评价记录
        List<ProductReview> reviews = reviewDtos.stream().map(dto -> {
            ProductReview review = new ProductReview();
            BeanUtil.copyProperties(dto, review);
            review.setAuthorId(authorId);
            return review;
        }).collect(Collectors.toList());

        boolean result = this.saveBatch(reviews);

        // 更新订单状态为已评价
        if (result) {
            Order order = orderService.getById(orderId);
            if (order != null && order.getOrderStatus() == 3) { // 已完成状态
                order.setOrderStatus(7); // 已评价
                orderService.updateById(order);
            }
        }

        return result;
    }

    @Override
    public List<ProductReviewVo> getReviewsByOrderId(String orderId) {
        return baseMapper.selectReviewDetailsByOrderId(orderId);
    }

    @Override
    public boolean isOrderReviewed(String orderId) {
        QueryWrapper<ProductReview> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ORDER_ID", orderId).eq("DELETED", 0);
        return this.count(queryWrapper) > 0;
    }

    @Override
    public List<ProductReviewVo> getOrderItemsForReview(String orderId, String authorId) {
        // 检查权限
        if (!checkReviewPermission(orderId, authorId)) {
            throw new ApiException("无权限查看该订单信息");
        }

        // 获取订单详情（包含订单项）
        OrderVo orderVo = orderService.selectOrderById(orderId);
        if (orderVo == null) {
            throw new ApiException("订单不存在");
        }

        // 只有已完成的订单才能评价
        if (orderVo.getOrderStatus() != 3) {
            throw new ApiException("订单状态不正确，无法评价");
        }

        // 获取订单项
        List<OrderItem> orderItems = orderVo.getItems();
        if (orderItems == null || orderItems.isEmpty()) {
            throw new ApiException("订单商品信息不存在");
        }

        // 转换为评价VO
        return orderItems.stream().map(item -> {
            ProductReviewVo vo = new ProductReviewVo();
            vo.setOrderId(orderId);
            vo.setItemId(item.getItemId());
            vo.setProductId(item.getProductId());
            vo.setProductName(item.getProductName());
            vo.setProductImage(item.getProductImage());
            vo.setAuthorId(authorId);
            vo.setRating(5); // 默认5星

            // 检查是否已评价
            QueryWrapper<ProductReview> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("ORDER_ID", orderId)
                       .eq("PRODUCT_ID", item.getProductId())
                       .eq("AUTHOR_ID", authorId)
                       .eq("DELETED", 0);
            ProductReview existReview = this.getOne(queryWrapper);
            if (existReview != null) {
                vo.setReviewId(existReview.getReviewId());
                vo.setContent(existReview.getContent());
                vo.setRating(existReview.getRating());
                vo.setImgId(existReview.getImgId());
                vo.setImgUrl(existReview.getImgUrl());
            }

            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public boolean checkReviewPermission(String orderId, String authorId) {
        Order order = orderService.getById(orderId);
        return order != null && authorId.equals(order.getAuthorId());
    }

    @Override
    public List<ProductReviewVo> getReviewsByProductId(String productId) {
        return baseMapper.selectReviewsByProductId(productId);
    }

    @Override
    public com.baomidou.mybatisplus.core.metadata.IPage<ProductReviewVo> selectReviewPage(ProductReviewDto reviewDto) {
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<ProductReviewVo> page = 
            new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(reviewDto.getCurrent(), reviewDto.getSize());
        return baseMapper.selectReviewPage(page, reviewDto);
    }

    @Override
    public com.baomidou.mybatisplus.core.metadata.IPage<ProductReviewVo> selectReviewList(ProductReviewDto reviewDto) {
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<ProductReviewVo> page = 
            new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(reviewDto.getCurrent(), reviewDto.getSize());
        return baseMapper.selectReviewPage(page, reviewDto);
    }

    @Override
    public ProductReviewVo selectReviewById(String reviewId) {
        return baseMapper.selectReviewById(reviewId);
    }
}