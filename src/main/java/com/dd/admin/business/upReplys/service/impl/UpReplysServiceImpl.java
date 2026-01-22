package com.dd.admin.business.upReplys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.business.upNotes.entity.UpNotes;
import com.dd.admin.common.model.PageFactory;
import com.dd.admin.business.upReplys.entity.UpReplys;
import com.dd.admin.business.upReplys.mapper.UpReplysMapper;
import com.dd.admin.business.upReplys.service.UpReplysService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.dd.admin.business.upReplys.domain.UpReplysVo;
import com.dd.admin.business.upReplys.domain.UpReplysDto;
import java.util.List;

/**
 * <p>
 * 笔记点赞表 服务实现类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-25
 */
@Service
public class UpReplysServiceImpl extends ServiceImpl<UpReplysMapper, UpReplys> implements UpReplysService {

    @Override
    public IPage<UpReplysVo> selectUpReplysPage(UpReplysDto upReplysDto) {
        Page page = PageFactory.defaultPage();
        return baseMapper.selectUpReplysPage(page,upReplysDto);
    }

    @Override
    public List<UpReplysVo> selectUpReplysList(UpReplysDto upReplysDto) {
        return baseMapper.selectUpReplysList(upReplysDto);
    }

    @Override
    public UpReplys selectOneByFollowId(String replyId, String followId) {
        LambdaQueryWrapper<UpReplys> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(UpReplys::getReplyId,replyId);
        queryWrapper.eq(UpReplys::getFollowId,followId);
        return this.getOne(queryWrapper);
    }
}
