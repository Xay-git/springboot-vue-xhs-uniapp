package com.dd.admin.business.starNotes.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.business.starNotes.entity.StarNotes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.admin.business.starNotes.domain.StarNotesVo;
import com.dd.admin.business.starNotes.domain.StarNotesDto;

import java.util.List;

/**
 * <p>
 * 收藏笔记列表 Mapper 接口
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-24
 */
@Mapper
public interface StarNotesMapper extends BaseMapper<StarNotes> {

    IPage<StarNotesVo> selectStarNotesPage(Page<StarNotesVo> page, @Param("starNotesDto") StarNotesDto starNotesDto);

    List<StarNotesVo> selectStarNotesList(@Param("starNotesDto") StarNotesDto starNotesDto);
}
