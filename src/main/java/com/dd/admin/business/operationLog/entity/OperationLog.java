package com.dd.admin.business.operationLog.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 操作日志
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-06-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("business_operation_log")
@ApiModel(value="OperationLog对象", description="操作日志")
public class OperationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "日志id")
    @TableId(value = "OPER_ID", type = IdType.ASSIGN_UUID)
    private String operId;

    @ApiModelProperty(value = "请求模块")
    @TableField("OPER_MODULE")
    private String operModule;

    @ApiModelProperty(value = "操作类型")
    @TableField("OPER_TYPE")
    private String operType;

    @ApiModelProperty(value = "操作描述")
    @TableField("OPER_DESC")
    private String operDesc;

    @ApiModelProperty(value = "操作方法")
    @TableField("OPER_METHOD")
    private String operMethod;

    @ApiModelProperty(value = "请求参数")
    @TableField("OPRR_REQUEST_PARAM")
    private String oprrRequestParam;

    @ApiModelProperty(value = "响应参数")
    @TableField("OPER_RESPONSE_PARAM")
    private String operResponseParam;

    @ApiModelProperty(value = "操作人姓名")
    @TableField("OPER_USER_ID")
    private String operUserId;

    @ApiModelProperty(value = "操作人ID")
    @TableField("OPER_USER_NAME")
    private String operUserName;

    @ApiModelProperty(value = "操作ip")
    @TableField("OPER_IP")
    private String operIp;

    @ApiModelProperty(value = "操作ip")
    @TableField("OPER_IP_ADDRESS")
    private String operIpAddress;

    @ApiModelProperty(value = "请求url")
    @TableField("OPER_URL")
    private String operUrl;

    @ApiModelProperty(value = "操作机构id")
    @TableField("OPER_DEPT_ID")
    private String operDeptId;

    @ApiModelProperty(value = "机构名")
    @TableField("OPER_DEPT_NAME")
    private String operDeptName;

    @TableField(value = "CREATE_TIME")
    private Date createTime;

    @ApiModelProperty(value = "会员id")
    @TableField("MEMBER_ID")
    private String memberId;

    @ApiModelProperty(value = "会员名")
    @TableField("MEMBER_NAME")
    private String memberName;


}
