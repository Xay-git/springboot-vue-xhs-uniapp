package com.dd.admin.business.operationLog.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.business.operationLog.entity.OperationLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.admin.business.operationLog.domain.OperationLogVo;
import com.dd.admin.business.operationLog.domain.OperationLogDto;

import java.util.List;

/**
 * <p>
 * 操作日志 Mapper 接口
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-06-07
 */
@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {

    IPage<OperationLogVo> selectOperationLogPage(Page<OperationLogVo> page, @Param("operationLogDto") OperationLogDto operationLogDto);

    List<OperationLogVo> selectOperationLogList(@Param("operationLogDto") OperationLogDto operationLogDto);
}
