package com.dd.admin.business.review.service;

import com.dd.admin.business.review.entity.ProductReview;
import com.dd.admin.business.review.dto.ProductReviewDto;
import com.dd.admin.business.review.vo.ProductReviewVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品评价 服务类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-01-15
 */
public interface ProductReviewService extends IService<ProductReview> {

    /**
     * 提交商品评价
     * @param reviewDto 评价信息
     * @param authorId 用户ID
     * @return 是否成功
     */
    boolean submitReview(ProductReviewDto reviewDto, String authorId);

    /**
     * 批量提交商品评价
     * @param reviewDtos 评价信息列表
     * @param authorId 用户ID
     * @return 是否成功
     */
    boolean submitBatchReview(List<ProductReviewDto> reviewDtos, String authorId);

    /**
     * 根据订单ID查询评价列表
     * @param orderId 订单ID
     * @return 评价列表
     */
    List<ProductReviewVo> getReviewsByOrderId(String orderId);

    /**
     * 检查订单是否已评价
     * @param orderId 订单ID
     * @return 是否已评价
     */
    boolean isOrderReviewed(String orderId);

    /**
     * 获取订单商品评价信息（用于评价页面展示）
     * @param orderId 订单ID
     * @param authorId 用户ID
     * @return 商品评价信息列表
     */
    List<ProductReviewVo> getOrderItemsForReview(String orderId, String authorId);

    /**
     * 检查用户是否有权限评价该订单
     * @param orderId 订单ID
     * @param authorId 用户ID
     * @return 是否有权限
     */
    boolean checkReviewPermission(String orderId, String authorId);

    /**
     * 根据商品ID获取评价列表
     * @param productId 商品ID
     * @return 评价列表
     */
    List<ProductReviewVo> getReviewsByProductId(String productId);

    /**
     * 分页查询评价列表
     * @param reviewDto 查询条件
     * @return 分页结果
     */
    com.baomidou.mybatisplus.core.metadata.IPage<ProductReviewVo> selectReviewPage(ProductReviewDto reviewDto);

    /**
     * 查询评价列表
     * @param reviewDto 查询条件
     * @return 分页结果
     */
    com.baomidou.mybatisplus.core.metadata.IPage<ProductReviewVo> selectReviewList(ProductReviewDto reviewDto);

    /**
     * 根据ID查询评价详情
     * @param reviewId 评价ID
     * @return 评价详情
     */
    ProductReviewVo selectReviewById(String reviewId);
}