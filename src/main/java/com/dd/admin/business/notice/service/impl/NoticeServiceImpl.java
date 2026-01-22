package com.dd.admin.business.notice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.business.chat.entity.Chat;
import com.dd.admin.common.model.PageFactory;
import com.dd.admin.business.notice.entity.Notice;
import com.dd.admin.business.notice.mapper.NoticeMapper;
import com.dd.admin.business.notice.service.NoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.dd.admin.business.notice.domain.NoticeVo;
import com.dd.admin.business.notice.domain.NoticeDto;
import java.util.List;

/**
 * <p>
 * 通知公告表 服务实现类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-01-14
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Override
    public IPage<NoticeVo> selectNoticePage(NoticeDto noticeDto) {
        Page page = PageFactory.defaultPage();
        return baseMapper.selectNoticePage(page,noticeDto);
    }

    @Override
    public List<NoticeVo> selectNoticeList(NoticeDto noticeDto) {
        return baseMapper.selectNoticeList(noticeDto);
    }


}
