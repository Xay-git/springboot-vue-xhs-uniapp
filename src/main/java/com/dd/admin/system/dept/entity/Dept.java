package com.dd.admin.system.dept.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 机构
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2022-01-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_dept")
@ApiModel(value="Dept对象", description="机构")
public class Dept implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "机构id")
    @TableId(value = "DEPT_ID", type = IdType.ASSIGN_UUID)
    private String deptId;

    @ApiModelProperty(value = "机构名称")
    @TableField(value = "DEPT_NAME", fill = FieldFill.INSERT)
    private String deptName;

    @ApiModelProperty(value = "机构编号")
    @TableField("DEPT_NO")
    private String deptNo;

    @ApiModelProperty(value = "上级id")
    @TableField("PARENT_ID")
    private String parentId;

    @ApiModelProperty(value = "上级机构")
    @TableField("PARENT_NAME")
    private String parentName;

    @ApiModelProperty(value = "上级id集合")
    @TableField("PARENT_IDS")
    private String parentIds;


    @ApiModelProperty(value = "省")
    @TableField("PROVINCE")
    private String province;

    @ApiModelProperty(value = "市 ")
    @TableField("PARENT_IDS")
    private String CITY;

    @ApiModelProperty(value = "区")
    @TableField("PARENT_IDS")
    private String AREA;


    @ApiModelProperty(value = "过期时间")
    @TableField("EXPIRE_DATE")
    private Date expireDate;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

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


}
