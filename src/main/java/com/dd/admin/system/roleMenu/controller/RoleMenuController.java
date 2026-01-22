package com.dd.admin.system.roleMenu.controller;

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
import com.dd.admin.system.roleMenu.entity.RoleMenu;
import com.dd.admin.system.roleMenu.domain.RoleMenuVo;
import com.dd.admin.system.roleMenu.domain.RoleMenuDto;
import com.dd.admin.system.roleMenu.service.RoleMenuService;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 角色菜单 前端控制器
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2022-01-04
 */
@Api(tags = "角色菜单")
@RestController
public class RoleMenuController {

    @Autowired
    RoleMenuService roleMenuService;

    @ApiOperation(value = "角色菜单-分页列表")
    @ApiOperationSupport(order = 1)
    @GetMapping("/admin/roleMenu/page")
    public ResultBean<IPage<RoleMenuVo>> page(RoleMenuDto roleMenuDto) {
        IPage<RoleMenuVo> pageInfo = roleMenuService.selectRoleMenuPage(roleMenuDto);
        return ResultBean.success(pageInfo);
    }

    @ApiOperation(value = "角色菜单-列表")
    @ApiOperationSupport(order = 2)
    @GetMapping("/admin/roleMenu/list")
    public ResultBean<List<RoleMenuVo>> list(RoleMenuDto roleMenuDto) {
        List<RoleMenuVo> list = roleMenuService.selectRoleMenuList(roleMenuDto);
        return ResultBean.success(list);
    }

    @ApiOperation(value = "角色菜单-添加")
    @ApiOperationSupport(order = 3)
    @PostMapping("/admin/roleMenu/add")
    public ResultBean<RoleMenu> add(@RequestBody @Validated RoleMenuDto roleMenuDto) {
        RoleMenu roleMenu = BeanUtil.copyProperties(roleMenuDto, RoleMenu.class);
        roleMenuService.save(roleMenu);
        return ResultBean.success(roleMenu);
    }

    @ApiOperation(value = "角色菜单-查询")
    @ApiOperationSupport(order = 4)
    @GetMapping("/admin/roleMenu/{roleMenuId}")
    public ResultBean<RoleMenuVo> get(@PathVariable @NotBlank String roleMenuId) {
        RoleMenu roleMenu = roleMenuService.getById(roleMenuId);
        RoleMenuVo roleMenuVo = BeanUtil.copyProperties(roleMenu,RoleMenuVo.class);
        return ResultBean.success(roleMenuVo);
    }

    @ApiOperation(value = "角色菜单-修改")
    @ApiOperationSupport(order = 5)
    @PostMapping("/admin/roleMenu/update")
    public ResultBean<RoleMenu> update(@RequestBody @Validated(UpdateGroup.class) RoleMenuDto roleMenuDto) {
        RoleMenu roleMenu = BeanUtil.copyProperties(roleMenuDto, RoleMenu.class);
        roleMenuService.updateById(roleMenu);
        return ResultBean.success(roleMenu);
    }

    @ApiOperation(value = "角色菜单-删除")
    @ApiOperationSupport(order = 6)
    @GetMapping("/admin/roleMenu/delete/{roleMenuId}")
    public ResultBean<RoleMenu> delete(@PathVariable @NotBlank String roleMenuId) {
        Boolean b = roleMenuService.removeById(roleMenuId);
        return ResultBean.success(b);
    }
}
