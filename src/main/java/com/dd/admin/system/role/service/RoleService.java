package com.dd.admin.system.role.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.system.role.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.admin.system.role.domain.RoleVo;
import com.dd.admin.system.role.domain.RoleDto;
import java.util.List;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2022-01-04
 */
public interface RoleService extends IService<Role> {

    //角色-分页列表
    IPage<RoleVo> selectRolePage(RoleDto roleDto);

    //角色-列表
    List<RoleVo> selectRoleList(RoleDto roleDto);

}
