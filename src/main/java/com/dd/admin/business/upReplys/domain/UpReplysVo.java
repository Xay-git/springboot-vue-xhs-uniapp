package com.dd.admin.business.upReplys.domain;

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
 * 笔记点赞表返回对象
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-25
 */
@Data
@ApiModel(value="笔记点赞表返回对象")
public class UpReplysVo {


    private String replyUpId;

    private String replyId;

    private String replyContent;

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


}
