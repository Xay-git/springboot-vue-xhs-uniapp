package com.dd.admin.business.note.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.business.note.domain.ReplayMeVo;
import com.dd.admin.business.note.domain.UpMeVo;
import com.dd.admin.business.note.entity.Note;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.admin.business.note.domain.NoteVo;
import com.dd.admin.business.note.domain.NoteDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 笔记表 服务类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-09
 */
public interface NoteService extends IService<Note> {

    //笔记表-分页列表
    IPage<NoteVo> selectNotePage(NoteDto noteDto);
    //笔记表-列表
    List<NoteVo> selectNoteList(NoteDto noteDto);

    NoteVo selectNoteDetail(NoteDto noteDto);

    IPage<NoteVo> selectMineUpNotes( String followId);

    IPage<NoteVo> selectMineStarNotes( String followId);

    IPage<UpMeVo> selectUpMeNotes(String authorId);

    IPage<ReplayMeVo> selectReplyMeNotes(String authorId);


    IPage<NoteVo> selectNoteListLike(NoteDto noteDto);

    List<NoteVo> selectHotNoteList();
    
    /**
     * 处理笔记图片关联
     * @param noteId 笔记ID
     * @param authorId 作者ID
     * @param imgs 图片ID列表
     */
    void handleNoteImages(String noteId, String authorId, List<String> imgs);
}
