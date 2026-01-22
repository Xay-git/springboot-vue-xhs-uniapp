package com.dd.admin.system.menu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.admin.system.menu.domain.MenuDto;
import com.dd.admin.system.menu.domain.MenuVo;
import com.dd.admin.system.menu.entity.Menu;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 菜单 服务类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2022-01-05
 */
public interface MenuService extends IService<Menu> {

    //菜单-分页列表
    IPage<MenuVo> selectMenuPage(MenuDto menuDto);

    //菜单-列表
    List<MenuVo> selectMenuList(MenuDto menuDto);

    //根据用户id获取菜单
    // 获取用户菜单集
    List<MenuVo> selectMenuByUserId(String userId);

    //查询用户权限
    Set<String> selectPermsByUserId(String userId);
}
