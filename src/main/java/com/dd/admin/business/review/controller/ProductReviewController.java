package com.dd.admin.business.review.controller;

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
import com.dd.admin.business.review.entity.ProductReview;
import com.dd.admin.business.review.vo.ProductReviewVo;
import com.dd.admin.business.review.dto.ProductReviewDto;
import com.dd.admin.business.review.service.ProductReviewService;

/**
 * <p>
 * 商品评价管理 前端控制器
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-01-15
 */
@Api(tags = "商品评价管理")
@RestController
public class ProductReviewController {
    @Autowired
    ProductReviewService productReviewService;
    
    @ApiOperation(value = "评价-分页列表")
    @ApiOperationSupport(order = 1)
    @OperLog(operModule = "商品评价", operType = OperType.QUERY, operDesc = "评价-分页列表")
    @GetMapping("/admin/review/page")
    public ResultBean<IPage<ProductReviewVo>> page(ProductReviewDto reviewDto) {
        IPage<ProductReviewVo> pageInfo = productReviewService.selectReviewPage(reviewDto);
        return ResultBean.success(pageInfo);
    }
    
    @ApiOperation(value = "评价-列表")
    @ApiOperationSupport(order = 2)
    @OperLog(operModule = "商品评价", operType = OperType.QUERY, operDesc = "评价-列表")
    @GetMapping("/admin/review/list")
    public ResultBean<IPage<ProductReviewVo>> list(ProductReviewDto reviewDto) {
        IPage<ProductReviewVo> pageInfo = productReviewService.selectReviewList(reviewDto);
        return ResultBean.success(pageInfo);
    }

    @ApiOperation(value = "评价-查询")
    @ApiOperationSupport(order = 3)
    @OperLog(operModule = "商品评价", operType = OperType.QUERY, operDesc = "评价-查询")
    @GetMapping("/admin/review/{reviewId}")
    public ResultBean<ProductReviewVo> get(@PathVariable @NotBlank String reviewId) {
        ProductReviewVo reviewVo = productReviewService.selectReviewById(reviewId);
        return ResultBean.success(reviewVo);
    }
    
    @ApiOperation(value = "评价-修改")
    @ApiOperationSupport(order = 4)
    @OperLog(operModule = "商品评价", operType = OperType.EDIT, operDesc = "评价-修改")
    @PostMapping("/admin/review/update")
    public ResultBean<ProductReview> update(@RequestBody @Validated(UpdateGroup.class) ProductReviewDto reviewDto) {
        ProductReview review = new ProductReview();
        BeanUtil.copyProperties(reviewDto, review);
        productReviewService.updateById(review);
        return ResultBean.success(review);
    }

    @ApiOperation(value = "评价-删除")
    @ApiOperationSupport(order = 5)
    @OperLog(operModule = "商品评价", operType = OperType.REMOVE, operDesc = "评价-删除")
    @GetMapping("/admin/review/delete/{reviewId}")
    public ResultBean<ProductReview> delete(@PathVariable @NotBlank String reviewId) {
        productReviewService.removeById(reviewId);
        return ResultBean.success();
    }

    @ApiOperation(value = "根据商品ID获取评价列表")
    @ApiOperationSupport(order = 6)
    @OperLog(operModule = "商品评价", operType = OperType.QUERY, operDesc = "根据商品ID获取评价列表")
    @GetMapping("/admin/review/product/{productId}")
    public ResultBean<java.util.List<ProductReviewVo>> getByProductId(@PathVariable @NotBlank String productId) {
        java.util.List<ProductReviewVo> reviews = productReviewService.getReviewsByProductId(productId);
        return ResultBean.success(reviews);
    }

    @ApiOperation(value = "根据订单ID获取评价列表")
    @ApiOperationSupport(order = 7)
    @OperLog(operModule = "商品评价", operType = OperType.QUERY, operDesc = "根据订单ID获取评价列表")
    @GetMapping("/admin/review/order/{orderId}")
    public ResultBean<java.util.List<ProductReviewVo>> getByOrderId(@PathVariable @NotBlank String orderId) {
        java.util.List<ProductReviewVo> reviews = productReviewService.getReviewsByOrderId(orderId);
        return ResultBean.success(reviews);
    }
}