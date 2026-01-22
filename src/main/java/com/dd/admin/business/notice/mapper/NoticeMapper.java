package com.dd.admin.business.notice.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.business.notice.entity.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.admin.business.notice.domain.NoticeVo;
import com.dd.admin.business.notice.domain.NoticeDto;

import java.util.List;

/**
 * <p>
 * 通知公告表 Mapper 接口
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-01-14
 */
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {

    IPage<NoticeVo> selectNoticePage(Page<NoticeVo> page, @Param("noticeDto") NoticeDto noticeDto);

    List<NoticeVo> selectNoticeList(@Param("noticeDto") NoticeDto noticeDto);
}
