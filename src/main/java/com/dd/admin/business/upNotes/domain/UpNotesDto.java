package com.dd.admin.business.upNotes.domain;

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
import javax.validation.constraints.NotBlank;
import com.dd.admin.common.model.UpdateGroup;


/**
 * <p>
 * 点赞笔记列表返回对象
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-24
 */
@Data
@ApiModel(value="点赞笔记列表接收对象")
public class UpNotesDto {


    @NotBlank(message = "点赞笔记列表id不能为空",groups = UpdateGroup.class)
    private String upNoteId;

    private String noteId;

    private String noteTitle;

    @ApiModelProperty(value = "被关注id")
    private String authorId;

    @ApiModelProperty(value = "被关注名字")
    private String authorName;

    @ApiModelProperty(value = "关注者")
    private String followId;

    @ApiModelProperty(value = "关注者名字")
    private String followName;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "0发送 1已读")
    @TableField("MESSAGE_STATUS")
    private Integer messageStatus;

}
