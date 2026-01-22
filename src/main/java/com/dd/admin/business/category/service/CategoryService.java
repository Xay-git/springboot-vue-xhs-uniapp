package com.dd.admin.business.category.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.admin.business.category.entity.Category;

import java.util.List;

/**
 * 文章分类Service接口
 */
public interface CategoryService extends IService<Category> {

    /**
     * 分页查询分类
     *
     * @param page     页码
     * @param limit    每页数量
     * @param keyword  关键词
     * @return 分页结果
     */
    IPage<Category> selectCategoryPage(Integer page, Integer limit, String keyword);

    /**
     * 查询所有分类
     *
     * @return 分类列表
     */
    List<Category> selectCategoryList();
} 