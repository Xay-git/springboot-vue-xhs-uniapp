package com.dd.admin.system.userRole.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.admin.system.userRole.domain.UserRoleDto;
import com.dd.admin.system.userRole.domain.UserRoleVo;
import com.dd.admin.system.userRole.entity.UserRole;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2022-01-04
 */
public interface UserRoleService extends IService<UserRole> {

    //-分页列表
    IPage<UserRoleVo> selectUserRolePage(UserRoleDto userRoleDto);

    //-列表
    List<UserRoleVo> selectUserRoleList(UserRoleDto userRoleDto);

    Integer deleteUserRoleByUserId(String userId);

    List<String> selectAllByUserId(String userId);

}
