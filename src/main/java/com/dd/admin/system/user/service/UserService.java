package com.dd.admin.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.admin.system.user.domain.UserDto;
import com.dd.admin.system.user.domain.UserVo;
import com.dd.admin.system.user.entity.User;

import java.util.List;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2022-01-05
 */
public interface UserService extends IService<User> {

    //用户-分页列表
    IPage<UserVo> selectUserPage(UserDto userDto);

    //用户-列表
    List<UserVo> selectUserList(UserDto userDto);

    //根据用户名查询
    UserVo selectOneByUserName(String userName);

    //添加用户
    User addUser(UserDto userDto);

    //根据用户id设置用户角色信息
    void setUserRole(List<String> roleList,String userId);

    //创建默认密码
    String genDefaultPassword();
}
