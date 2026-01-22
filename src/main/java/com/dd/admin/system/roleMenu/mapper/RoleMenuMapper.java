package com.dd.admin.system.roleMenu.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.system.roleMenu.entity.RoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.admin.system.roleMenu.domain.RoleMenuVo;
import com.dd.admin.system.roleMenu.domain.RoleMenuDto;

import java.util.List;

/**
 * <p>
 * 角色菜单 Mapper 接口
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2022-01-04
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    IPage<RoleMenuVo> selectRoleMenuPage(Page<RoleMenuVo> page, @Param("roleMenuDto") RoleMenuDto roleMenuDto);

    List<RoleMenuVo> selectRoleMenuList(@Param("roleMenuDto") RoleMenuDto roleMenuDto);

    List<String> selectAllByRoleId(@Param("roleId")String roleId);

    //根据角色id删除所有的菜单
    Integer deleteRoleMenuByRoleId(@Param("roleId") String roleId);

}
