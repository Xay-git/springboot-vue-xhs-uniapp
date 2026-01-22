package com.dd.admin.system.userRole.controller;

import cn.hutool.core.bean.BeanUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.dd.admin.common.model.UpdateGroup;
import com.dd.admin.common.model.result.ResultBean;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotBlank;
import com.dd.admin.system.userRole.entity.UserRole;
import com.dd.admin.system.userRole.domain.UserRoleVo;
import com.dd.admin.system.userRole.domain.UserRoleDto;
import com.dd.admin.system.userRole.service.UserRoleService;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2022-01-04
 */
@Api(tags = "用户角色关联")
@RestController
public class UserRoleController {

    @Autowired
    UserRoleService userRoleService;

    @ApiOperation(value = "-分页列表")
    @ApiOperationSupport(order = 1)
    @GetMapping("/admin/userRole/page")
    public ResultBean<IPage<UserRoleVo>> page(UserRoleDto userRoleDto) {
        IPage<UserRoleVo> pageInfo = userRoleService.selectUserRolePage(userRoleDto);
        return ResultBean.success(pageInfo);
    }

    @ApiOperation(value = "-列表")
    @ApiOperationSupport(order = 2)
    @GetMapping("/admin/userRole/list")
    public ResultBean<List<UserRoleVo>> list(UserRoleDto userRoleDto) {
        List<UserRoleVo> list = userRoleService.selectUserRoleList(userRoleDto);
        return ResultBean.success(list);
    }

    @ApiOperation(value = "-添加")
    @ApiOperationSupport(order = 3)
    @PostMapping("/admin/userRole/add")
    public ResultBean<UserRole> add(@RequestBody @Validated UserRoleDto userRoleDto) {
        UserRole userRole = BeanUtil.copyProperties(userRoleDto, UserRole.class);
        userRoleService.save(userRole);
        return ResultBean.success(userRole);
    }

    @ApiOperation(value = "-查询")
    @ApiOperationSupport(order = 4)
    @GetMapping("/admin/userRole/{userRoleId}")
    public ResultBean<UserRoleVo> get(@PathVariable @NotBlank String userRoleId) {
        UserRole userRole = userRoleService.getById(userRoleId);
        UserRoleVo userRoleVo = BeanUtil.copyProperties(userRole,UserRoleVo.class);
        return ResultBean.success(userRoleVo);
    }

    @ApiOperation(value = "-修改")
    @ApiOperationSupport(order = 5)
    @PostMapping("/admin/userRole/update")
    public ResultBean<UserRole> update(@RequestBody @Validated(UpdateGroup.class) UserRoleDto userRoleDto) {
        UserRole userRole = BeanUtil.copyProperties(userRoleDto, UserRole.class);
        userRoleService.updateById(userRole);
        return ResultBean.success(userRole);
    }

    @ApiOperation(value = "-删除")
    @ApiOperationSupport(order = 6)
    @GetMapping("/admin/userRole/delete/{userRoleId}")
    public ResultBean<UserRole> delete(@PathVariable @NotBlank String userRoleId) {
        Boolean b = userRoleService.removeById(userRoleId);
        return ResultBean.success(b);
    }
}
