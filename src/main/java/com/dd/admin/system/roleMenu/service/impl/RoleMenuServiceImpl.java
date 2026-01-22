package com.dd.admin.system.roleMenu.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.common.model.PageFactory;
import com.dd.admin.system.roleMenu.entity.RoleMenu;
import com.dd.admin.system.roleMenu.mapper.RoleMenuMapper;
import com.dd.admin.system.roleMenu.service.RoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.dd.admin.system.roleMenu.domain.RoleMenuVo;
import com.dd.admin.system.roleMenu.domain.RoleMenuDto;
import java.util.List;

/**
 * <p>
 * 角色菜单 服务实现类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2022-01-04
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    @Override
    public IPage<RoleMenuVo> selectRoleMenuPage(RoleMenuDto roleMenuDto) {
        Page page = PageFactory.defaultPage();
        return baseMapper.selectRoleMenuPage(page,roleMenuDto);
    }

    @Override
    public List<RoleMenuVo> selectRoleMenuList(RoleMenuDto roleMenuDto) {
        return baseMapper.selectRoleMenuList(roleMenuDto);
    }

    @Override
    public List<String> selectAllByRoleId(String roleId) {
        return baseMapper.selectAllByRoleId(roleId);
    }

    @Override
    public Integer deleteRoleMenuByRoleId(String roleId) {
        return baseMapper.deleteRoleMenuByRoleId(roleId);
    }
}
