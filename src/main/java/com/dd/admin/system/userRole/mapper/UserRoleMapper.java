package com.dd.admin.system.userRole.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.system.userRole.entity.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.admin.system.userRole.domain.UserRoleVo;
import com.dd.admin.system.userRole.domain.UserRoleDto;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2022-01-04
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

    IPage<UserRoleVo> selectUserRolePage(Page<UserRoleVo> page, @Param("userRoleDto") UserRoleDto userRoleDto);

    List<UserRoleVo> selectUserRoleList(@Param("userRoleDto") UserRoleDto userRoleDto);

    //根据用户id删除用户角色信息
    Integer deleteUserRoleByUserId(@Param("userId")String userId);

    List<String> selectAllByUserId(@Param("userId") String userId);

}
