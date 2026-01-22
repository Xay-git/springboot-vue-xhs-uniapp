package com.dd.admin.business.receive.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.business.receive.entity.Receive;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.admin.business.receive.domain.ReceiveVo;
import com.dd.admin.business.receive.domain.ReceiveDto;

import java.util.List;

/**
 * <p>
 * 接收消息表 Mapper 接口
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-01-14
 */
@Mapper
public interface ReceiveMapper extends BaseMapper<Receive> {

    IPage<ReceiveVo> selectReceivePage(Page<ReceiveVo> page, @Param("receiveDto") ReceiveDto receiveDto);

    List<ReceiveVo> selectReceiveList(@Param("receiveDto") ReceiveDto receiveDto);

    ReceiveVo selectReceiveLast(@Param("authorId")String authorId);
}
