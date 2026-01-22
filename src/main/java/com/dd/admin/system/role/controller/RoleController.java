package com.dd.admin.system.role.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.common.model.UpdateGroup;
import com.dd.admin.common.model.result.ResultBean;
import com.dd.admin.system.role.domain.RoleDto;
import com.dd.admin.system.role.domain.RoleMenusDto;
import com.dd.admin.system.role.domain.RoleVo;
import com.dd.admin.system.role.entity.Role;
import com.dd.admin.system.role.service.RoleService;
import com.dd.admin.system.roleMenu.entity.RoleMenu;
import com.dd.admin.system.roleMenu.service.RoleMenuService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色 前端控制器
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2022-01-04
 */
@Api(tags = "角色")
@RestController
public class RoleController {

    @Autowired
    RoleService roleService;

    @Autowired
    RoleMenuService roleMenuService;

    @ApiOperation(value = "角色-分页列表")
    @ApiOperationSupport(order = 1)
    @GetMapping("/admin/role/page")
    public ResultBean<IPage<RoleVo>> page(RoleDto roleDto) {
        IPage<RoleVo> pageInfo = roleService.selectRolePage(roleDto);
        return ResultBean.success(pageInfo);
    }

    @ApiOperation(value = "角色-列表")
    @ApiOperationSupport(order = 2)
    @GetMapping("/admin/role/list")
    public ResultBean<List<RoleVo>> list(RoleDto roleDto) {
        List<RoleVo> list = roleService.selectRoleList(roleDto);
        return ResultBean.success(list);
    }

    @ApiOperation(value = "角色-添加")
    @ApiOperationSupport(order = 3)
    @PostMapping("/admin/role/add")
    public ResultBean<Role> add(@RequestBody @Validated RoleDto roleDto) {
        Role role = BeanUtil.copyProperties(roleDto, Role.class);
        roleService.save(role);
        return ResultBean.success(role);
    }

    @ApiOperation(value = "角色-查询")
    @ApiOperationSupport(order = 4)
    @GetMapping("/admin/role/{roleId}")
    public ResultBean<RoleVo> get(@PathVariable @NotBlank String roleId) {
        Role role = roleService.getById(roleId);
        RoleVo roleVo = BeanUtil.copyProperties(role,RoleVo.class);
        return ResultBean.success(roleVo);
    }

    @ApiOperation(value = "角色-修改")
    @ApiOperationSupport(order = 5)
    @PostMapping("/admin/role/update")
    public ResultBean<Role> update(@RequestBody @Validated(UpdateGroup.class) RoleDto roleDto) {
        Role role = BeanUtil.copyProperties(roleDto, Role.class);
        roleService.updateById(role);
        return ResultBean.success(role);
    }

    @ApiOperation(value = "角色-删除")
    @ApiOperationSupport(order = 6)
    @GetMapping("/admin/role/delete/{roleId}")
    public ResultBean<Role> delete(@PathVariable @NotBlank String roleId) {
        Boolean b = roleService.removeById(roleId);
        return ResultBean.success(b);
    }

    @ApiOperation(value = "获取角色相应权限")
    @GetMapping("/admin/role/menu/{roleId}")
    @ApiOperationSupport(order = 7)
    public ResultBean<List<String>> menu(@PathVariable @NotBlank String roleId) {
        List<String> list = roleMenuService.selectAllByRoleId(roleId);
        return ResultBean.success(list);
    }


    @ApiOperation(value = "修改角色相应权限")
    @PostMapping("/admin/role/menu/update")
    @Transactional
    public ResultBean updateMenu(@RequestBody @Validated RoleMenusDto roleMenusDto) {
        String roleId = roleMenusDto.getRoleId();
        //删除原有的角色集合
        Integer count = roleMenuService.deleteRoleMenuByRoleId(roleId);
        //获取重新添加的
        List<String> menuIdList = roleMenusDto.getMenuIdList();
        List<RoleMenu> roleMenuList =  menuIdList
                .stream()
                .map(menuId -> {
                    return new RoleMenu().setRoleId(roleId).setMenuId(menuId);
                })
                .collect(Collectors.toList());
        //批量添加
        roleMenuService.saveBatch(roleMenuList);
        return ResultBean.success();
    }
}
