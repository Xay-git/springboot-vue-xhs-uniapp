package com.dd.admin.system.role.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.system.role.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.admin.system.role.domain.RoleVo;
import com.dd.admin.system.role.domain.RoleDto;

import java.util.List;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2022-01-04
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    IPage<RoleVo> selectRolePage(Page<RoleVo> page, @Param("roleDto") RoleDto roleDto);

    List<RoleVo> selectRoleList(@Param("roleDto") RoleDto roleDto);
}
