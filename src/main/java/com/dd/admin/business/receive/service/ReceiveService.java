package com.dd.admin.business.receive.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.business.receive.entity.Receive;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.admin.business.receive.domain.ReceiveVo;
import com.dd.admin.business.receive.domain.ReceiveDto;
import java.util.List;

/**
 * <p>
 * 接收消息表 服务类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-01-14
 */
public interface ReceiveService extends IService<Receive> {

    //接收消息表-分页列表
    IPage<ReceiveVo> selectReceivePage(ReceiveDto receiveDto);

    //接收消息表-列表
    List<ReceiveVo> selectReceiveList(ReceiveDto receiveDto);

    Integer selectUnReadCount(String authorId);

    ReceiveVo selectReceiveLast(String authorId);

    public void readMessage(String authorId);

}
