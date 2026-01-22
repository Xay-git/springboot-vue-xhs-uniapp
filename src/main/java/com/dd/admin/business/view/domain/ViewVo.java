package com.dd.admin.business.view.domain;

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
 * 查看返回对象
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-01-15
 */
@Data
@ApiModel(value="查看返回对象")
public class ViewVo {


    @ApiModelProperty(value = "查看id")
    private String viewId;

    @ApiModelProperty(value = "作者id")
    private String authorId;

    @ApiModelProperty(value = "作者姓名")
    private String authorName;

    @ApiModelProperty(value = "笔记id")
    private String noteId;

    @ApiModelProperty(value = "笔记名")
    private String noteName;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}
