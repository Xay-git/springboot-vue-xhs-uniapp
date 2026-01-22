package com.dd.admin.system.role.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.common.model.PageFactory;
import com.dd.admin.system.role.entity.Role;
import com.dd.admin.system.role.mapper.RoleMapper;
import com.dd.admin.system.role.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.dd.admin.system.role.domain.RoleVo;
import com.dd.admin.system.role.domain.RoleDto;
import java.util.List;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2022-01-04
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public IPage<RoleVo> selectRolePage(RoleDto roleDto) {
        Page page = PageFactory.defaultPage();
        return baseMapper.selectRolePage(page,roleDto);
    }

    @Override
    public List<RoleVo> selectRoleList(RoleDto roleDto) {
        return baseMapper.selectRoleList(roleDto);
    }
}
