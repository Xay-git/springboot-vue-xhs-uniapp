package com.dd.admin.business.address.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 地址DTO
 * </p>
 *
 * @author system
 * @since 2025-01-13
 */
@Data
@ApiModel(value="AddressDto对象", description="地址DTO")
public class AddressDto {

    @ApiModelProperty(value = "地址ID")
    private String addressId;

    @ApiModelProperty(value = "用户ID")
    private String authorId;

    @ApiModelProperty(value = "收货人姓名", required = true)
    @NotBlank(message = "收货人姓名不能为空")
    private String receiverName;

    @ApiModelProperty(value = "收货人电话", required = true)
    @NotBlank(message = "收货人电话不能为空")
    private String receiverPhone;

    @ApiModelProperty(value = "省份", required = true)
    @NotBlank(message = "省份不能为空")
    private String province;

    @ApiModelProperty(value = "城市", required = true)
    @NotBlank(message = "城市不能为空")
    private String city;

    @ApiModelProperty(value = "区/县", required = true)
    @NotBlank(message = "区/县不能为空")
    private String district;

    @ApiModelProperty(value = "详细地址", required = true)
    @NotBlank(message = "详细地址不能为空")
    private String detailAddress;

    @ApiModelProperty(value = "是否默认 0-否 1-是")
    private Integer isDefault;
}