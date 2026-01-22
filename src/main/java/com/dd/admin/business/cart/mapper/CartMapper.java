package com.dd.admin.business.cart.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.business.cart.dto.CartDto;
import com.dd.admin.business.cart.entity.Cart;
import com.dd.admin.business.cart.vo.CartVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 购物车 Mapper 接口
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-09-11
 */
@Mapper
public interface CartMapper extends BaseMapper<Cart> {

    /**
     * 分页查询购物车
     *
     * @param page 分页参数
     * @param cartDto 查询条件
     * @return 分页结果
     */
    IPage<CartVo> selectCartPage(IPage<CartVo> page, @Param("cartDto") CartDto cartDto);

    /**
     * 查询购物车列表
     *
     * @param cartDto 查询条件
     * @return 购物车列表
     */
    List<CartVo> selectCartList(@Param("cartDto") CartDto cartDto);

    /**
     * 根据ID查询购物车详情
     *
     * @param cartId 购物车ID
     * @return 购物车详情
     */
    CartVo selectCartById(@Param("cartId") String cartId);

    /**
     * 获取用户购物车列表
     *
     * @param authorId 用户ID
     * @return 购物车列表
     */
    List<Cart> getUserCartList(@Param("authorId") String authorId);

    /**
     * 更新购物车数量
     *
     * @param cartId 购物车ID
     * @param quantity 数量
     * @return 更新结果
     */
    int updateCartQuantity(@Param("cartId") String cartId, @Param("quantity") Integer quantity);

    /**
     * 更新购物车选中状态
     *
     * @param cartId 购物车ID
     * @param selected 选中状态
     * @return 更新结果
     */
    int updateCartSelected(@Param("cartId") String cartId, @Param("selected") Integer selected);

    /**
     * 根据用户ID和商品ID查询购物车记录
     *
     * @param authorId 用户ID
     * @param productId 商品ID
     * @return 购物车记录
     */
    Cart selectByAuthorIdAndProductId(@Param("authorId") String authorId, @Param("productId") String productId);
    
    /**
     * 根据用户ID和商品ID删除购物车记录
     *
     * @param authorId 用户ID
     * @param productId 商品ID
     * @return 影响行数
     */
    int deleteByAuthorIdAndProductId(@Param("authorId") String authorId, @Param("productId") String productId);

}






