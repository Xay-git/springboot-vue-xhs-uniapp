package com.dd.admin.business.product.controller;

import com.dd.admin.business.product.entity.ProductCategory;
import com.dd.admin.business.product.service.ProductCategoryService;
import com.dd.admin.common.aop.operationLog.aop.OperLog;
import com.dd.admin.common.aop.operationLog.aop.OperType;
import com.dd.admin.common.model.result.ResultBean;
import com.dd.admin.common.exception.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品分类 前端控制器
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-09-11
 */
@RestController
@Api(tags = "商品分类接口")
public class ProductCategoryController {
    @Autowired
    private ProductCategoryService productCategoryService;
    
    @GetMapping("/admin/product/category/list")
    @ApiOperation("获取分类列表")
    @OperLog(operModule = "商品分类", operType = OperType.QUERY, operDesc = "获取分类列表")
    public ResultBean<List<ProductCategory>> list() {
        List<ProductCategory> list = productCategoryService.list();
        return ResultBean.success(list);
    }
    
    @GetMapping("/admin/product/category/tree")
    @ApiOperation("获取分类树")
    @OperLog(operModule = "商品分类", operType = OperType.QUERY, operDesc = "获取分类树")
    public ResultBean<List<ProductCategory>> tree() {
        List<ProductCategory> tree = productCategoryService.getCategoryTree();
        return ResultBean.success(tree);
    }
    
    @PostMapping("/admin/product/category/add")
    @ApiOperation("添加分类")
    @OperLog(operModule = "商品分类", operType = OperType.ADD, operDesc = "添加分类")
    public ResultBean<?> add(@RequestBody ProductCategory category) {
        boolean result = productCategoryService.addCategory(category);
        if (!result) {
            throw new ApiException("添加失败");
        }
        return ResultBean.success();
    }
    
    @PostMapping("/admin/product/category/update")
    @ApiOperation("更新分类")
    @OperLog(operModule = "商品分类", operType = OperType.EDIT, operDesc = "更新分类")
    public ResultBean<?> update(@RequestBody ProductCategory category) {
        boolean result = productCategoryService.updateCategory(category);
        if (!result) {
            throw new ApiException("更新失败");
        }
        return ResultBean.success();
    }
    
    @GetMapping("/admin/product/category/delete/{id}")
    @ApiOperation("删除分类")
    @OperLog(operModule = "商品分类", operType = OperType.REMOVE, operDesc = "删除分类")
    public ResultBean<?> delete(@PathVariable("id") String categoryId) {
        boolean result = productCategoryService.deleteCategory(categoryId);
        if (!result) {
            throw new ApiException("删除失败");
        }
        return ResultBean.success();
    }
}






