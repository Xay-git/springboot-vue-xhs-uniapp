package com.dd.admin.business.noteImg.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.dd.admin.business.note.domain.NoteVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 笔记包含的图片返回对象
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-23
 */
@Data
@ApiModel(value="笔记包含的图片返回对象")
public class NoteImgVo {


    @ApiModelProperty(value = "关联id")
    private NoteVo noteImgId;

    @ApiModelProperty(value = "图片上传的文件id")
    private String imgId;

    @ApiModelProperty(value = "相应的笔记id")
    private String noteId;

    @ApiModelProperty(value = "图片排序")
    private Integer imgSort;

    @ApiModelProperty(value = "作者ID")
    private String authorId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "图片地址")
    private String imgUrl;


}
