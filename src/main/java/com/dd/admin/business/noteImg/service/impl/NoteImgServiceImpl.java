package com.dd.admin.business.noteImg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.common.model.PageFactory;
import com.dd.admin.business.noteImg.entity.NoteImg;
import com.dd.admin.business.noteImg.mapper.NoteImgMapper;
import com.dd.admin.business.noteImg.service.NoteImgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.dd.admin.business.noteImg.domain.NoteImgVo;
import com.dd.admin.business.noteImg.domain.NoteImgDto;
import com.dd.admin.business.file.service.FileService;
import com.dd.admin.common.util.MinioUrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 笔记包含的图片 服务实现类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-23
 */
@Service
public class NoteImgServiceImpl extends ServiceImpl<NoteImgMapper, NoteImg> implements NoteImgService {

    @Autowired
    private MinioUrlUtil minioUrlUtil;

    @Override
    public IPage<NoteImgVo> selectNoteImgPage(NoteImgDto noteImgDto) {
        Page page = PageFactory.defaultPage();
        return baseMapper.selectNoteImgPage(page,noteImgDto);
    }

    @Override
    public List<NoteImgVo> selectNoteImgList(NoteImgDto noteImgDto) {
        return baseMapper.selectNoteImgList(noteImgDto);
    }

    @Override
    public Integer deleteNoteImgByNoteId(String noteId) {
        LambdaUpdateWrapper<NoteImg> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(NoteImg::getNoteId, noteId);
        return baseMapper.delete(updateWrapper);
    }

    @Autowired
    private FileService fileService;

    @Override
    public List<String> getImgsByNoteId(String noteId) {
        LambdaQueryWrapper<NoteImg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(NoteImg::getNoteId, noteId);
        queryWrapper.orderByAsc(NoteImg::getImgSort);
        return list(queryWrapper).stream()
                .map(noteImg -> minioUrlUtil.refreshMinioUrl(noteImg.getImgUrl()))
                .collect(Collectors.toList());
    }
}
