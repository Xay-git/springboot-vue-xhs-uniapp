package com.dd.admin.business.category.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.admin.business.category.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章分类Mapper接口
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
} 