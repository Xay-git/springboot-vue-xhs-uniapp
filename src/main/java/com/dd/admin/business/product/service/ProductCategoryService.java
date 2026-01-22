package com.dd.admin.business.product.service;

import com.dd.admin.business.product.entity.ProductCategory;
import com.dd.admin.business.product.vo.ProductCategoryVo;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * 商品分类 服务类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-09-11
 */
public interface ProductCategoryService extends IService<ProductCategory> {
    /**
     * 获取分类树形结构
     * @return 分类树
     */
    List<ProductCategory> getCategoryTree();
    
    /**
     * 新增分类
     * @param category 分类信息
     * @return 是否成功
     */
    boolean addCategory(ProductCategory category);
    
    /**
     * 更新分类
     * @param category 分类信息
     * @return 是否成功
     */
    boolean updateCategory(ProductCategory category);
    
    /**
     * 删除分类
     * @param categoryId 分类ID
     * @return 是否成功
     */
    boolean deleteCategory(String categoryId);
    
    /**
     * 获取分类列表（移动端使用）
     * @return 分类列表
     */
    List<ProductCategoryVo> selectCategoryList();
}
