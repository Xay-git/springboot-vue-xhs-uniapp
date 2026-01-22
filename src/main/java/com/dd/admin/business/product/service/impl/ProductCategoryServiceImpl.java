package com.dd.admin.business.product.service.impl;

import com.dd.admin.business.product.entity.ProductCategory;
import com.dd.admin.business.product.mapper.ProductCategoryMapper;
import com.dd.admin.business.product.service.ProductCategoryService;
import com.dd.admin.business.product.vo.ProductCategoryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品分类 服务实现类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-09-11
 */
@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {

    @Override
    public List<ProductCategory> getCategoryTree() {
        // 获取所有分类
        List<ProductCategory> allCategories = this.list();
        
        // 获取顶级分类
        List<ProductCategory> rootCategories = allCategories.stream()
                .filter(category -> category.getParentId() == null || "".equals(category.getParentId().trim()))
                .collect(Collectors.toList());
        
        // 获取非顶级分类，按照parentId分组
        Map<String, List<ProductCategory>> childrenMap = allCategories.stream()
                .filter(category -> category.getParentId() != null && !"".equals(category.getParentId().trim()))
                .collect(Collectors.groupingBy(ProductCategory::getParentId));
        
        // 递归设置子分类
        rootCategories.forEach(root -> setChildren(root, childrenMap));
        
        return rootCategories;
    }
    
    private void setChildren(ProductCategory parent, Map<String, List<ProductCategory>> childrenMap) {
        List<ProductCategory> children = childrenMap.get(parent.getCategoryId());
        if (children != null) {
            parent.setChildren(children);
            children.forEach(child -> setChildren(child, childrenMap));
        }
    }
    
    @Override
    public List<ProductCategoryVo> selectCategoryList() {
        // 查询启用的分类
        LambdaQueryWrapper<ProductCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductCategory::getCategoryStatus, 0); // 0-正常
        queryWrapper.orderByAsc(ProductCategory::getCategorySort);
        
        List<ProductCategory> categoryList = this.list(queryWrapper);
        
        // 转换为VO对象
        return categoryList.stream().map(category -> {
            ProductCategoryVo vo = new ProductCategoryVo();
            BeanUtils.copyProperties(category, vo);
            return vo;
        }).collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public boolean addCategory(ProductCategory category) {
        return this.save(category);
    }
    
    @Override
    @Transactional
    public boolean updateCategory(ProductCategory category) {
        return this.updateById(category);
    }
    
    @Override
    @Transactional
    public boolean deleteCategory(String categoryId) {
        // 检查是否有子分类
        LambdaQueryWrapper<ProductCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductCategory::getParentId, categoryId);
        int count = this.count(wrapper);
        if (count > 0) {
            throw new RuntimeException("该分类下有子分类，无法删除");
        }
        
        return this.removeById(categoryId);
    }
}






