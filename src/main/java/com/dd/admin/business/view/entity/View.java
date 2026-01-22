package com.dd.admin.business.view.entity;

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
 * 查看
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-01-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("business_view")
@ApiModel(value="View对象", description="查看")
public class View implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "查看id")
    @TableId(value = "VIEW_ID", type = IdType.ASSIGN_UUID)
    private String viewId;

    @ApiModelProperty(value = "作者id")
    @TableField("AUTHOR_ID")
    private String authorId;

    @ApiModelProperty(value = "作者姓名")
    @TableField("AUTHOR_NAME")
    private String authorName;

    @ApiModelProperty(value = "笔记id")
    @TableField("NOTE_ID")
    private String noteId;

    @ApiModelProperty(value = "笔记名")
    @TableField("NOTE_NAME")
    private String noteName;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;


}
