package com.dd.admin.business.address.service.impl;

import com.dd.admin.business.address.entity.Address;
import com.dd.admin.business.address.mapper.AddressMapper;
import com.dd.admin.business.address.service.AddressService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 用户地址 服务实现类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-09-11
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {

    @Override
    public List<Address> getUserAddressList(String authorId) {
        LambdaQueryWrapper<Address> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Address::getAuthorId, authorId);
        wrapper.orderByDesc(Address::getIsDefault, Address::getCreateTime);
        return this.list(wrapper);
    }
    
    @Override
    @Transactional
    public boolean addAddress(Address address) {
        // 如果是默认地址，将其他地址设置为非默认
        if (address.getIsDefault() != null && address.getIsDefault() == 1) {
            this.resetDefaultAddress(address.getAuthorId());
        }
        return this.save(address);
    }
    
    @Override
    @Transactional
    public boolean updateAddress(Address address) {
        // 如果是默认地址，将其他地址设置为非默认
        if (address.getIsDefault() != null && address.getIsDefault() == 1) {
            this.resetDefaultAddress(address.getAuthorId());
        }
        return this.updateById(address);
    }
    
    @Override
    @Transactional
    public boolean setDefaultAddress(String addressId, String authorId) {
        // 将其他地址设置为非默认
        this.resetDefaultAddress(authorId);
        
        // 设置当前地址为默认
        LambdaQueryWrapper<Address> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Address::getAddressId, addressId);
        wrapper.eq(Address::getAuthorId, authorId);
        
        Address address = new Address();
        address.setIsDefault(1);
        return this.update(address, wrapper);
    }
    
    @Override
    @Transactional
    public boolean deleteAddress(String addressId) {
        return this.removeById(addressId);
    }
    
    /**
     * 重置用户的默认地址
     * @param authorId 用户ID
     */
    private void resetDefaultAddress(String authorId) {
        LambdaQueryWrapper<Address> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Address::getAuthorId, authorId);
        wrapper.eq(Address::getIsDefault, 1);
        
        Address address = new Address();
        address.setIsDefault(0);
        this.update(address, wrapper);
    }
}






