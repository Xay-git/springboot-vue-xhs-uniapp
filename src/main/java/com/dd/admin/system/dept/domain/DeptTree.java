package com.dd.admin.system.dept.domain;

import com.dd.admin.system.dept.entity.Dept;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value="机构返回对象")
public class DeptTree extends Dept {
    @ApiModelProperty(value = "上级id")
    private String parentId;

    @ApiModelProperty(value = "机构id")
    private String deptId;

    @ApiModelProperty(value = "下级机构")
    private List<DeptTree> children;
}
