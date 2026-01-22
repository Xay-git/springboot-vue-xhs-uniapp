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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商品图片
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-09-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("business_product_image")
@ApiModel(value="ProductImage对象", description="商品图片")
public class ProductImage implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "商品ID")
    @TableField("PRODUCT_ID")
    private String productId;

    @ApiModelProperty(value = "图片ID")
    @TableField("IMAGE_ID")
    private String imageId;

    @ApiModelProperty(value = "图片URL")
    @TableField("IMAGE_URL")
    private String imageUrl;

    @ApiModelProperty(value = "排序")
    @TableField("IMAGE_SORT")
    private Integer imageSort;

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
}






