package com.dd.admin.business.address.controller;

import com.dd.admin.business.address.entity.Address;
import com.dd.admin.business.address.service.AddressService;
import com.dd.admin.common.model.result.ResultBean;
import com.dd.admin.common.exception.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 用户地址 前端控制器
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-09-11
 */
@RestController
@Api(tags = "用户地址接口")
public class AddressController {
    @Autowired
    private AddressService addressService;
    
    @GetMapping("/admin/address/list")
    @ApiOperation("获取用户地址列表")
    public ResultBean<List<Address>> list(@ApiParam("用户ID") @RequestParam String authorId) {
        List<Address> addressList = addressService.getUserAddressList(authorId);
        return ResultBean.success(addressList);
    }
    
    @PostMapping("/admin/address/add")
    @ApiOperation("添加地址")
    public ResultBean<?> add(@RequestBody Address address) {
        boolean result = addressService.addAddress(address);
        if (!result) {
            throw new ApiException("添加失败");
        }
        return ResultBean.success();
    }
    
    @PostMapping("/admin/address/update")
    @ApiOperation("更新地址")
    public ResultBean<?> update(@RequestBody Address address) {
        boolean result = addressService.updateAddress(address);
        if (!result) {
            throw new ApiException("更新失败");
        }
        return ResultBean.success();
    }
    
    @PostMapping("/admin/address/default")
    @ApiOperation("设置默认地址")
    public ResultBean<?> setDefault(
            @ApiParam("地址ID") @RequestParam String addressId,
            @ApiParam("用户ID") @RequestParam String authorId) {
        boolean result = addressService.setDefaultAddress(addressId, authorId);
        if (!result) {
            throw new ApiException("设置默认地址失败");
        }
        return ResultBean.success();
    }
    
    @GetMapping("/admin/address/delete/{id}")
    @ApiOperation("删除地址")
    public ResultBean<?> delete(@PathVariable("id") String addressId) {
        boolean result = addressService.deleteAddress(addressId);
        if (!result) {
            throw new ApiException("删除失败");
        }
        return ResultBean.success();
    }
}






