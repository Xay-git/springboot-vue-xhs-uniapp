package com.dd.admin.business.notice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.business.notice.entity.Notice;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.admin.business.notice.domain.NoticeVo;
import com.dd.admin.business.notice.domain.NoticeDto;
import java.util.List;

/**
 * <p>
 * 通知公告表 服务类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-01-14
 */
public interface NoticeService extends IService<Notice> {

    //通知公告表-分页列表
    IPage<NoticeVo> selectNoticePage(NoticeDto noticeDto);

    //通知公告表-列表
    List<NoticeVo> selectNoticeList(NoticeDto noticeDto);

}
