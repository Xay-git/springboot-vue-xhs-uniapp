package com.dd.admin.system.setting.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 系统设置返回对象
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-01
 */
@Data
@Accessors(chain = true)
@ApiModel(value="SystemSettingVo对象", description="系统设置返回对象")
public class SystemSettingVo implements Serializable {

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

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人姓名")
    private String createName;

    @ApiModelProperty(value = "创建人ID")
    private String createId;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "更新人姓名")
    private String updateName;

}