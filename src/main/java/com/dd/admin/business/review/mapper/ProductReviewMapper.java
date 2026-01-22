package com.dd.admin.business.review.mapper;

import com.dd.admin.business.review.entity.ProductReview;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品评价 Mapper 接口
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-01-15
 */
@Mapper
public interface ProductReviewMapper extends BaseMapper<ProductReview> {

    /**
     * 根据订单ID查询评价列表
     * @param orderId 订单ID
     * @return 评价列表
     */
    List<ProductReview> selectByOrderId(@Param("orderId") String orderId);

    /**
     * 根据订单ID和商品ID查询评价
     * @param orderId 订单ID
     * @param productId 商品ID
     * @return 评价信息
     */
    ProductReview selectByOrderIdAndProductId(@Param("orderId") String orderId, @Param("productId") String productId);

    /**
     * 查询订单商品评价详情（包含商品信息）
     * @param orderId 订单ID
     * @return 评价详情列表
     */
    List<com.dd.admin.business.review.vo.ProductReviewVo> selectReviewDetailsByOrderId(@Param("orderId") String orderId);

    /**
     * 根据商品ID查询评论列表（包含用户信息）
     * @param productId 商品ID
     * @return 评论列表
     */
    List<com.dd.admin.business.review.vo.ProductReviewVo> selectReviewsByProductId(@Param("productId") String productId);

    /**
     * 分页查询评价列表
     * @param page 分页参数
     * @param reviewDto 查询条件
     * @return 分页结果
     */
    com.baomidou.mybatisplus.core.metadata.IPage<com.dd.admin.business.review.vo.ProductReviewVo> selectReviewPage(
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.dd.admin.business.review.vo.ProductReviewVo> page,
        @Param("reviewDto") com.dd.admin.business.review.dto.ProductReviewDto reviewDto);

    /**
     * 根据ID查询评价详情
     * @param reviewId 评价ID
     * @return 评价详情
     */
    com.dd.admin.business.review.vo.ProductReviewVo selectReviewById(@Param("reviewId") String reviewId);
}