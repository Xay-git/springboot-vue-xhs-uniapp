package com.dd.admin.system.menu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor                //有参构造
public class Meta {
    // 设置该路由在侧边栏和面包屑中展示的名字
    private String title;
    // 设置该路由的图标，支持 svg-class，也支持 el-icon-x element-ui 的 icon
    private String icon;

    //不开启缓存 true不开启 false开启
    private Boolean noCache;
}
