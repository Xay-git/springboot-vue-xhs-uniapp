package com.dd.admin.business.note.entity;

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
 * 笔记表
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("business_note")
@ApiModel(value="Note对象", description="笔记表")
public class Note implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "笔记id")
    @TableId(value = "NOTE_ID", type = IdType.ASSIGN_UUID)
    private String noteId;

    @ApiModelProperty(value = "笔记标题")
    @TableField("NOTE_TITLE")
    private String noteTitle;

    @ApiModelProperty(value = "笔记内容")
    @TableField("NOTE_CONTENT")
    private String noteContent;

    @ApiModelProperty(value = "类型id")
    @TableField("NOTE_CATEGORY")
    private String noteCategory;

    @ApiModelProperty(value = "笔记类型名")
    @TableField("NOTE_CATEGORY_NAME")
    private String noteCategoryName;

    @ApiModelProperty(value = "1 图文 2视频  3文字")
    @TableField("NOTE_TYPE")
    private Integer noteType;

    @ApiModelProperty(value = "作者ID")
    @TableField("AUTHOR_ID")
    private String authorId;

    @ApiModelProperty(value = "作者名字")
    @TableField("AUTHOR_NAME")
    private String authorName;

    @ApiModelProperty(value = "首图URL")
    @TableField("FIRST_PICTURE")
    private String firstPicture;

    @ApiModelProperty(value = "视频地址")
    @TableField("VIDEO_URL")
    private String videoUrl;

    @ApiModelProperty(value = "音乐地址")
    @TableField("MUSIC_URL")
    private String musicUrl;

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

    @ApiModelProperty(value = "0正常")
    @TableField("NOTE_STATUS")
    private Integer noteStatus;
    
    @ApiModelProperty(value = "初始点赞数")
    @TableField("INIT_UP_COUNT")
    private Long initUpCount = 0L;
    
    @ApiModelProperty(value = "初始收藏数")
    @TableField("INIT_STAR_COUNT")
    private Long initStarCount = 0L;
    
    @ApiModelProperty(value = "关联商品ID")
    @TableField("PRODUCT_ID")
    private String productId;
}
