package com.dd.admin.system.menu.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dd.admin.common.model.PageFactory;
import com.dd.admin.common.utils.StringUtil;
import com.dd.admin.system.menu.domain.MenuDto;
import com.dd.admin.system.menu.domain.MenuVo;
import com.dd.admin.system.menu.entity.Menu;
import com.dd.admin.system.menu.mapper.MenuMapper;
import com.dd.admin.system.menu.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.dd.admin.common.consts.SystemConst.AUTH_BUTTON;

/**
 * <p>
 * 菜单 服务实现类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2022-01-05
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public IPage<MenuVo> selectMenuPage(MenuDto menuDto) {
        Page page = PageFactory.defaultPage();
        return baseMapper.selectMenuPage(page,menuDto);
    }

    @Override
    public List<MenuVo> selectMenuList(MenuDto menuDto) {
        return baseMapper.selectMenuList(menuDto);
    }

    @Override
    public List<MenuVo> selectMenuByUserId(String userId) {
        return baseMapper.selectMenuByUserId(userId);
    }

    @Override
    public Set<String> selectPermsByUserId(String userId) {
        List<MenuVo> list = selectMenuByUserId(userId);
        Set<String> perms = list.stream()
                .filter(menu -> menu.getMenuType().equals(AUTH_BUTTON)&& StringUtil.isNotEmpty(menu.getPerms()))
                .map(menu -> menu.getPerms())
                .collect(Collectors.toSet());
        return perms;
    }
}
