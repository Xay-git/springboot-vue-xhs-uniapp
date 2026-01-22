package com.dd.admin.system.roleMenu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.system.roleMenu.entity.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.admin.system.roleMenu.domain.RoleMenuVo;
import com.dd.admin.system.roleMenu.domain.RoleMenuDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色菜单 服务类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2022-01-04
 */
public interface RoleMenuService extends IService<RoleMenu> {

    //角色菜单-分页列表
    IPage<RoleMenuVo> selectRoleMenuPage(RoleMenuDto roleMenuDto);

    //角色菜单-列表
    List<RoleMenuVo> selectRoleMenuList(RoleMenuDto roleMenuDto);

    //根据角色id查询 权限
    List<String> selectAllByRoleId(String roleId);

    //根据角色id删除所有的菜单
    Integer deleteRoleMenuByRoleId(String roleId);


}
