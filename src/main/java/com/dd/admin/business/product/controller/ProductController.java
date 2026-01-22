package com.dd.admin.business.product.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.business.product.dto.ProductDto;
import com.dd.admin.business.product.dto.ProductAddDto;
import com.dd.admin.business.product.vo.ProductVo;
import com.dd.admin.business.product.service.ProductService;
import com.dd.admin.common.aop.operationLog.aop.OperLog;
import com.dd.admin.common.aop.operationLog.aop.OperType;
import com.dd.admin.common.model.PageFactory;
import com.dd.admin.common.model.result.ResultBean;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品 前端控制器
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-09-11
 */
@RestController
@Api(tags = "商品")
public class ProductController {
    @Autowired
    private ProductService productService;
    
    @GetMapping("/admin/product/page")
    @ApiOperation("分页查询商品")
    @ApiOperationSupport(order = 1)
    @OperLog(operModule = "商品", operType = OperType.QUERY, operDesc = "分页查询商品")
    public ResultBean<IPage<ProductVo>> selectProductPage(ProductDto productDto) {
        Page page = PageFactory.defaultPage();
        IPage<ProductVo> productPage = productService.selectProductPage(page, productDto);
        return ResultBean.success(productPage);
    }
    
    @GetMapping("/admin/product/list")
    @ApiOperation("查询商品列表")
    @ApiOperationSupport(order = 2)
    @OperLog(operModule = "商品", operType = OperType.QUERY, operDesc = "查询商品列表")
    public ResultBean<List<ProductVo>> selectProductList(ProductDto productDto) {
        List<ProductVo> productList = productService.selectProductList(productDto);
        return ResultBean.success(productList);
    }
    
    @GetMapping("/admin/product/{id}")
    @ApiOperation("根据ID查询商品")
    @ApiOperationSupport(order = 3)
    @OperLog(operModule = "商品", operType = OperType.QUERY, operDesc = "根据ID查询商品")
    public ResultBean<ProductVo> selectProductById(@PathVariable("id") String id) {
        ProductVo product = productService.selectProductById(id);
        return ResultBean.success(product);
    }
    
    @PostMapping("/admin/product/add")
    @ApiOperation("添加商品")
    @ApiOperationSupport(order = 4)
    @OperLog(operModule = "商品", operType = OperType.ADD, operDesc = "添加商品")
    public ResultBean<?> addProduct(@RequestBody ProductAddDto productAddDto) {
        productService.addProduct(productAddDto);
        return ResultBean.success();
    }
    
    @PostMapping("/admin/product/update")
    @ApiOperation("修改商品")
    @ApiOperationSupport(order = 5)
    @OperLog(operModule = "商品", operType = OperType.EDIT, operDesc = "修改商品")
    public ResultBean<?> updateProduct(@RequestBody ProductDto productDto) {
        productService.updateProduct(productDto);
        return ResultBean.success();
    }
    
    @GetMapping("/admin/product/delete/{id}")
    @ApiOperation("删除商品")
    @ApiOperationSupport(order = 6)
    @OperLog(operModule = "商品", operType = OperType.REMOVE, operDesc = "删除商品")
    public ResultBean<?> deleteProduct(@PathVariable("id") String id) {
        productService.deleteProduct(id);
        return ResultBean.success();
    }
    
    @PostMapping("/admin/product/status")
    @ApiOperation("更新商品状态")
    @ApiOperationSupport(order = 7)
    @OperLog(operModule = "商品", operType = OperType.EDIT, operDesc = "更新商品状态")
    public ResultBean<?> updateProductStatus(@RequestParam("productId") String productId, 
                                            @RequestParam("status") Integer status) {
        productService.updateProductStatus(productId, status);
        return ResultBean.success();
    }

}

