package com.dd.admin.business.cart.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.business.cart.service.CartService;
import com.dd.admin.business.cart.vo.CartVo;
import com.dd.admin.business.cart.dto.CartDto;
import com.dd.admin.business.cart.dto.CartAddDto;
import com.dd.admin.common.aop.operationLog.aop.OperLog;
import com.dd.admin.common.aop.operationLog.aop.OperType;
import com.dd.admin.common.model.result.ResultBean;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * <p>
 * 购物车 前端控制器
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-09-11
 */
@RestController
@Api(tags = "购物车")
public class CartController {
    @Autowired
    private CartService cartService;
    
    @GetMapping("/admin/cart/page")
    @ApiOperation("分页查询购物车")
    @ApiOperationSupport(order = 1)
    @OperLog(operModule = "购物车", operType = OperType.QUERY, operDesc = "分页查询购物车")
    public ResultBean<IPage<CartVo>> selectCartPage(CartDto cartDto) {
        IPage<CartVo> page = cartService.selectCartPage(cartDto);
        return ResultBean.success(page);
    }

    @GetMapping("/admin/cart/list")
    @ApiOperation("查询购物车列表")
    @ApiOperationSupport(order = 2)
    @OperLog(operModule = "购物车", operType = OperType.QUERY, operDesc = "查询购物车列表")
    public ResultBean<List<CartVo>> selectCartList(CartDto cartDto) {
        List<CartVo> list = cartService.selectCartList(cartDto);
        return ResultBean.success(list);
    }

    @GetMapping("/admin/cart/detail/{cartId}")
    @ApiOperation("查询购物车详情")
    @ApiOperationSupport(order = 3)
    @OperLog(operModule = "购物车", operType = OperType.QUERY, operDesc = "查询购物车详情")
    public ResultBean<CartVo> selectCartById(@PathVariable @NotBlank String cartId) {
        CartVo cartVo = cartService.selectCartById(cartId);
        return ResultBean.success(cartVo);
    }

    @PostMapping("/admin/cart/add")
    @ApiOperation("新增购物车")
    @ApiOperationSupport(order = 4)
    @OperLog(operModule = "购物车", operType = OperType.ADD, operDesc = "新增购物车")
    public ResultBean<String> addCart(@RequestBody @Validated CartAddDto cartAddDto) {
        cartService.addCart(cartAddDto);
        return ResultBean.success("新增成功");
    }
    
    @PostMapping("/admin/cart/update")
    @ApiOperation("修改购物车")
    @ApiOperationSupport(order = 5)
    @OperLog(operModule = "购物车", operType = OperType.EDIT, operDesc = "修改购物车")
    public ResultBean<String> updateCart(@RequestBody @Validated CartDto cartDto) {
        cartService.updateCart(cartDto);
        return ResultBean.success("修改成功");
    }

    @PostMapping("/admin/cart/delete/{cartId}")
    @ApiOperation("删除购物车")
    @ApiOperationSupport(order = 6)
    @OperLog(operModule = "购物车", operType = OperType.REMOVE, operDesc = "删除购物车")
    public ResultBean<String> deleteCart(@PathVariable @NotBlank String cartId) {
        cartService.deleteCart(cartId);
        return ResultBean.success("删除成功");
    }
}

