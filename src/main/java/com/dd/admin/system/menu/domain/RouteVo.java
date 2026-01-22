package com.dd.admin.system.menu.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class RouteVo {


 /**
  * 菜单id
  */
 private String menuId;
 /**
  * 菜单上级id
  */
 private String parentId;

    /**
     * 路径
     */
   private String path;

    /**
     * 名字
     */
    private String name;
    /**
     * 点击面包屑是否可以点击
     */
    private String redirect;
    /**
     * 一级页面统一为Layout
     */
    private String component;
    /**
     * 额外信息
     */
    private Meta meta;
    /**
     * 子菜单
     */
    @JsonInclude(value= JsonInclude.Include.NON_NULL)
    private List<RouteVo> children;
}
