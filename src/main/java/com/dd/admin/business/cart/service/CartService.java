package com.dd.admin.business.cart.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.business.cart.dto.CartAddDto;
import com.dd.admin.business.cart.dto.CartDto;
import com.dd.admin.business.cart.vo.CartVo;
import com.dd.admin.business.cart.entity.Cart;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 购物车服务接口
 *
 * @author system
 * @date 2024-01-15
 */
public interface CartService extends IService<Cart> {

    /**
     * 分页查询购物车
     *
     * @param cartDto 查询条件
     * @return 分页结果
     */
    IPage<CartVo> selectCartPage(CartDto cartDto);

    /**
     * 查询购物车列表
     *
     * @param cartDto 查询条件
     * @return 购物车列表
     */
    List<CartVo> selectCartList(CartDto cartDto);

    /**
     * 根据ID查询购物车详情
     *
     * @param cartId 购物车ID
     * @return 购物车详情
     */
    CartVo selectCartById(String cartId);

    /**
     * 新增购物车
     *
     * @param cartAddDto 新增数据
     */
    void addCart(CartAddDto cartAddDto);

    /**
     * 修改购物车
     *
     * @param cartDto 修改数据
     */
    void updateCart(CartDto cartDto);

    /**
     * 删除购物车
     *
     * @param cartId 购物车ID
     */
    void deleteCart(String cartId);
    
    /**
     * 获取用户购物车列表
     * @param authorId 用户ID
     * @return 购物车列表
     */
    List<Cart> getUserCartList(String authorId);
    
    /**
     * 添加商品到购物车
     * @param cart 购物车信息
     * @return 是否成功
     */
    boolean addToCart(Cart cart);
    
    /**
     * 更新购物车商品数量
     * @param cartId 购物车ID
     * @param quantity 数量
     * @return 是否成功
     */
    boolean updateCartQuantity(String cartId, Integer quantity);
    
    /**
     * 更新购物车商品选中状态
     * @param cartId 购物车ID
     * @param selected 选中状态
     * @return 是否成功
     */
    boolean updateCartSelected(String cartId, Integer selected);

    /**
     * 根据用户ID和商品ID查询购物车记录
     * @param authorId 用户ID
     * @param productId 商品ID
     * @return 购物车记录
     */
    Cart selectByAuthorIdAndProductId(String authorId, String productId);
    
    /**
     * 根据用户ID和商品ID删除购物车记录
     * @param authorId 用户ID
     * @param productId 商品ID
     * @return 是否成功
     */
    boolean deleteByAuthorIdAndProductId(String authorId, String productId);
}