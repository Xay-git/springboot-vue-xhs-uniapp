package com.dd.admin.system.menu.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.common.model.UpdateGroup;
import com.dd.admin.common.model.result.ResultBean;
import com.dd.admin.common.service.CommonService;
import com.dd.admin.common.utils.ColumnUtil;
import com.dd.admin.system.menu.domain.MenuDto;
import com.dd.admin.system.menu.domain.MenuTree;
import com.dd.admin.system.menu.domain.MenuVo;
import com.dd.admin.system.menu.entity.Menu;
import com.dd.admin.system.menu.service.MenuService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * <p>
 * 菜单 前端控制器
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2022-01-05
 */
@Api(tags = "菜单")
@RestController
public class MenuController {

    @Autowired
    MenuService menuService;
    @Autowired
    CommonService commonService;

    @ApiOperation(value = "菜单-分页列表")
    @ApiOperationSupport(order = 1)
    @GetMapping("/admin/menu/page")
    public ResultBean<IPage<MenuVo>> page(MenuDto menuDto) {
        IPage<MenuVo> pageInfo = menuService.selectMenuPage(menuDto);
        return ResultBean.success(pageInfo);
    }

    @ApiOperation(value = "菜单-列表")
    @ApiOperationSupport(order = 2)
    @GetMapping("/admin/menu/list")
    public ResultBean<List<MenuVo>> list(MenuDto menuDto) {
        List<MenuVo> list = menuService.selectMenuList(menuDto);
        return ResultBean.success(list);
    }

    @ApiOperation(value = "菜单-添加")
    @ApiOperationSupport(order = 3)
    @PostMapping("/admin/menu/add")
    public ResultBean<Menu> add(@RequestBody @Validated MenuDto menuDto) {
        Menu menu = BeanUtil.copyProperties(menuDto, Menu.class);
        menuService.save(menu);
        return ResultBean.success(menu);
    }

    @ApiOperation(value = "菜单-查询")
    @ApiOperationSupport(order = 4)
    @GetMapping("/admin/menu/{menuId}")
    public ResultBean<MenuVo> get(@PathVariable @NotBlank String menuId) {
        Menu menu = menuService.getById(menuId);
        MenuVo menuVo = BeanUtil.copyProperties(menu,MenuVo.class);
        return ResultBean.success(menuVo);
    }

    @ApiOperation(value = "菜单-修改")
    @ApiOperationSupport(order = 5)
    @PostMapping("/admin/menu/update")
    public ResultBean<Menu> update(@RequestBody @Validated(UpdateGroup.class) MenuDto menuDto) {
        Menu menu = BeanUtil.copyProperties(menuDto, Menu.class);
        menuService.updateById(menu);
        return ResultBean.success(menu);
    }

    @ApiOperation(value = "菜单-删除")
    @ApiOperationSupport(order = 6)
    @GetMapping("/admin/menu/delete/{menuId}")
    public ResultBean<Menu> delete(@PathVariable @NotBlank String menuId) {
        Boolean b = menuService.removeById(menuId);
        return ResultBean.success(b);
    }


    @ApiOperation(value = "菜单-tree")
    @ApiOperationSupport(order = 7)
    @GetMapping("/admin/menu/tree")
    public ResultBean<List<MenuTree>> tree() {
        LambdaQueryWrapper<Menu> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByAsc(Menu::getMenuSort);
        List<Menu> menuList = menuService.list(lambdaQueryWrapper);
        List<MenuTree> treeList =  BeanUtil.copyToList(menuList, MenuTree.class);
        treeList.add(new MenuTree().buildTopMenu());
        List<MenuTree> tree = commonService.generateSubs(treeList, ColumnUtil.getName(MenuTree::getMenuId));
        return ResultBean.success(tree);
    }
}
