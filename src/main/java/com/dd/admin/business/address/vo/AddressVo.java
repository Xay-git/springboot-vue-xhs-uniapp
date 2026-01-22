package com.dd.admin.business.address.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 地址VO
 * </p>
 *
 * @author system
 * @since 2025-01-13
 */
@Data
@ApiModel(value="AddressVo对象", description="地址VO")
public class AddressVo {

    @ApiModelProperty(value = "地址ID")
    private String addressId;

    @ApiModelProperty(value = "用户ID")
    private String authorId;

    @ApiModelProperty(value = "收货人姓名")
    private String receiverName;

    @ApiModelProperty(value = "收货人电话")
    private String receiverPhone;

    @ApiModelProperty(value = "省份")
    private String province;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "区/县")
    private String district;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    @ApiModelProperty(value = "完整地址")
    private String fullAddress;

    @ApiModelProperty(value = "是否默认 0-否 1-是")
    private Integer isDefault;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    /**
     * 获取完整地址
     */
    public String getFullAddress() {
        if (province != null && city != null && district != null && detailAddress != null) {
            return province + city + district + detailAddress;
        }
        return "";
    }
}