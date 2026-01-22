package com.dd.admin.business.topics.entity;

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
 * 话题
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-04-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("business_topics")
@ApiModel(value="Topics对象", description="话题")
public class Topics implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "话题唯一标识，自增")
    @TableId(value = "TOPIC_ID", type = IdType.ASSIGN_UUID)
    private String topicId;

    @ApiModelProperty(value = "话题名称")
    @TableField("TOPIC_NAME")
    private String topicName;

    @ApiModelProperty(value = "话题详细描述")
    @TableField("TOPIC_DESCRIPTION")
    private String topicDescription;

    @ApiModelProperty(value = "话题创建时间")
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "话题信息更新时间")
    @TableField(value = "UPDATE_TIME", fill = FieldFill.UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "话题热度，用于排序和筛选")
    @TableField("TOPIC_POPULARITY")
    private Integer topicPopularity;

    @ApiModelProperty(value = "话题下关联的笔记数量")
    @TableField("NOTE_COUNT")
    private Integer noteCount;

    @ApiModelProperty(value = "是否为热门话题标识")
    @TableField("IS_HOT")
    private Boolean isHot;


}
