package com.dd.admin.system.role.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.NotBlank;
import com.dd.admin.common.model.UpdateGroup;


/**
 * <p>
 * 角色返回对象
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2022-01-04
 */
@Data
@ApiModel(value="角色接收对象")
public class RoleDto {


    @NotBlank(message = "角色id不能为空",groups = UpdateGroup.class)
    private String roleId;

    @ApiModelProperty(value = "角色名")
    private String roleName;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "机构id")
    private String deptId;

    @ApiModelProperty(value = "机构名")
    private String deptName;

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

    @ApiModelProperty(value = "修改人id")
    private String updateId;


}
