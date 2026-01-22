package com.dd.admin.system.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2022-01-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user")
@ApiModel(value="User对象", description="用户")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    @TableId(value = "USER_ID", type = IdType.ASSIGN_UUID)
    private String userId;

    @ApiModelProperty(value = "用户名")
    @TableField("USER_NAME")
    private String userName;

    @ApiModelProperty(value = "头像")
    @TableField("AVATAR")
    private String avatar;

    @ApiModelProperty(value = "密码")
    @TableField("PASSWORD")
    private String password;

    @ApiModelProperty(value = "手机号")
    @TableField("PHONE_NUMBER")
    private String phoneNumber;

    @ApiModelProperty(value = "用户类型")
    @TableField("USER_TYPE")
    private Integer userType;

    @ApiModelProperty(value = "用户状态")
    @TableField("USER_STATUS")
    private Integer userStatus;

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
