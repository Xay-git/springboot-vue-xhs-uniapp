package com.dd.admin.business.note.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.business.note.domain.ReplayMeVo;
import com.dd.admin.business.note.domain.UpMeVo;
import com.dd.admin.business.noteImg.domain.NoteImgVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.business.note.entity.Note;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.admin.business.note.domain.NoteVo;
import com.dd.admin.business.note.domain.NoteDto;

import java.util.List;

/**
 * <p>
 * 笔记表 Mapper 接口
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-09
 */
@Mapper
public interface NoteMapper extends BaseMapper<Note> {

    IPage<NoteVo> selectNotePage(Page<NoteVo> page, @Param("noteDto") NoteDto noteDto);

    List<NoteVo> selectNoteList(@Param("noteDto") NoteDto noteDto);

    NoteVo selectNoteDetail( @Param("noteDto") NoteDto noteDto);

    IPage<NoteVo> selectMineUpNotes(Page<NoteVo> page, @Param("followId")String followId);
    IPage<NoteVo> selectMineStarNotes(Page<NoteVo> page, @Param("followId")String followId);

    IPage<UpMeVo> selectUpMeNotes(Page<NoteVo> page, @Param("authorId")String authorId);
    IPage<ReplayMeVo> selectReplyMeNotes(Page<NoteVo> page, @Param("authorId")String authorId);

    IPage<NoteVo> selectNoteListLike(Page<NoteVo> page, @Param("noteDto") NoteDto noteDto);
    List<NoteVo> selectHotNoteList();
}
