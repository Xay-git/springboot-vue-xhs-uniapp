package com.dd.admin.business.product.service.impl;

import com.dd.admin.business.product.entity.ProductImage;
import com.dd.admin.business.product.mapper.ProductImageMapper;
import com.dd.admin.business.product.service.ProductImageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.ArrayList;

/**
 * <p>
 * 商品图片 服务实现类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-09-11
 */
@Slf4j
@Service
public class ProductImageServiceImpl extends ServiceImpl<ProductImageMapper, ProductImage> implements ProductImageService {

    @Override
    public List<ProductImage> getByProductId(String productId) {
        log.info("获取商品图片列表，商品ID: {}", productId);
        QueryWrapper<ProductImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("PRODUCT_ID", productId)
                   .orderByAsc("IMAGE_SORT");
        List<ProductImage> images = this.list(queryWrapper);
        log.info("获取到商品图片数量: {}", images.size());
        return images;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveProductImages(String productId, List<String> imageUrls) {
        log.info("批量保存商品图片，商品ID: {}, 图片数量: {}", productId, imageUrls != null ? imageUrls.size() : 0);
        
        if (imageUrls == null || imageUrls.isEmpty()) {
            log.warn("图片URL列表为空，跳过保存");
            return true;
        }

        try {
            List<ProductImage> productImages = new ArrayList<>();
            for (int i = 0; i < imageUrls.size(); i++) {
                ProductImage productImage = new ProductImage();
                productImage.setProductId(productId);
                productImage.setImageUrl(imageUrls.get(i));
                productImage.setImageSort(i + 1);
                productImages.add(productImage);
            }
            
            boolean result = this.saveBatch(productImages);
            log.info("批量保存商品图片结果: {}", result);
            return result;
        } catch (Exception e) {
            log.error("批量保存商品图片失败，商品ID: {}, 错误信息: {}", productId, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByProductId(String productId) {
        log.info("删除商品所有图片，商品ID: {}", productId);
        
        try {
            QueryWrapper<ProductImage> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("PRODUCT_ID", productId);
            
            boolean result = this.remove(queryWrapper);
            log.info("删除商品图片结果: {}", result);
            return result;
        } catch (Exception e) {
            log.error("删除商品图片失败，商品ID: {}, 错误信息: {}", productId, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateProductImages(String productId, List<String> imageUrls) {
        log.info("更新商品图片，商品ID: {}, 新图片数量: {}", productId, imageUrls != null ? imageUrls.size() : 0);
        
        try {
            // 先删除原有图片
            this.deleteByProductId(productId);
            
            // 再保存新图片
            if (imageUrls != null && !imageUrls.isEmpty()) {
                return this.saveProductImages(productId, imageUrls);
            }
            
            log.info("商品图片更新完成，商品ID: {}", productId);
            return true;
        } catch (Exception e) {
            log.error("更新商品图片失败，商品ID: {}, 错误信息: {}", productId, e.getMessage(), e);
            throw e;
        }
    }
}