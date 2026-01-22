package com.dd.admin.business.receive.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.business.chat.entity.Chat;
import com.dd.admin.business.follow.entity.Follow;
import com.dd.admin.business.notice.entity.Notice;
import com.dd.admin.common.model.PageFactory;
import com.dd.admin.business.receive.entity.Receive;
import com.dd.admin.business.receive.mapper.ReceiveMapper;
import com.dd.admin.business.receive.service.ReceiveService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.dd.admin.business.receive.domain.ReceiveVo;
import com.dd.admin.business.receive.domain.ReceiveDto;
import java.util.List;

/**
 * <p>
 * 接收消息表 服务实现类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-01-14
 */
@Service
public class ReceiveServiceImpl extends ServiceImpl<ReceiveMapper, Receive> implements ReceiveService {

    @Override
    public IPage<ReceiveVo> selectReceivePage(ReceiveDto receiveDto) {
        Page page = PageFactory.defaultPage();
        return baseMapper.selectReceivePage(page,receiveDto);
    }

    @Override
    public List<ReceiveVo> selectReceiveList(ReceiveDto receiveDto) {
        return baseMapper.selectReceiveList(receiveDto);
    }

    @Override
    public Integer selectUnReadCount(String authorId) {
        LambdaQueryWrapper<Receive> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Receive::getAuthorId,authorId);
        queryWrapper.eq(Receive::getReceiveStatus,0);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public ReceiveVo selectReceiveLast(String authorId) {
        return baseMapper.selectReceiveLast(authorId);
    }

    @Override
    public void readMessage(String authorId) {
        LambdaUpdateWrapper<Receive> queryWrapper = new LambdaUpdateWrapper();
        queryWrapper.eq(Receive::getAuthorId,authorId);
        queryWrapper.set(Receive::getReceiveStatus,1);
        this.update(queryWrapper);
    }
}
