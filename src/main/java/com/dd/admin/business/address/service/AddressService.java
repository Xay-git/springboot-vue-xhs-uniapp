package com.dd.admin.business.address.service;

import com.dd.admin.business.address.entity.Address;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户地址 服务类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-09-11
 */
public interface AddressService extends IService<Address> {

    /**
     * 获取用户地址列表
     * @param authorId 用户ID
     * @return 地址列表
     */
    List<Address> getUserAddressList(String authorId);
    
    /**
     * 添加地址
     * @param address 地址信息
     * @return 是否成功
     */
    boolean addAddress(Address address);
    
    /**
     * 更新地址
     * @param address 地址信息
     * @return 是否成功
     */
    boolean updateAddress(Address address);
    
    /**
     * 设置默认地址
     * @param addressId 地址ID
     * @param authorId 用户ID
     * @return 是否成功
     */
    boolean setDefaultAddress(String addressId, String authorId);
    
    /**
     * 删除地址
     * @param addressId 地址ID
     * @return 是否成功
     */
    boolean deleteAddress(String addressId);
}
