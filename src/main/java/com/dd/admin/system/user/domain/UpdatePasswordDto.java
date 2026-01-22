package com.dd.admin.system.user.domain;

import com.dd.admin.common.model.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value="用户修改密码接收对象")
public class UpdatePasswordDto {
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "密码")
    private String newPassword;
}
