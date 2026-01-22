package com.dd.admin.business.noteImg.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 笔记包含的图片
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("business_note_img")
@ApiModel(value="NoteImg对象", description="笔记包含的图片")
public class NoteImg implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "关联id")
    @TableId(value = "NOTE_IMG_ID", type = IdType.ASSIGN_UUID)
    private String noteImgId;

    @ApiModelProperty(value = "图片上传的文件id")
    @TableField("IMG_ID")
    private String imgId;

    @ApiModelProperty(value = "相应的笔记id")
    @TableField("NOTE_ID")
    private String noteId;

    @ApiModelProperty(value = "图片排序")
    @TableField("IMG_SORT")
    private Integer imgSort;

    @ApiModelProperty(value = "作者ID")
    @TableField("AUTHOR_ID")
    private String authorId;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "UPDATE_TIME", fill = FieldFill.UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "图片地址")
    @TableField("IMG_URL")
    private String imgUrl;


}
