package com.dd.admin.system.menu.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 菜单返回对象
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2022-01-05
 */
@Data
@ApiModel(value="菜单返回对象")
@Accessors(chain = true)
public class MenuVo {


    private String menuId;

    @ApiModelProperty(value = "父级id")
    private String parentId;

    @ApiModelProperty(value = "菜单标题")
    private String menuTitle;

    @ApiModelProperty(value = "菜单名")
    private String menuName;

    @ApiModelProperty(value = "菜单图标")
    private String menuIcon;

    @ApiModelProperty(value = "菜单文件路径")
    private String menuPath;

    @ApiModelProperty(value = "组件")
    private String component;

    @ApiModelProperty(value = "权限标识")
    private String perms;

    @ApiModelProperty(value = "菜单类型 1菜单 2权限")
    private Integer menuType;

    @ApiModelProperty(value = "页面缓存 1开启 0关闭")
    private Integer menuCache;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createName;

    @ApiModelProperty(value = "创建人id")
    private String createId;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "修改人")
    private String updateName;

    @ApiModelProperty(value = "修改人id")
    private String updateId;


}
