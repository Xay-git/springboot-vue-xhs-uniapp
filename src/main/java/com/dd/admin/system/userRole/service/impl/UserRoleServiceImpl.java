package com.dd.admin.system.userRole.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.common.model.PageFactory;
import com.dd.admin.system.userRole.entity.UserRole;
import com.dd.admin.system.userRole.mapper.UserRoleMapper;
import com.dd.admin.system.userRole.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.dd.admin.system.userRole.domain.UserRoleVo;
import com.dd.admin.system.userRole.domain.UserRoleDto;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2022-01-04
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Override
    public IPage<UserRoleVo> selectUserRolePage(UserRoleDto userRoleDto) {
        Page page = PageFactory.defaultPage();
        return baseMapper.selectUserRolePage(page,userRoleDto);
    }

    @Override
    public List<UserRoleVo> selectUserRoleList(UserRoleDto userRoleDto) {
        return baseMapper.selectUserRoleList(userRoleDto);
    }

    @Override
    public Integer deleteUserRoleByUserId(String userId) {
        return baseMapper.deleteUserRoleByUserId(userId);
    }

    @Override
    public List<String> selectAllByUserId(String userId) {
        return baseMapper.selectAllByUserId(userId);
    }
}
