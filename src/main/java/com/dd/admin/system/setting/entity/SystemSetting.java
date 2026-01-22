package com.dd.admin.system.setting.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 系统设置
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-01
 */
@Data
@Accessors(chain = true)
@TableName("system_setting")
@ApiModel(value="SystemSetting对象", description="系统设置")
public class SystemSetting implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "设置ID")
    @TableId(value = "SETTING_ID", type = IdType.ASSIGN_UUID)
    private String settingId;

    @ApiModelProperty(value = "设置键")
    @TableField("SETTING_KEY")
    private String settingKey;

    @ApiModelProperty(value = "设置值")
    @TableField("SETTING_VALUE")
    private String settingValue;

    @ApiModelProperty(value = "设置描述")
    @TableField("SETTING_DESC")
    private String settingDesc;

    @ApiModelProperty(value = "设置类型")
    @TableField("SETTING_TYPE")
    private String settingType;

    @ApiModelProperty(value = "是否启用")
    @TableField("IS_ENABLED")
    private Boolean isEnabled;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private Date createTime;

    @ApiModelProperty(value = "创建人姓名")
    @TableField("CREATE_NAME")
    private String createName;

    @ApiModelProperty(value = "创建人ID")
    @TableField("CREATE_ID")
    private String createId;

    @ApiModelProperty(value = "更新时间")
    @TableField("UPDATE_TIME")
    private Date updateTime;

    @ApiModelProperty(value = "更新人姓名")
    @TableField("UPDATE_NAME")
    private String updateName;

}