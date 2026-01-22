package com.dd.admin.business.dev.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-02-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("business_dev")
@ApiModel(value="Dev对象", description="")
public class Dev implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "DEV_ID", type = IdType.ASSIGN_UUID)
    private String devId;

    @TableField("SERVER_NAME")
    private String serverName;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField("IP_ADDRESS")
    private String ipAddress;

    @TableField("IP_REAL_ADDRESS")
    private String ipRealAddress;


}
