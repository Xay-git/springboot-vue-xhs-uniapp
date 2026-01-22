package com.dd.admin.business.dev.domain;

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
 * 返回对象
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-02-11
 */
@Data
@ApiModel(value="返回对象")
public class DevVo {


    private String devId;

    private String serverName;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    private String ipAddress;

    private String ipRealAddress;


}
