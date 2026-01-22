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
import javax.validation.constraints.NotBlank;
import com.dd.admin.common.model.UpdateGroup;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * <p>
 * 笔记表返回对象
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-09
 */
@Data
@ApiModel(value="笔记表接收对象")
public class NoteDto {
    @ApiModelProperty(value = "查询我收藏的字段")
    private String myStarById;

    @ApiModelProperty(value = "查询我点赞的字段")
    private String myUpById;

    @ApiModelProperty(value = "笔记id")
    @NotBlank(message = "笔记表id不能为空",groups = UpdateGroup.class)
    private String noteId;

    @ApiModelProperty(value = "笔记标题")
    private String noteTitle;

    @ApiModelProperty(value = "笔记内容")
    private String noteContent;

    @ApiModelProperty(value = "类型id")
    private String noteCategory;

    @ApiModelProperty(value = "笔记类型名")
    private String noteCategoryName;

    @ApiModelProperty(value = "0:图文 1:视频 2:音乐")
    private Integer type;

    @ApiModelProperty(value = "1 图文 2视频  3文字")
    private Integer noteType;

    @ApiModelProperty(value = "作者ID")
    private String authorId;

    private String authorIds;

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
    private Long upCount;

    @ApiModelProperty(value = "收藏数")
    private Long starCount;

    @ApiModelProperty(value = "初始点赞数")
    private Long initUpCount = 0L;
    
    @ApiModelProperty(value = "初始收藏数")
    private Long initStarCount = 0L;

    @ApiModelProperty(value = "图片列表")
    private List<String> imgs;

    @ApiModelProperty(value = "作者ID")
    //传入此参数 如果你点赞了会显示相应状态
    private String followId;

    @ApiModelProperty(value = "关键字搜索")
    private String keyword;

    private Integer noteStatus;

    @ApiModelProperty(value = "视频地址")
    private String videoUrl;

    @ApiModelProperty(value = "音乐地址")
    private String musicUrl;

    @ApiModelProperty(value = "关联商品ID")
    private String productId;

}
