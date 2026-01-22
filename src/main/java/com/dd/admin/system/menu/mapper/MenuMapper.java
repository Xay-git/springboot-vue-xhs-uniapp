package com.dd.admin.system.menu.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.system.menu.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.admin.system.menu.domain.MenuVo;
import com.dd.admin.system.menu.domain.MenuDto;

import java.util.List;

/**
 * <p>
 * 菜单 Mapper 接口
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2022-01-05
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    IPage<MenuVo> selectMenuPage(Page<MenuVo> page, @Param("menuDto") MenuDto menuDto);

    List<MenuVo> selectMenuList(@Param("menuDto") MenuDto menuDto);

    //根据用户id获取菜单
    // 获取用户菜单集
    List<MenuVo> selectMenuByUserId(@Param("userId") String userId);
}
