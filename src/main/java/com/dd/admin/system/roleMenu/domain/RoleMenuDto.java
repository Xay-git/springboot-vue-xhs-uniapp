package com.dd.admin.system.roleMenu.domain;

import com.dd.admin.common.model.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;


/**
 * <p>
 * 角色菜单返回对象
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2022-01-04
 */
@Data
@ApiModel(value="角色菜单接收对象")
public class RoleMenuDto {


    @ApiModelProperty(value = "id")
    @NotBlank(message = "角色菜单id不能为空",groups = UpdateGroup.class)
    private String id;

    @ApiModelProperty(value = "角色id")
    private String roleId;

    @ApiModelProperty(value = "菜单id")
    private String menuId;

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


}
