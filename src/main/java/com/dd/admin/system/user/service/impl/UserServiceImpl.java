package com.dd.admin.system.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dd.admin.common.exception.ApiException;
import com.dd.admin.common.model.PageFactory;
import com.dd.admin.system.dept.entity.Dept;
import com.dd.admin.system.dept.service.DeptService;
import com.dd.admin.system.user.domain.UserDto;
import com.dd.admin.system.user.domain.UserVo;
import com.dd.admin.system.user.entity.User;
import com.dd.admin.system.user.mapper.UserMapper;
import com.dd.admin.system.user.service.UserService;
import com.dd.admin.system.userRole.entity.UserRole;
import com.dd.admin.system.userRole.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.dd.admin.common.consts.SystemConst.DEFAULT_PASSWORD;
import static com.dd.admin.common.exception.enums.SystemExceptionEnum.USERNAME_ALREADY_EXIST;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2022-01-05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    DeptService deptService;

    @Autowired
    UserRoleService userRoleService;

    @Override
    public IPage<UserVo> selectUserPage(UserDto userDto) {
        Page page = PageFactory.defaultPage();
        return baseMapper.selectUserPage(page,userDto);
    }

    @Override
    public List<UserVo> selectUserList(UserDto userDto) {
        return baseMapper.selectUserList(userDto);
    }

    @Override
    public UserVo selectOneByUserName(String userName) {
        return baseMapper.selectOneByUserName(userName);
    }

    @Override
    public User addUser(UserDto userDto) {
        String username = userDto.getUserName();
        UserVo userVo = selectOneByUserName(username);
        if(userVo!=null){
            throw new ApiException(USERNAME_ALREADY_EXIST);
        }
        User user = BeanUtil.copyProperties(userDto,User.class);
        user.setPassword(new BCryptPasswordEncoder().encode(DEFAULT_PASSWORD));

        Dept dept = deptService.getById(user.getDeptId());
        user.setDeptName(dept.getDeptName());
        save(user);
        return user;
    }

    @Override
    public void setUserRole(List<String> roleList, String userId) {
        //删除用户的角色信息
        userRoleService.deleteUserRoleByUserId(userId);
        //批量添加新的
        List<UserRole> userRoleList = roleList.stream().map(roleId -> {
            return new UserRole().setRoleId(roleId).setUserId(userId);
        }).collect(Collectors.toList());
        userRoleService.saveBatch(userRoleList);
    }

    @Override
    public String genDefaultPassword() {
        return new BCryptPasswordEncoder().encode(DEFAULT_PASSWORD);
    }
}
