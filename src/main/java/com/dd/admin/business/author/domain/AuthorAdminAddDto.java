package com.dd.admin.business.author.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value="后台新增博主接收对象")
public class AuthorAdminAddDto {
    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank(message = "手机号不能为空")
    private String phoneNumber;

    @ApiModelProperty(value = "昵称")
    private String authorName;

    @ApiModelProperty(value = "简介")
    private String description;

    @ApiModelProperty(value = "头像文件ID")
    private String avatarUrl;

} 