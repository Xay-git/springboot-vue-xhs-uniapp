package com.dd.admin.system.setting.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统设置接收对象
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-01
 */
@Data
@Accessors(chain = true)
@ApiModel(value="SystemSettingDto对象", description="系统设置接收对象")
public class SystemSettingDto {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "设置ID")
    private String settingId;

    @ApiModelProperty(value = "设置键")
    private String settingKey;

    @ApiModelProperty(value = "设置值")
    private String settingValue;

    @ApiModelProperty(value = "设置描述")
    private String settingDesc;

    @ApiModelProperty(value = "设置类型")
    private String settingType;

    @ApiModelProperty(value = "是否启用")
    private Boolean isEnabled;

}