package com.dd.admin.business.category.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.business.category.entity.Category;
import com.dd.admin.business.category.service.CategoryService;
import com.dd.admin.common.aop.operationLog.aop.OperLog;
import com.dd.admin.common.aop.operationLog.aop.OperType;
import com.dd.admin.common.model.result.ResultBean;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 文章分类Controller
 */
@Api(tags = "文章分类管理")
@RestController
@RequestMapping("/admin/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "分页查询分类")
    @ApiOperationSupport(order = 1)
    @GetMapping("/page")
    @OperLog(operModule = "分页查询分类", operType = OperType.QUERY, operDesc = "分页查询分类")
    public ResultBean<IPage<Category>> page(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @RequestParam(value = "keyword", required = false) String keyword) {
        IPage<Category> pageInfo = categoryService.selectCategoryPage(page, limit, keyword);
        return ResultBean.success(pageInfo);
    }

    @ApiOperation(value = "查询所有分类")
    @ApiOperationSupport(order = 2)
    @GetMapping("/list")
    @OperLog(operModule = "查询所有分类", operType = OperType.QUERY, operDesc = "查询所有分类")
    public ResultBean<List<Category>> list() {
        List<Category> categoryList = categoryService.selectCategoryList();
        return ResultBean.success(categoryList);
    }

    @ApiOperation(value = "添加分类")
    @ApiOperationSupport(order = 3)
    @PostMapping("/add")
    @OperLog(operModule = "添加分类", operType = OperType.ADD, operDesc = "添加分类")
    public ResultBean<Boolean> add(@RequestBody Category category) {
        Date now = new Date();
        category.setCreateTime(now);
        category.setUpdateTime(now);
        category.setVersion(0);
        category.setDeleted(0);
        boolean result = categoryService.save(category);
        return ResultBean.success(result);
    }

    @ApiOperation(value = "修改分类")
    @ApiOperationSupport(order = 4)
    @PostMapping("/update")
    @OperLog(operModule = "修改分类", operType = OperType.EDIT, operDesc = "修改分类")
    public ResultBean<Boolean> update(@RequestBody Category category) {
        category.setUpdateTime(new Date());
        boolean result = categoryService.updateById(category);
        return ResultBean.success(result);
    }

    @ApiOperation(value = "删除分类")
    @ApiOperationSupport(order = 5)
    @GetMapping("/delete/{categoryId}")
    @OperLog(operModule = "删除分类", operType = OperType.REMOVE, operDesc = "删除分类")
    public ResultBean<Boolean> delete(@PathVariable String categoryId) {
        Category category = categoryService.getById(categoryId);
        if (category != null) {
            category.setDeleted(1);
            category.setUpdateTime(new Date());
            boolean result = categoryService.updateById(category);
            return ResultBean.success(result);
        }
        return ResultBean.error("分类不存在");
    }
}