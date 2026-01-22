package com.dd.admin.business.noteImg.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.business.noteImg.entity.NoteImg;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.admin.business.noteImg.domain.NoteImgVo;
import com.dd.admin.business.noteImg.domain.NoteImgDto;
import java.util.List;

/**
 * <p>
 * 笔记包含的图片 服务类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-23
 */
public interface NoteImgService extends IService<NoteImg> {

    //笔记包含的图片-分页列表
    IPage<NoteImgVo> selectNoteImgPage(NoteImgDto noteImgDto);

    //笔记包含的图片-列表
    List<NoteImgVo> selectNoteImgList(NoteImgDto noteImgDto);

    Integer deleteNoteImgByNoteId(String noteId);

    List<String> getImgsByNoteId(String noteId);
}
