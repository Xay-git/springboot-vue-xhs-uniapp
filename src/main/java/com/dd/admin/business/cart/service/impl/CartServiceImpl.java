package com.dd.admin.business.cart.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dd.admin.business.cart.dto.CartAddDto;
import com.dd.admin.business.cart.dto.CartDto;
import com.dd.admin.business.cart.entity.Cart;
import com.dd.admin.business.cart.mapper.CartMapper;
import com.dd.admin.business.cart.service.CartService;
import com.dd.admin.business.cart.vo.CartVo;
import com.dd.admin.common.model.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 购物车 服务实现类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-09-11
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Override
    public IPage<CartVo> selectCartPage(CartDto cartDto) {
        IPage<CartVo> page = PageFactory.defaultPage();
        return cartMapper.selectCartPage(page, cartDto);
    }

    @Override
    public List<CartVo> selectCartList(CartDto cartDto) {
        return cartMapper.selectCartList(cartDto);
    }

    @Override
    public CartVo selectCartById(String cartId) {
        Cart cart = cartMapper.selectById(cartId);
        if (cart == null) {
            return null;
        }
        CartVo cartVo = new CartVo();
        BeanUtil.copyProperties(cart, cartVo);
        return cartVo;
    }

    @Override
    @Transactional
    public void addCart(CartAddDto cartAddDto) {
        // 检查用户购物车中是否已存在相同商品
        Cart existingCart = cartMapper.selectByAuthorIdAndProductId(cartAddDto.getAuthorId(), cartAddDto.getProductId());
        
        if (existingCart != null) {
            // 如果已存在，累计数量并更新价格为最新价格
            Integer newQuantity = existingCart.getQuantity() + cartAddDto.getQuantity();
            // 更新数量和价格
            existingCart.setQuantity(newQuantity);
            existingCart.setProductPrice(cartAddDto.getPrice()); // 更新为最新价格
            existingCart.setProductName(cartAddDto.getProductName()); // 更新商品名称
            existingCart.setProductImage(cartAddDto.getProductImage()); // 更新商品图片
            cartMapper.updateById(existingCart);
        } else {
            // 如果不存在，创建新记录
            Cart cart = new Cart();
            BeanUtil.copyProperties(cartAddDto, cart);
            // 手动设置价格字段，因为字段名不匹配（DTO中是price，实体中是productPrice）
            cart.setProductPrice(cartAddDto.getPrice());
            cartMapper.insert(cart);
        }
    }

    @Override
    @Transactional
    public void updateCart(CartDto cartDto) {
        Cart cart = new Cart();
        BeanUtil.copyProperties(cartDto, cart);
        cartMapper.updateById(cart);
    }

    @Override
    @Transactional
    public void deleteCart(String cartId) {
        cartMapper.deleteById(cartId);
    }
    
    @Override
    public List<Cart> getUserCartList(String authorId) {
        return cartMapper.getUserCartList(authorId);
    }
    
    @Override
    @Transactional
    public boolean addToCart(Cart cart) {
        return cartMapper.insert(cart) > 0;
    }
    
    @Override
    @Transactional
    public boolean updateCartQuantity(String cartId, Integer quantity) {
        return cartMapper.updateCartQuantity(cartId, quantity) > 0;
    }
    
    @Override
    @Transactional
    public boolean updateCartSelected(String cartId, Integer selected) {
        return cartMapper.updateCartSelected(cartId, selected) > 0;
    }
    
    @Override
    public Cart selectByAuthorIdAndProductId(String authorId, String productId) {
        return cartMapper.selectByAuthorIdAndProductId(authorId, productId);
    }
    
    @Override
    @Transactional
    public boolean deleteByAuthorIdAndProductId(String authorId, String productId) {
        return cartMapper.deleteByAuthorIdAndProductId(authorId, productId) > 0;
    }
    
}






