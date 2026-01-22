package com.dd.admin.business.category.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dd.admin.business.category.entity.Category;
import com.dd.admin.business.category.mapper.CategoryMapper;
import com.dd.admin.business.category.service.CategoryService;
import com.dd.admin.common.utils.StringUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文章分类Service实现类
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    /**
     * 分页查询分类
     *
     * @param page     页码
     * @param limit    每页数量
     * @param keyword  关键词
     * @return 分页结果
     */
    @Override
    public IPage<Category> selectCategoryPage(Integer page, Integer limit, String keyword) {
        Page<Category> pageInfo = new Page<>(page, limit);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getDeleted, 0);
        
        if (StringUtil.isNotEmpty(keyword)) {
            queryWrapper.like(Category::getCategoryName, keyword);
        }
        
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getCreateTime);
        return baseMapper.selectPage(pageInfo, queryWrapper);
    }

    /**
     * 查询所有分类
     *
     * @return 分类列表
     */
    @Override
    public List<Category> selectCategoryList() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getDeleted, 0);
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getCreateTime);
        return baseMapper.selectList(queryWrapper);
    }
} 