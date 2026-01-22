package com.dd.admin.business.starNotes.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.business.upNotes.entity.UpNotes;
import com.dd.admin.common.model.PageFactory;
import com.dd.admin.business.starNotes.entity.StarNotes;
import com.dd.admin.business.starNotes.mapper.StarNotesMapper;
import com.dd.admin.business.starNotes.service.StarNotesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.dd.admin.business.starNotes.domain.StarNotesVo;
import com.dd.admin.business.starNotes.domain.StarNotesDto;
import java.util.List;

/**
 * <p>
 * 收藏笔记列表 服务实现类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-24
 */
@Service
public class StarNotesServiceImpl extends ServiceImpl<StarNotesMapper, StarNotes> implements StarNotesService {

    @Override
    public IPage<StarNotesVo> selectStarNotesPage(StarNotesDto starNotesDto) {
        Page page = PageFactory.defaultPage();
        return baseMapper.selectStarNotesPage(page,starNotesDto);
    }

    @Override
    public List<StarNotesVo> selectStarNotesList(StarNotesDto starNotesDto) {
        return baseMapper.selectStarNotesList(starNotesDto);
    }

    @Override
    public StarNotes selectOneByFollowId(String noteId, String followId) {
        LambdaQueryWrapper<StarNotes> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StarNotes::getNoteId,noteId);
        queryWrapper.eq(StarNotes::getFollowId,followId);
        return this.getOne(queryWrapper);
    }

    @Override
    public Integer selectUnReadCount(String authorId) {
        LambdaQueryWrapper<StarNotes> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StarNotes::getAuthorId,authorId);
        queryWrapper.eq(StarNotes::getMessageStatus,0);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public void readMessage(String authorId) {
        LambdaUpdateWrapper<StarNotes> queryWrapper = new LambdaUpdateWrapper();
        queryWrapper.eq(StarNotes::getAuthorId,authorId);
        queryWrapper.set(StarNotes::getMessageStatus,1);
        this.update(queryWrapper);
    }

}
