package com.dd.admin.system.user.domain;

import com.dd.admin.common.model.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;


/**
 * <p>
 * 用户返回对象
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2022-01-05
 */
@Data
@ApiModel(value="用户接收对象")
public class UserDto {


    @ApiModelProperty(value = "用户id")
    @NotBlank(message = "用户id不能为空",groups = UpdateGroup.class)
    private String userId;

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String userName;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "手机号")
    private String phoneNumber;

    @ApiModelProperty(value = "用户类型")
    private Integer userType;

    @ApiModelProperty(value = "用户状态")
    private Integer userStatus;

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

    @ApiModelProperty(value = "用户角色")
    @NotEmpty(message = "用户角色不能为空")
    private List<String> roles;
}
