package com.dd.admin.business.author.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.ArrayList;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.dd.admin.business.follow.domain.FollowDto;
import com.dd.admin.business.follow.domain.FollowVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 作者（博主）
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("business_author")
@ApiModel(value="Author对象", description="作者（博主）")
public class Author implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "作者id")
    @TableId(value = "AUTHOR_ID", type = IdType.ASSIGN_UUID)
    private String authorId;

    @ApiModelProperty(value = "作者号")
    @TableField("AUTHOR_NO")
    private String authorNo;

    @ApiModelProperty(value = "作者姓名")
    @TableField("AUTHOR_NAME")
    private String authorName;

    @ApiModelProperty(value = "头像id")
    @TableField("AVATAR_ID")
    private String avatarId;

    @ApiModelProperty(value = "头像地址")
    @TableField("AVATAR_URL")
    private String avatarUrl;

    @ApiModelProperty(value = "简介")
    @TableField("DESCRIPTION")
    private String description;

    @ApiModelProperty(value = "性别")
    @TableField("SEX")
    private String sex;

    @ApiModelProperty(value = "生日")
    @TableField("BIRTH")
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Date birth;

    @ApiModelProperty(value = "职业")
    @TableField("JOB")
    private String job;

    @ApiModelProperty(value = "地区")
    @TableField("AREA")
    private String area;

    @ApiModelProperty(value = "学校")
    @TableField("SCHOOL")
    private String school;

    @ApiModelProperty(value = "背景图")
    @TableField("BACK_GROUND_ID")
    private String backGroundId;

    @ApiModelProperty(value = "背景图地址")
    @TableField("BACK_GROUND_URL")
    private String backGroundUrl;

    @ApiModelProperty(value = "关注数")
    @TableField("FOLLOW")
    private Long follow;

    @ApiModelProperty(value = "粉丝数")
    @TableField("FANS")
    private Long fans;

    @ApiModelProperty(value = "点赞数")
    @TableField("UP_COUNT")
    private Long upCount;

    @ApiModelProperty(value = "收藏数")
    @TableField("STAR_COUNT")
    private Long starCount;

    @ApiModelProperty(value = "0正常")
    @TableField("AUTHOR_STATUS")
    private Integer authorStatus;

    @ApiModelProperty(value = "乐观锁字段")
    @TableField("VERSION")
    @Version
    private Long version;

    @ApiModelProperty(value = "0正常 1删除")
    @TableField("DELETED")
    @TableLogic
    private Integer deleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "UPDATE_TIME", fill = FieldFill.UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "ip地址")
    @TableField("IP_ADDRESS")
    private String ipAddress;

    @ApiModelProperty(value = "真实ip地址")
    @TableField("IP_REAL_ADDRESS")
    private String ipRealAddress;

    @ApiModelProperty(value = "真实姓名")
    @TableField("REAL_NAME")
    private String realName;

    @ApiModelProperty(value = "身份证号")
    @TableField("ID_CARD")
    private String idCard;

    @ApiModelProperty(value = "手机号")
    @TableField("PHONE_NUMBER")
    private String phoneNumber;

    @ApiModelProperty(value = "用户余额")
    @TableField("BALANCE")
    private BigDecimal balance;

    @TableField(exist = false)
    private String token;

    @TableField(exist = false)
    private Boolean isFollow = Boolean.FALSE;

    @TableField(exist = false)
    private Boolean isFollowMe = Boolean.FALSE;

    @TableField(exist = false)
    List<FollowVo> followMes = new ArrayList<>();

    @TableField(exist = false)
    List<FollowVo> myFollows = new ArrayList<>();

    @TableField(exist = false)
    private Long upAndStarCount;

    @TableField(exist = false)
    private Integer age = 0;
}
