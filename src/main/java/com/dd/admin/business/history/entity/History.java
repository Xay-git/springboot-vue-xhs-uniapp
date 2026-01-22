package com.dd.admin.business.history.entity;

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
 * 搜索历史表
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-01-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("business_history")
@ApiModel(value="History对象", description="搜索历史表")
public class History implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "HISTORY_ID", type = IdType.ASSIGN_UUID)
    private String historyId;

    @ApiModelProperty(value = "博主id")
    @TableField("AUTHOR_ID")
    private String authorId;

    @ApiModelProperty(value = "博主名")
    @TableField("AURHOR_NAME")
    private String aurhorName;

    @ApiModelProperty(value = "搜索内容")
    @TableField("CONTENT")
    private String content;

    @ApiModelProperty(value = "0正常 1删除")
    @TableField("DELETED")
    @TableLogic
    private Integer deleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;


}
