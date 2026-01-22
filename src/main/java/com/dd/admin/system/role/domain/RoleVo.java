package com.dd.admin.system.role.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 角色返回对象
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2022-01-04
 */
@Data
@ApiModel(value="角色返回对象")
public class RoleVo {


    private String roleId;

    @ApiModelProperty(value = "角色名")
    private String roleName;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "机构id")
    private String deptId;

    @ApiModelProperty(value = "机构名")
    private String deptName;

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
