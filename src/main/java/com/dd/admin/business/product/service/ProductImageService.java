package com.dd.admin.business.product.service;

import com.dd.admin.business.product.entity.ProductImage;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * 商品图片 服务类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-09-11
 */
public interface ProductImageService extends IService<ProductImage> {

    /**
     * 根据商品ID获取商品图片列表
     * @param productId 商品ID
     * @return 商品图片列表
     */
    List<ProductImage> getByProductId(String productId);

    /**
     * 批量保存商品图片
     * @param productId 商品ID
     * @param imageUrls 图片URL列表
     * @return 是否成功
     */
    boolean saveProductImages(String productId, List<String> imageUrls);

    /**
     * 删除商品的所有图片
     * @param productId 商品ID
     * @return 是否成功
     */
    boolean deleteByProductId(String productId);

    /**
     * 更新商品图片
     * @param productId 商品ID
     * @param imageUrls 新的图片URL列表
     * @return 是否成功
     */
    boolean updateProductImages(String productId, List<String> imageUrls);
}