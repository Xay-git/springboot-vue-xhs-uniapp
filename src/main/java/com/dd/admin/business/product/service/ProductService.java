package com.dd.admin.business.product.service;

import com.dd.admin.business.product.dto.ProductDto;
import com.dd.admin.business.product.dto.ProductAddDto;
import com.dd.admin.business.product.vo.ProductVo;
import com.dd.admin.business.product.entity.Product;
import com.dd.admin.business.product.entity.ProductImage;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 商品Service接口
 *
 * @author system
 * @date 2024-01-15
 */
public interface ProductService extends IService<Product> {

    /**
     * 分页查询商品
     *
     * @param page 分页参数
     * @param productDto 查询条件
     * @return 分页结果
     */
    IPage<ProductVo> selectProductPage(IPage<ProductVo> page, ProductDto productDto);

    /**
     * 查询商品列表
     *
     * @param productDto 查询条件
     * @return 商品列表
     */
    List<ProductVo> selectProductList(ProductDto productDto);

    /**
     * 根据ID查询商品详情（使用MyBatis Plus内置方法）
     *
     * @param productId 商品ID
     * @return 商品详情
     */
    ProductVo selectProductById(String productId);

    /**
     * 新增商品
     *
     * @param productAddDto 商品信息
     */
    void addProduct(ProductAddDto productAddDto);

    /**
     * 修改商品
     *
     * @param productDto 商品信息
     */
    void updateProduct(ProductDto productDto);

    /**
     * 删除商品
     *
     * @param productId 商品ID
     */
    void deleteProduct(String productId);
    
    /**
     * 更新商品状态
     *
     * @param productId 商品ID
     * @param status 状态
     */
    void updateProductStatus(String productId, Integer status);
    

    
    /**
     * 添加商品图片
     * @param productImage 图片信息
     * @return 是否成功
     */
    boolean addProductImage(ProductImage productImage);
    
    /**
     * 删除商品图片
     * @param id 主键ID
     * @return 是否成功
     */
    boolean deleteProductImage(String id);
    
    /**
     * 更新商品库存和销量
     * @param productId 商品ID
     * @param quantity 购买数量
     * @return 是否成功
     */
    boolean updateStockAndSales(String productId, Integer quantity);
    
    /**
     * 恢复商品库存（退款时使用）
     * @param productId 商品ID
     * @param quantity 恢复数量
     * @return 是否成功
     */
    boolean restoreStock(String productId, Integer quantity);
    
    /**
     * 增加商品浏览数
     *
     * @param productId 商品ID
     * @return 是否成功
     */
    boolean incrementViews(String productId);

    /**
     * 检查商品库存是否充足
     *
     * @param productId 商品ID
     * @param quantity 需要的数量
     * @throws com.dd.admin.common.exception.ApiException 库存不足时抛出异常，包含商品名称
     */
    void checkStock(String productId, Integer quantity);

}