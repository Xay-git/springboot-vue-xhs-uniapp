package com.dd.admin.business.upNotes.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.business.upNotes.entity.UpNotes;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.admin.business.upNotes.domain.UpNotesVo;
import com.dd.admin.business.upNotes.domain.UpNotesDto;
import java.util.List;

/**
 * <p>
 * 点赞笔记列表 服务类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-24
 */
public interface UpNotesService extends IService<UpNotes> {

    //点赞笔记列表-分页列表
    IPage<UpNotesVo> selectUpNotesPage(UpNotesDto upNotesDto);

    //点赞笔记列表-列表
    List<UpNotesVo> selectUpNotesList(UpNotesDto upNotesDto);

    List<UpNotesVo> selectAllUpCount();

    UpNotes selectOneByFollowId(String noteId ,String followId);

    //未读聊天消息的数量
    Integer selectUnReadCount(String authorId);
    void readMessage(String authorId);

}
