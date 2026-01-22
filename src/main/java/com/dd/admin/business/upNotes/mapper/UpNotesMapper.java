package com.dd.admin.business.upNotes.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.business.upNotes.entity.UpNotes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.admin.business.upNotes.domain.UpNotesVo;
import com.dd.admin.business.upNotes.domain.UpNotesDto;

import java.util.List;

/**
 * <p>
 * 点赞笔记列表 Mapper 接口
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-24
 */
@Mapper
public interface UpNotesMapper extends BaseMapper<UpNotes> {

    IPage<UpNotesVo> selectUpNotesPage(Page<UpNotesVo> page, @Param("upNotesDto") UpNotesDto upNotesDto);

    List<UpNotesVo> selectUpNotesList(@Param("upNotesDto") UpNotesDto upNotesDto);

    List<UpNotesVo> selectAllUpCount();


}
