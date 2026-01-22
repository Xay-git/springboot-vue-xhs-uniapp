package com.dd.admin.business.receive.domain;

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
 * 接收消息表返回对象
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-01-14
 */
@Data
@ApiModel(value="接收消息表返回对象")
public class ReceiveVo {


    @ApiModelProperty(value = "接收id")
    private String receiveId;

    @ApiModelProperty(value = "博主id")
    private String authorId;

    @ApiModelProperty(value = "博主名")
    private String authorName;

    @ApiModelProperty(value = "通知id")
    private String noticeId;

    @ApiModelProperty(value = "通知标题")
    private String noticeTitle;

    @ApiModelProperty(value = "通知标题")
    private String noticeContent;


    @ApiModelProperty(value = "0正常 1删除")
    private Integer deleted;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "0 发送 1已读")
    private Integer receiveStatus;

    @ApiModelProperty(value = "跳转url")
    private String redirectUrl;
}
