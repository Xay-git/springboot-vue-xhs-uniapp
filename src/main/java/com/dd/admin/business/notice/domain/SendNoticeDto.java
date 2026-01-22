package com.dd.admin.business.notice.domain;

import com.dd.admin.business.author.entity.Author;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;


/**
 * <p>
 * 通知公告表返回对象
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-01-14
 */
@Data
@ApiModel(value="通知公告表接收对象")
public class SendNoticeDto {

    @ApiModelProperty(value = "通知id")
    private String noticeId;

    @ApiModelProperty(value = "作者集合 没有则全部发布")
    private List<Author> authorList;

}
