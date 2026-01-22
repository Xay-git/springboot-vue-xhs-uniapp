package com.dd.admin.business.operationLog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.common.model.PageFactory;
import com.dd.admin.business.operationLog.entity.OperationLog;
import com.dd.admin.business.operationLog.mapper.OperationLogMapper;
import com.dd.admin.business.operationLog.service.OperationLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.dd.admin.business.operationLog.domain.OperationLogVo;
import com.dd.admin.business.operationLog.domain.OperationLogDto;
import java.util.List;

/**
 * <p>
 * 操作日志 服务实现类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-06-07
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements OperationLogService {

    @Override
    public IPage<OperationLogVo> selectOperationLogPage(OperationLogDto operationLogDto) {
        Page page = PageFactory.defaultPage();
        return baseMapper.selectOperationLogPage(page,operationLogDto);
    }

    @Override
    public List<OperationLogVo> selectOperationLogList(OperationLogDto operationLogDto) {
        return baseMapper.selectOperationLogList(operationLogDto);
    }
}
