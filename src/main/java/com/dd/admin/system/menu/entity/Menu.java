package com.dd.admin.system.menu.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 菜单
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2022-01-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_menu")
@ApiModel(value="Menu对象", description="菜单")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "MENU_ID", type = IdType.ASSIGN_UUID)
    private String menuId;

    @ApiModelProperty(value = "父级id")
    @TableField("PARENT_ID")
    private String parentId;

    @ApiModelProperty(value = "菜单标题")
    @TableField("MENU_TITLE")
    private String menuTitle;

    @ApiModelProperty(value = "菜单名")
    @TableField("MENU_NAME")
    private String menuName;

    @ApiModelProperty(value = "菜单图标")
    @TableField("MENU_ICON")
    private String menuIcon;

    @ApiModelProperty(value = "菜单文件路径")
    @TableField("MENU_PATH")
    private String menuPath;

    @ApiModelProperty(value = "组件")
    @TableField("COMPONENT")
    private String component;

    @ApiModelProperty(value = "权限标识")
    @TableField("PERMS")
    private String perms;

    @ApiModelProperty(value = "菜单类型 1菜单 2权限")
    @TableField("MENU_TYPE")
    private Integer menuType;

    @ApiModelProperty(value = "页面缓存 1开启 0关闭")
    @TableField("MENU_CACHE")
    private Integer menuCache;

    @ApiModelProperty(value = "组件")
    @TableField("MENU_SORT")
    private String menuSort;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    @TableField(value = "CREATE_NAME", fill = FieldFill.INSERT)
    private String createName;

    @ApiModelProperty(value = "创建人id")
    @TableField(value = "CREATE_ID", fill = FieldFill.INSERT)
    private String createId;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "UPDATE_TIME", fill = FieldFill.UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "修改人")
    @TableField(value = "UPDATE_NAME", fill = FieldFill.UPDATE)
    private String updateName;

    @ApiModelProperty(value = "修改人id")
    @TableField(value = "UPDATE_ID", fill = FieldFill.UPDATE)
    private String updateId;
}
