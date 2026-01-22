package com.dd.admin.business.author.domain;

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
 * 作者（博主）返回对象
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-12
 */
@Data
@ApiModel(value="作者（博主）返回对象")
public class AuthorVo {


    @ApiModelProperty(value = "作者id")
    private String authorId;

    @ApiModelProperty(value = "作者号")
    private String authorNo;

    @ApiModelProperty(value = "作者姓名")
    private String authorName;

    @ApiModelProperty(value = "头像id")
    private String avatarId;

    @ApiModelProperty(value = "头像地址")
    private String avatarUrl;

    @ApiModelProperty(value = "简介")
    private String description;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "生日")
    private Date birth;

    @ApiModelProperty(value = "职业")
    private String job;

    @ApiModelProperty(value = "地区")
    private String area;

    @ApiModelProperty(value = "学校")
    private String school;

    @ApiModelProperty(value = "背景图")
    private String backGroundId;

    @ApiModelProperty(value = "背景图地址")
    private String backGroundUrl;

    @ApiModelProperty(value = "关注数")
    private Long follow;

    @ApiModelProperty(value = "粉丝数")
    private Long fans;

    @ApiModelProperty(value = "点赞数")
    private Long upCount;

    @ApiModelProperty(value = "收藏数")
    private Long starCount;

    @ApiModelProperty(value = "0正常")
    private Integer authorStatus;

    @ApiModelProperty(value = "乐观锁字段")
    private Long version;

    @ApiModelProperty(value = "0正常 1删除")
    private Integer deleted;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "ip地址")
    private String ipAddress;

    @ApiModelProperty(value = "真实ip地址")
    private String ipRealAddress;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "手机号")
    private String phoneNumber;

    private Boolean isFollowMe = Boolean.FALSE;
    private Boolean isFollow = Boolean.FALSE;

}
