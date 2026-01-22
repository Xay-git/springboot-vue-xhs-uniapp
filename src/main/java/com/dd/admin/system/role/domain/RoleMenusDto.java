package com.dd.admin.system.role.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 角色表返回对象
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2021-07-27
 */
@Data
@ApiModel(value="角色表接收对象")
public class RoleMenusDto {
    private String roleId;
    private List<String> menuIdList;
}
