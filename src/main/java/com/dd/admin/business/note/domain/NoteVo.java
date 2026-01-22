package com.dd.admin.business.note.domain;

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

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 笔记表返回对象
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-09
 */
@Data
@ApiModel(value="笔记表返回对象")
public class NoteVo {


    @ApiModelProperty(value = "笔记id")
    private String noteId;

    @ApiModelProperty(value = "笔记标题")
    private String noteTitle;

    @ApiModelProperty(value = "笔记内容")
    private String noteContent;

    @ApiModelProperty(value = "类型id")
    private String noteCategory;

    @ApiModelProperty(value = "笔记类型名")
    private String noteCategoryName;

    @ApiModelProperty(value = "1 图文 2视频  3文字")
    private Integer noteType;

    @ApiModelProperty(value = "作者ID")
    private String authorId;


    @ApiModelProperty(value = "作者头像")
    private String authorAvatar;

    @ApiModelProperty(value = "作者名字")
    private String authorName;

    @ApiModelProperty(value = "首图URL")
    private String firstPicture;

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

    @ApiModelProperty(value = "点赞数")
    private Integer upCount = 0;

    @ApiModelProperty(value = "被当前达人点赞")
    private Boolean isUp = Boolean.FALSE;

    @ApiModelProperty(value = "收藏数")
    private Long starCount;

    @ApiModelProperty(value = "初始点赞数")
    private Long initUpCount = 0L;
    
    @ApiModelProperty(value = "初始收藏数")
    private Long initStarCount = 0L;

    @ApiModelProperty(value = "被当前达人点赞")
    private Boolean isStar = Boolean.FALSE;

    @ApiModelProperty(value = "图片集合")
    private List<String> imgList;

    private Integer noteStatus;

    @ApiModelProperty(value = "查看数")
    private Long viewCount;

    @ApiModelProperty(value = "视频地址")
    private String videoUrl;

    @ApiModelProperty(value = "音乐地址")
    private String musicUrl;

    @ApiModelProperty(value = "关联商品ID")
    private String productId;

    @ApiModelProperty(value = "商品名称")
    private String productName;

    @ApiModelProperty(value = "商品价格")
    private String productPrice;

    @ApiModelProperty(value = "商品图片")
    private String productImage;
}
