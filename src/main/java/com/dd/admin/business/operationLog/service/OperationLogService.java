package com.dd.admin.business.operationLog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.business.operationLog.entity.OperationLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.admin.business.operationLog.domain.OperationLogVo;
import com.dd.admin.business.operationLog.domain.OperationLogDto;
import java.util.List;

/**
 * <p>
 * 操作日志 服务类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-06-07
 */
public interface OperationLogService extends IService<OperationLog> {

    //操作日志-分页列表
    IPage<OperationLogVo> selectOperationLogPage(OperationLogDto operationLogDto);

    //操作日志-列表
    List<OperationLogVo> selectOperationLogList(OperationLogDto operationLogDto);

}
