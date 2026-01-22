package com.dd.admin.system.menu.domain;

import com.dd.admin.system.menu.entity.Menu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value="菜单接收对象")
public class MenuTree extends Menu {

    @ApiModelProperty(value = "菜单id")
    private String menuId;

    @ApiModelProperty(value = "父级id")
    private String parentId;

    @ApiModelProperty(value = "下级菜单")
    private List<MenuTree> children;


    public MenuTree buildTopMenu(){
        MenuTree menuTree = new MenuTree();
        menuTree.setMenuId("0");
        menuTree.setMenuTitle("顶级");
        return menuTree;
    }
}
