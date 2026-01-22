package com.dd.admin.business.noteImg.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.business.noteImg.entity.NoteImg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.admin.business.noteImg.domain.NoteImgVo;
import com.dd.admin.business.noteImg.domain.NoteImgDto;

import java.util.List;

/**
 * <p>
 * 笔记包含的图片 Mapper 接口
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-23
 */
@Mapper
public interface NoteImgMapper extends BaseMapper<NoteImg> {

    IPage<NoteImgVo> selectNoteImgPage(Page<NoteImgVo> page, @Param("noteImgDto") NoteImgDto noteImgDto);

    List<NoteImgVo> selectNoteImgList(@Param("noteImgDto") NoteImgDto noteImgDto);

}
