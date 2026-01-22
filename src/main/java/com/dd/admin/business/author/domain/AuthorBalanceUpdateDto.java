package com.dd.admin.business.author.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 作者余额修改DTO
 *
 * @author admin
 * @since 2024-12-12
 */
@Data
@ApiModel(value = "AuthorBalanceUpdateDto", description = "作者余额修改DTO")
public class AuthorBalanceUpdateDto {

    @ApiModelProperty(value = "作者ID", required = true)
    @NotBlank(message = "作者ID不能为空")
    private String authorId;

    @ApiModelProperty(value = "操作类型 3-管理员增加 4-管理员减少", required = true)
    @NotNull(message = "操作类型不能为空")
    private Integer operationType;

    @ApiModelProperty(value = "金额", required = true)
    @NotNull(message = "金额不能为空")
    @DecimalMin(value = "0.01", message = "金额必须大于0")
    private BigDecimal amount;

    @ApiModelProperty(value = "备注")
    private String remark;
}