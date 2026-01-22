package com.dd.admin.business.product.mapper;

import com.dd.admin.business.product.dto.ProductDto;
import com.dd.admin.business.product.vo.ProductVo;
import com.dd.admin.business.product.entity.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品 Mapper 接口
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-09-11
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {

    /**
     * 分页查询商品
     *
     * @param page 分页参数
     * @param productDto 查询条件
     * @return 分页结果
     */
    IPage<ProductVo> selectProductPage(IPage<ProductVo> page, @Param("productDto") ProductDto productDto);

    /**
     * 查询商品列表
     *
     * @param productDto 查询条件
     * @return 商品列表
     */
    List<ProductVo> selectProductList(@Param("productDto") ProductDto productDto);



}






