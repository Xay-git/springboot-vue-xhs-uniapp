package com.dd.admin.business.topics.domain;

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
 * 话题返回对象
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-04-14
 */
@Data
@ApiModel(value="话题返回对象")
public class TopicsVo {


    @ApiModelProperty(value = "话题唯一标识，自增")
    private String topicId;

    @ApiModelProperty(value = "话题名称")
    private String topicName;

    @ApiModelProperty(value = "话题详细描述")
    private String topicDescription;

    @ApiModelProperty(value = "话题创建时间")
    private Date createTime;

    @ApiModelProperty(value = "话题信息更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "话题热度，用于排序和筛选")
    private Integer topicPopularity;

    @ApiModelProperty(value = "话题下关联的笔记数量")
    private Integer noteCount;

    @ApiModelProperty(value = "是否为热门话题标识")
    private Boolean isHot;


}
