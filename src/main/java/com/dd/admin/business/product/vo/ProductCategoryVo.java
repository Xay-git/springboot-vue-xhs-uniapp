package com.dd.admin.business.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 商品分类Vo
 *
 * @author system
 * @date 2024-01-15
 */
@Data
@ApiModel(value = "ProductCategoryVo对象", description = "商品分类Vo")
public class ProductCategoryVo {

    @ApiModelProperty(value = "分类ID")
    private String categoryId;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    @ApiModelProperty(value = "父级分类ID")
    private String parentId;

    @ApiModelProperty(value = "分类图标")
    private String categoryIcon;

    @ApiModelProperty(value = "分类描述")
    private String categoryDesc;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "分类状态 0-禁用 1-启用")
    private Integer categoryStatus;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "乐观锁字段")
    private Long version;

    @ApiModelProperty(value = "0正常 1删除")
    private Integer deleted;
}