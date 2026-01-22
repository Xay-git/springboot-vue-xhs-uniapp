package com.dd.admin.business.history.domain;

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
 * 搜索历史表返回对象
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-01-16
 */
@Data
@ApiModel(value="搜索历史表返回对象")
public class HistoryVo {


    private String historyId;

    @ApiModelProperty(value = "博主id")
    private String authorId;

    @ApiModelProperty(value = "博主名")
    private String aurhorName;

    @ApiModelProperty(value = "搜索内容")
    private String content;

    @ApiModelProperty(value = "0正常 1删除")
    private Integer deleted;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}
