package com.dd.admin.system.dept.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * <p>
 * 机构返回对象
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2022-01-04
 */
@Data
@ApiModel(value="机构返回对象")
public class DeptVo {


    @ApiModelProperty(value = "机构id")
    private String deptId;

    @ApiModelProperty(value = "机构名称")
    private String deptName;

    @ApiModelProperty(value = "机构编号")
    private String deptNo;

    @ApiModelProperty(value = "上级id")
    private String parentId;

    @ApiModelProperty(value = "上级机构")
    private String parentName;

    @ApiModelProperty(value = "上级id集合")
    private String parentIds;

    @ApiModelProperty(value = "过期时间")
    private Date expireDate;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createName;

    @ApiModelProperty(value = "创建人id")
    private String createId;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "修改人")
    private String updateName;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "市 ")
    private String city;

    @ApiModelProperty(value = "区")
    private String area;


}
