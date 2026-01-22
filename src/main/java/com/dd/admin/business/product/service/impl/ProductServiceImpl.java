package com.dd.admin.business.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.dd.admin.business.product.dto.ProductDto;
import com.dd.admin.business.product.dto.ProductAddDto;
import com.dd.admin.business.product.vo.ProductVo;
import com.dd.admin.business.product.entity.Product;
import com.dd.admin.business.product.entity.ProductImage;
import com.dd.admin.business.product.mapper.ProductMapper;
import com.dd.admin.business.product.service.ProductService;
import com.dd.admin.business.product.service.ProductImageService;
import com.dd.admin.common.exception.ApiException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-09-11
 */
@Slf4j
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private ProductMapper productMapper;
    
    @Autowired
    private ProductImageService productImageService;
    
    @Override
    public IPage<ProductVo> selectProductPage(IPage<ProductVo> page, ProductDto productDto) {
        log.info("执行商品分页查询 - 页码: {}, 每页数量: {}, 分类ID: {}, 商品状态: {}", 
                page.getCurrent(), page.getSize(), productDto.getCategoryId(), productDto.getProductStatus());
        
        IPage<ProductVo> result = productMapper.selectProductPage(page, productDto);
        
        log.info("商品分页查询结果 - 总数: {}, 当前页数据量: {}", result.getTotal(), result.getRecords().size());
        
        // 为每个商品填充imageObjects字段
        if (result != null && result.getRecords() != null) {
            for (ProductVo productVo : result.getRecords()) {
                // 获取商品图片列表
                List<ProductImage> productImages = productImageService.getByProductId(productVo.getProductId());
                if (productImages != null && !productImages.isEmpty()) {
                    List<String> imageUrls = productImages.stream()
                        .map(ProductImage::getImageUrl)
                        .collect(java.util.stream.Collectors.toList());
                    productVo.setImageUrls(imageUrls);
                    
                    // 设置包含id和url的图片对象列表
                    List<com.dd.admin.business.product.dto.ImageDto> imageObjects = productImages.stream()
                        .map(img -> new com.dd.admin.business.product.dto.ImageDto(img.getImageId(), img.getImageUrl()))
                        .collect(java.util.stream.Collectors.toList());
                    productVo.setImageObjects(imageObjects);
                }
            }
        }
        
        return result;
    }

    @Override
    public List<ProductVo> selectProductList(ProductDto productDto) {
        return productMapper.selectProductList(productDto);
    }

    @Override
    public ProductVo selectProductById(String productId) {
        Product product = productMapper.selectById(productId);
        if (product == null) {
            throw new ApiException("商品不存在！");
        }
        ProductVo productVo = new ProductVo();
        BeanUtil.copyProperties(product, productVo);
        
        // 设置前端需要的字段
        productVo.setFirstPictureId(product.getFirstPictureId());
        productVo.setFirstPictureUrl(product.getFirstPictureUrl());
        
        // 兼容旧字段
        productVo.setMainImageId(product.getFirstPictureId());
        productVo.setMainImageUrl(product.getFirstPictureUrl());
        
        // 获取商品图片列表
        List<ProductImage> productImages = productImageService.getByProductId(productId);
        if (productImages != null && !productImages.isEmpty()) {
            List<String> imageUrls = productImages.stream()
                .map(ProductImage::getImageUrl)
                .collect(java.util.stream.Collectors.toList());
            productVo.setImageUrls(imageUrls);
            
            // 设置包含id和url的图片对象列表
            List<com.dd.admin.business.product.dto.ImageDto> imageObjects = productImages.stream()
                .map(img -> new com.dd.admin.business.product.dto.ImageDto(img.getImageId(), img.getImageUrl()))
                .collect(java.util.stream.Collectors.toList());
            productVo.setImageObjects(imageObjects);
        }
        
        return productVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addProduct(ProductAddDto productAddDto) {
        log.info("开始新增商品，商品名称: {}", productAddDto.getProductName());
        
        Product product = new Product();
        BeanUtil.copyProperties(productAddDto, product);
        
        // 处理首图ID和URL
        if (StrUtil.isNotBlank(productAddDto.getFirstPictureId())) {
            product.setFirstPictureId(productAddDto.getFirstPictureId());
            log.info("设置首图ID: {}", productAddDto.getFirstPictureId());
        }
        if (StrUtil.isNotBlank(productAddDto.getFirstPictureUrl())) {
            product.setFirstPictureUrl(productAddDto.getFirstPictureUrl());
            log.info("设置首图URL: {}", productAddDto.getFirstPictureUrl());
        }
        
        int result = productMapper.insert(product);
        if (result <= 0) {
            throw new ApiException("新增商品失败！");
        }
        
        // 处理商品图片
        List<String> imageUrls = null;
        List<com.dd.admin.business.product.dto.ImageDto> imageObjects = productAddDto.getImageObjects();
        
        if (imageObjects != null && !imageObjects.isEmpty()) {
            // 优先使用新的imageObjects格式
            log.info("开始保存商品图片对象，数量: {}", imageObjects.size());
            boolean saveResult = saveProductImagesFromObjects(product.getProductId(), imageObjects);
            if (!saveResult) {
                throw new ApiException("保存商品图片失败！");
            }
        } else {
            // 兼容旧的URL数组格式
            imageUrls = productAddDto.getImageUrls();
            if (imageUrls == null || imageUrls.isEmpty()) {
                imageUrls = productAddDto.getImages(); // 兼容字段
            }
            
            if (imageUrls != null && !imageUrls.isEmpty()) {
                log.info("开始保存商品图片，数量: {}", imageUrls.size());
                boolean saveResult = productImageService.saveProductImages(product.getProductId(), imageUrls);
                if (!saveResult) {
                    throw new ApiException("保存商品图片失败！");
                }
            }
        }
        
        log.info("商品新增成功，商品ID: {}", product.getProductId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProduct(ProductDto productDto) {
        log.info("开始修改商品，商品ID: {}", productDto.getProductId());
        
        Product product = new Product();
        BeanUtil.copyProperties(productDto, product);
        
        // 处理首图ID和URL
        if (StrUtil.isNotBlank(productDto.getFirstPictureId())) {
            product.setFirstPictureId(productDto.getFirstPictureId());
            log.info("更新首图ID: {}", productDto.getFirstPictureId());
        }
        if (StrUtil.isNotBlank(productDto.getFirstPictureUrl())) {
            product.setFirstPictureUrl(productDto.getFirstPictureUrl());
            log.info("更新首图URL: {}", productDto.getFirstPictureUrl());
        }
        
        int result = productMapper.updateById(product);
        if (result <= 0) {
            throw new ApiException("修改商品失败！");
        }
        
        // 处理商品图片更新
        List<String> imageUrls = null;
        List<com.dd.admin.business.product.dto.ImageDto> imageObjects = productDto.getImageObjects();
        
        // 先删除原有图片
        productImageService.deleteByProductId(productDto.getProductId());
        
        if (imageObjects != null && !imageObjects.isEmpty()) {
            // 优先使用新的imageObjects格式
            log.info("开始更新商品图片对象，商品ID: {}, 新图片数量: {}", productDto.getProductId(), imageObjects.size());
            boolean updateResult = saveProductImagesFromObjects(productDto.getProductId(), imageObjects);
            if (!updateResult) {
                throw new ApiException("更新商品图片失败！");
            }
        } else {
            // 兼容旧的URL数组格式
            imageUrls = productDto.getImageUrls();
            if (imageUrls == null || imageUrls.isEmpty()) {
                imageUrls = productDto.getImages(); // 兼容字段
            }
            
            if (imageUrls != null && !imageUrls.isEmpty()) {
                log.info("开始更新商品图片，商品ID: {}, 新图片数量: {}", productDto.getProductId(), imageUrls.size());
                boolean updateResult = productImageService.saveProductImages(productDto.getProductId(), imageUrls);
                if (!updateResult) {
                    throw new ApiException("更新商品图片失败！");
                }
            }
        }
        
        log.info("商品修改成功，商品ID: {}", productDto.getProductId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProduct(String productId) {
        int result = productMapper.deleteById(productId);
        if (result <= 0) {
            throw new ApiException("删除商品失败！");
        }
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProductStatus(String productId, Integer status) {
        Product product = new Product();
        product.setProductId(productId);
        product.setProductStatus(status);
        int result = productMapper.updateById(product);
        if (result <= 0) {
            throw new ApiException("更新商品状态失败！");
        }
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStockAndSales(String productId, Integer quantity) {
        log.info("开始更新商品库存和销量，商品ID: {}, 购买数量: {}", productId, quantity);
        
        // 先查询商品信息，检查库存是否充足
        Product product = productMapper.selectById(productId);
        if (product == null) {
            log.error("商品不存在，商品ID: {}", productId);
            throw new ApiException("商品不存在");
        }
        
        // 检查库存是否充足
        if (product.getProductStock() == null || product.getProductStock() < quantity) {
            log.error("库存不足，商品ID: {}, 商品名称: {}, 当前库存: {}, 购买数量: {}", 
                    productId, product.getProductName(), product.getProductStock(), quantity);
            throw new ApiException("商品【" + product.getProductName() + "】库存不足，当前库存: " + 
                    (product.getProductStock() != null ? product.getProductStock() : 0) + "件，需要: " + quantity + "件");
        }
        
        // 更新库存和销量
        Product updateProduct = new Product();
        updateProduct.setProductId(productId);
        updateProduct.setProductStock(product.getProductStock() - quantity);
        
        // 增加销量
        Long currentSales = product.getSalesCount() != null ? product.getSalesCount() : 0L;
        updateProduct.setSalesCount(currentSales + quantity);
        
        // 设置版本号用于乐观锁
        updateProduct.setVersion(product.getVersion());
        
        int result = productMapper.updateById(updateProduct);
        if (result <= 0) {
            log.error("更新商品库存和销量失败，可能是并发冲突，商品ID: {}", productId);
            throw new ApiException("更新商品库存和销量失败，请重试");
        }
        
        log.info("更新商品库存和销量成功，商品ID: {}, 剩余库存: {}, 新销量: {}", 
                productId, product.getProductStock() - quantity, currentSales + quantity);
        
        return true;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean restoreStock(String productId, Integer quantity) {
        log.info("开始恢复商品库存，商品ID: {}, 恢复数量: {}", productId, quantity);
        
        // 查询商品信息
        Product product = productMapper.selectById(productId);
        if (product == null) {
            log.error("商品不存在，商品ID: {}", productId);
            throw new ApiException("商品不存在");
        }
        
        // 恢复库存，减少销量
        Product updateProduct = new Product();
        updateProduct.setProductId(productId);
        updateProduct.setProductStock(product.getProductStock() + quantity);
        
        // 减少销量（但不能小于0）
        Long currentSales = product.getSalesCount() != null ? product.getSalesCount() : 0L;
        Long newSales = Math.max(0L, currentSales - quantity);
        updateProduct.setSalesCount(newSales);
        
        // 设置版本号用于乐观锁
        updateProduct.setVersion(product.getVersion());
        
        int result = productMapper.updateById(updateProduct);
        if (result <= 0) {
            log.error("恢复商品库存失败，可能是并发冲突，商品ID: {}", productId);
            throw new ApiException("恢复商品库存失败，请重试");
        }
        
        log.info("恢复商品库存成功，商品ID: {}, 新库存: {}, 新销量: {}", 
                productId, product.getProductStock() + quantity, newSales);
        
        return true;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addProductImage(ProductImage productImage) {
        log.info("添加商品图片，商品ID: {}, 图片URL: {}", productImage.getProductId(), productImage.getImageUrl());
        return productImageService.save(productImage);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteProductImage(String id) {
        log.info("删除商品图片，主键ID: {}", id);
        return productImageService.removeById(id);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean incrementViews(String productId) {
        log.info("增加商品浏览量，商品ID: {}", productId);
        
        try {
            // 先查询商品是否存在
            Product product = productMapper.selectById(productId);
            if (product == null) {
                log.warn("商品不存在，无法增加浏览量，商品ID: {}", productId);
                return false;
            }
            
            // 增加浏览量
            Product updateProduct = new Product();
            updateProduct.setProductId(productId);
            Long currentViews = product.getProductViews() != null ? product.getProductViews() : 0L;
            updateProduct.setProductViews(currentViews + 1);
            
            int result = productMapper.updateById(updateProduct);
            if (result > 0) {
                log.info("商品浏览量增加成功，商品ID: {}, 新浏览量: {}", productId, currentViews + 1);
                return true;
            } else {
                log.warn("商品浏览量增加失败，商品ID: {}", productId);
                return false;
            }
        } catch (Exception e) {
            log.error("增加商品浏览量时发生异常，商品ID: {}", productId, e);
            return false;
        }
    }
    
    @Override
    public void checkStock(String productId, Integer quantity) {
        try {
            Product product = this.getById(productId);
            if (product == null) {
                throw new ApiException("商品不存在，商品ID: " + productId);
            }
            
            if (product.getProductStock() < quantity) {
                log.warn("商品库存不足，商品名称: {}, 商品ID: {}, 当前库存: {}, 需要数量: {}", 
                        product.getProductName(), productId, product.getProductStock(), quantity);
                throw new ApiException("商品【" + product.getProductName() + "】库存不足，当前库存: " + product.getProductStock() + "，需要数量: " + quantity);
            }
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("检查商品库存失败，商品ID: {}", productId, e);
            throw new ApiException("检查商品库存失败");
        }
    }
    
    /**
     * 从图片对象数组保存商品图片
     * @param productId 商品ID
     * @param imageObjects 图片对象列表
     * @return 是否成功
     */
    private boolean saveProductImagesFromObjects(String productId, List<com.dd.admin.business.product.dto.ImageDto> imageObjects) {
        try {
            List<ProductImage> productImages = new ArrayList<>();
            for (int i = 0; i < imageObjects.size(); i++) {
                com.dd.admin.business.product.dto.ImageDto imageDto = imageObjects.get(i);
                ProductImage productImage = new ProductImage();
                productImage.setProductId(productId);
                productImage.setImageId(imageDto.getId());
                productImage.setImageUrl(imageDto.getUrl());
                productImage.setImageSort(i + 1);
                productImages.add(productImage);
            }
            
            return productImageService.saveBatch(productImages);
        } catch (Exception e) {
            log.error("保存商品图片对象失败，商品ID: {}, 错误信息: {}", productId, e.getMessage(), e);
            return false;
        }
    }
}






