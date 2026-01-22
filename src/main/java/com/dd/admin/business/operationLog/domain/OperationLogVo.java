package com.dd.admin.business.operationLog.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 操作日志返回对象
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-06-07
 */
@Data
@ApiModel(value="操作日志返回对象")
public class OperationLogVo {


    @ApiModelProperty(value = "日志id")
    private String operId;

    @ApiModelProperty(value = "操作ip")
    @TableField("OPER_IP_ADDRESS")
    private String operIpAddress;

    @ApiModelProperty(value = "请求模块")
    private String operModule;

    @ApiModelProperty(value = "操作类型")
    private String operType;

    @ApiModelProperty(value = "操作描述")
    private String operDesc;

    @ApiModelProperty(value = "操作方法")
    private String operMethod;

    @ApiModelProperty(value = "请求参数")
    private String oprrRequestParam;

    @ApiModelProperty(value = "响应参数")
    private String operResponseParam;

    @ApiModelProperty(value = "操作人姓名")
    private String operUserId;

    @ApiModelProperty(value = "操作人ID")
    private String operUserName;

    @ApiModelProperty(value = "操作ip")
    private String operIp;

    @ApiModelProperty(value = "请求url")
    private String operUrl;

    @ApiModelProperty(value = "操作机构id")
    private String operDeptId;

    @ApiModelProperty(value = "机构名")
    private String operDeptName;

    private Date createTime;

    @ApiModelProperty(value = "会员id")
    private String memberId;

    @ApiModelProperty(value = "会员名")
    private String memberName;


}
