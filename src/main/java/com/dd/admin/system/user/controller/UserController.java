package com.dd.admin.system.user.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.common.exception.ApiException;
import com.dd.admin.common.model.UpdateGroup;
import com.dd.admin.common.model.result.ResultBean;
import com.dd.admin.common.security.SecurityUtil;
import com.dd.admin.common.utils.StringUtil;
import com.dd.admin.system.role.entity.Role;
import com.dd.admin.system.role.service.RoleService;
import com.dd.admin.system.user.domain.UpdatePasswordDto;
import com.dd.admin.system.user.domain.UserDto;
import com.dd.admin.system.user.domain.UserVo;
import com.dd.admin.system.user.entity.User;
import com.dd.admin.system.user.service.UserService;
import com.dd.admin.system.userRole.service.UserRoleService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

import static com.dd.admin.common.exception.enums.SystemExceptionEnum.USER_PASSWORD_ERROR;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2022-01-05
 */
@Api(tags = "用户")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    RoleService roleService;

    @Autowired
    PasswordEncoder encoder;

    @ApiOperation(value = "用户-分页列表")
    @ApiOperationSupport(order = 1)
    @GetMapping("/admin/user/page")
    public ResultBean<IPage<UserVo>> page(UserDto userDto) {
        IPage<UserVo> pageInfo = userService.selectUserPage(userDto);
        pageInfo.getRecords().stream().forEach(userVo -> {
            List<String> roles = userRoleService.selectAllByUserId(userVo.getUserId());
            List<String> roleNames = roles.stream().map(roleId->{
                Role role = roleService.getById(roleId);
                if(role!=null){ return role.getRoleName(); }
                return "";
            }).collect(Collectors.toList());
            userVo.setRoleNames(roleNames);
            userVo.setRoles(roles);
        });
        return ResultBean.success(pageInfo);
    }

    @ApiOperation(value = "用户-列表")
    @ApiOperationSupport(order = 2)
    @GetMapping("/admin/user/list")
    public ResultBean<List<UserVo>> list(UserDto userDto) {
        List<UserVo> list = userService.selectUserList(userDto);
        return ResultBean.success(list);
    }

    @ApiOperation(value = "用户-添加")
    @ApiOperationSupport(order = 3)
    @PostMapping("/admin/user/add")
    @Transactional
    public ResultBean<User> add(@RequestBody @Validated UserDto userDto) {
        User user = userService.addUser(userDto);
        userService.setUserRole(userDto.getRoles(),user.getUserId());
        return ResultBean.success(user);
    }

    @ApiOperation(value = "用户-查询")
    @ApiOperationSupport(order = 4)
    @GetMapping("/admin/user/{userId}")
    public ResultBean<UserVo> get(@PathVariable @NotBlank String userId) {
        User user = userService.getById(userId);
        UserVo userVo = BeanUtil.copyProperties(user,UserVo.class);
        return ResultBean.success(userVo);
    }

    @ApiOperation(value = "用户-修改")
    @ApiOperationSupport(order = 5)
    @PostMapping("/admin/user/update")
    public ResultBean<User> update(@RequestBody @Validated(UpdateGroup.class) UserDto userDto) {
        User user = BeanUtil.copyProperties(userDto, User.class);
        userService.updateById(user);
        userService.setUserRole(userDto.getRoles(),user.getUserId());
        return ResultBean.success(user);
    }

    @ApiOperation(value = "用户-删除")
    @ApiOperationSupport(order = 6)
    @GetMapping("/admin/user/delete/{userId}")
    public ResultBean<User> delete(@PathVariable @NotBlank String userId) {
        Boolean b = userService.removeById(userId);
        return ResultBean.success(b);
    }
    @ApiOperation(value = "用户重置密码")
    @ApiOperationSupport(order = 7)
    @GetMapping("/admin/user/resetPass/{userId}")
    public ResultBean resetPass(@PathVariable @NotBlank String userId) {
        User user = userService.getById(userId);
        user.setPassword(userService.genDefaultPassword());
        userService.updateById(user);
        return ResultBean.success();
    }

    @ApiOperation(value = "用户重置密码")
    @ApiOperationSupport(order = 7)
    @PostMapping("/admin/user/updatePassword")
    public ResultBean updatePassword(@RequestBody UpdatePasswordDto updatePasswordDto) {
        User user = userService.getById(SecurityUtil.getLoginUser().getUserId());
        Boolean b = encoder.matches(updatePasswordDto.getPassword(),user.getPassword());
        if(!b){
            throw new ApiException(USER_PASSWORD_ERROR);
        }
        user.setPassword(new BCryptPasswordEncoder().encode(updatePasswordDto.getNewPassword()));
        userService.updateById(user);
        return ResultBean.success();
    }

}
