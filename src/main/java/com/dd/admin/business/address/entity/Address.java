package com.dd.admin.business.address.entity;

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
 * 用户地址
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-09-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("business_address")
@ApiModel(value="Address对象", description="用户地址")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "地址ID")
    @TableId(value = "ADDRESS_ID", type = IdType.ASSIGN_UUID)
    private String addressId;

    @ApiModelProperty(value = "用户ID")
    @TableField("AUTHOR_ID")
    private String authorId;

    @ApiModelProperty(value = "收货人姓名")
    @TableField("RECEIVER_NAME")
    private String receiverName;

    @ApiModelProperty(value = "收货人电话")
    @TableField("RECEIVER_PHONE")
    private String receiverPhone;

    @ApiModelProperty(value = "省份")
    @TableField("PROVINCE")
    private String province;

    @ApiModelProperty(value = "城市")
    @TableField("CITY")
    private String city;

    @ApiModelProperty(value = "区/县")
    @TableField("DISTRICT")
    private String district;

    @ApiModelProperty(value = "详细地址")
    @TableField("DETAIL_ADDRESS")
    private String detailAddress;

    @ApiModelProperty(value = "是否默认 0-否 1-是")
    @TableField("IS_DEFAULT")
    private Integer isDefault;

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

