package com.dd.admin.business.upReplys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.business.upReplys.entity.UpReplys;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.admin.business.upReplys.domain.UpReplysVo;
import com.dd.admin.business.upReplys.domain.UpReplysDto;
import java.util.List;

/**
 * <p>
 * 笔记点赞表 服务类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-25
 */
public interface UpReplysService extends IService<UpReplys> {

    //笔记点赞表-分页列表
    IPage<UpReplysVo> selectUpReplysPage(UpReplysDto upReplysDto);

    //笔记点赞表-列表
    List<UpReplysVo> selectUpReplysList(UpReplysDto upReplysDto);

    UpReplys selectOneByFollowId(String replyId,String followId);

}
