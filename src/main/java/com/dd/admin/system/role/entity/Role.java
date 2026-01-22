package com.dd.admin.system.role.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2022-01-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role")
@ApiModel(value="Role对象", description="角色")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ROLE_ID", type = IdType.ASSIGN_UUID)
    private String roleId;

    @ApiModelProperty(value = "角色名")
    @TableField("ROLE_NAME")
    private String roleName;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

    @ApiModelProperty(value = "机构id")
    @TableField(value = "DEPT_ID", fill = FieldFill.INSERT)
    private String deptId;

    @ApiModelProperty(value = "机构名")
    @TableField(value = "DEPT_NAME", fill = FieldFill.INSERT)
    private String deptName;

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
