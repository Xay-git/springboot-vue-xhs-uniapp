package com.dd.admin.system.user.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.system.user.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.admin.system.user.domain.UserVo;
import com.dd.admin.system.user.domain.UserDto;

import java.util.List;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2022-01-05
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    IPage<UserVo> selectUserPage(Page<UserVo> page, @Param("userDto") UserDto userDto);

    List<UserVo> selectUserList(@Param("userDto") UserDto userDto);

    UserVo selectOneByUserName(@Param("userName") String userName);

}
