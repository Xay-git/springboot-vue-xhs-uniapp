package com.dd.admin.business.starNotes.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.business.starNotes.entity.StarNotes;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.admin.business.starNotes.domain.StarNotesVo;
import com.dd.admin.business.starNotes.domain.StarNotesDto;
import com.dd.admin.business.upNotes.domain.UpNotesDto;
import com.dd.admin.business.upNotes.domain.UpNotesVo;
import com.dd.admin.business.upNotes.entity.UpNotes;

import java.util.List;

/**
 * <p>
 * 收藏笔记列表 服务类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-24
 */
public interface StarNotesService extends IService<StarNotes> {

    //收藏笔记列表-分页列表
    IPage<StarNotesVo> selectStarNotesPage(StarNotesDto starNotesDto);

    //收藏笔记列表-列表
    List<StarNotesVo> selectStarNotesList(StarNotesDto starNotesDto);

    StarNotes selectOneByFollowId(String noteId, String followId);

    //未读聊天消息的数量
    Integer selectUnReadCount(String authorId);
    void readMessage(String authorId);

}
