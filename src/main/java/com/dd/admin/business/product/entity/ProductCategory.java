package com.dd.admin.business.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商品分类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-09-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("business_product_category")
@ApiModel(value="ProductCategory对象", description="商品分类")
public class ProductCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分类ID")
    @TableId(value = "CATEGORY_ID", type = IdType.ASSIGN_UUID)
    private String categoryId;

    @ApiModelProperty(value = "分类名称")
    @TableField("CATEGORY_NAME")
    private String categoryName;

    @ApiModelProperty(value = "父分类ID")
    @TableField("PARENT_ID")
    private String parentId;

    @ApiModelProperty(value = "排序")
    @TableField("CATEGORY_SORT")
    private Integer categorySort;

    @ApiModelProperty(value = "状态 0-正常 1-禁用")
    @TableField("CATEGORY_STATUS")
    private Integer categoryStatus;

    @ApiModelProperty(value = "乐观锁字段")
    @TableField("VERSION")
    @Version
    private Long version;

    @ApiModelProperty(value = "0-正常 1-删除")
    @TableField("DELETED")
    @TableLogic
    private Integer deleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "UPDATE_TIME", fill = FieldFill.UPDATE)
    private Date updateTime;
    
    @TableField(exist = false)
    private List<ProductCategory> children;
}

