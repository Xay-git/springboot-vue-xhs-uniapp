package com.dd.admin.business.follow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.business.starNotes.entity.StarNotes;
import com.dd.admin.business.upNotes.entity.UpNotes;
import com.dd.admin.common.model.PageFactory;
import com.dd.admin.business.follow.entity.Follow;
import com.dd.admin.business.follow.mapper.FollowMapper;
import com.dd.admin.business.follow.service.FollowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.dd.admin.business.follow.domain.FollowVo;
import com.dd.admin.business.follow.domain.FollowDto;
import java.util.List;

/**
 * <p>
 * 关注表 服务实现类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-24
 */
@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements FollowService {

    @Override
    public IPage<FollowVo> selectFollowPage(FollowDto followDto) {
        Page page = PageFactory.defaultPage();
        return baseMapper.selectFollowPage(page,followDto);
    }

    @Override
    public List<FollowVo> selectFollowList(FollowDto followDto) {
        return baseMapper.selectFollowList(followDto);
    }

    @Override
    public List<Follow> selectFollowListByFollowId(String followId) {
        LambdaQueryWrapper<Follow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Follow::getFollowId,followId);
        return this.list(queryWrapper);
    }

    @Override
    public Follow selectOneByFollowId(String authorId, String followId) {
        LambdaQueryWrapper<Follow> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Follow::getAuthorId,authorId);
        queryWrapper.eq(Follow::getFollowId,followId);
        return this.getOne(queryWrapper);
    }

    @Override
    public List<FollowVo> selectMyFollowList(String authorId,String targetId) {
        return baseMapper.selectMyFollowList(authorId,targetId);
    }

    @Override
    public List<FollowVo> selectFollowMeList(String authorId,String targetId) {
        return baseMapper.selectFollowMeList(authorId,targetId);
    }

    @Override
    public List<FollowVo> getMyFriends(String authorId) {
        return baseMapper.getMyFriends(authorId);
    }

    @Override
    public Integer selectUnReadCount(String authorId) {
        LambdaQueryWrapper<Follow> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Follow::getAuthorId,authorId);
        queryWrapper.ne(Follow::getFollowId,authorId);
        queryWrapper.eq(Follow::getMessageStatus,0);
        return baseMapper.selectCount(queryWrapper);
    }
    @Override
    public void readMessage(String authorId) {
        LambdaUpdateWrapper<Follow> queryWrapper = new LambdaUpdateWrapper();
        queryWrapper.eq(Follow::getAuthorId,authorId);
        queryWrapper.set(Follow::getMessageStatus,1);
        this.update(queryWrapper);
    }
}
