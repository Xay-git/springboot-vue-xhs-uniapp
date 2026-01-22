package com.dd.admin.business.upNotes.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.business.chat.entity.Chat;
import com.dd.admin.common.model.PageFactory;
import com.dd.admin.business.upNotes.entity.UpNotes;
import com.dd.admin.business.upNotes.mapper.UpNotesMapper;
import com.dd.admin.business.upNotes.service.UpNotesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.dd.admin.business.upNotes.domain.UpNotesVo;
import com.dd.admin.business.upNotes.domain.UpNotesDto;
import java.util.List;

/**
 * <p>
 * 点赞笔记列表 服务实现类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-24
 */
@Service
public class UpNotesServiceImpl extends ServiceImpl<UpNotesMapper, UpNotes> implements UpNotesService {

    @Override
    public IPage<UpNotesVo> selectUpNotesPage(UpNotesDto upNotesDto) {
        Page page = PageFactory.defaultPage();
        return baseMapper.selectUpNotesPage(page,upNotesDto);
    }

    @Override
    public List<UpNotesVo> selectUpNotesList(UpNotesDto upNotesDto) {
        return baseMapper.selectUpNotesList(upNotesDto);
    }

    @Override
    public List<UpNotesVo> selectAllUpCount() {
        return baseMapper.selectAllUpCount();
    }

    @Override
    public UpNotes selectOneByFollowId(String noteId, String followId) {
        LambdaQueryWrapper<UpNotes> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(UpNotes::getNoteId,noteId);
        queryWrapper.eq(UpNotes::getFollowId,followId);
        return this.getOne(queryWrapper);
    }

    @Override
    public Integer selectUnReadCount(String authorId) {
        LambdaQueryWrapper<UpNotes> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(UpNotes::getAuthorId,authorId);
        queryWrapper.eq(UpNotes::getMessageStatus,0);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public void readMessage(String authorId) {
        LambdaUpdateWrapper<UpNotes> queryWrapper = new LambdaUpdateWrapper();
        queryWrapper.eq(UpNotes::getAuthorId,authorId);
        queryWrapper.set(UpNotes::getMessageStatus,1);
        this.update(queryWrapper);
    }
}
