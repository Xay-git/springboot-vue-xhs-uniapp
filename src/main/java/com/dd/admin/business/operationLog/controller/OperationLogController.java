package com.dd.admin.business.operationLog.controller;

import cn.hutool.core.bean.BeanUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.dd.admin.common.model.UpdateGroup;
import com.dd.admin.common.model.result.ResultBean;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotBlank;
import com.dd.admin.business.operationLog.entity.OperationLog;
import com.dd.admin.business.operationLog.domain.OperationLogVo;
import com.dd.admin.business.operationLog.domain.OperationLogDto;
import com.dd.admin.business.operationLog.service.OperationLogService;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 操作日志 前端控制器
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-06-07
 */
@Api(tags = "操作日志")
@RestController
public class OperationLogController {

    @Autowired
    OperationLogService operationLogService;

    @ApiOperation(value = "操作日志-分页列表")
    @ApiOperationSupport(order = 1)
    @GetMapping("/admin/operationLog/page")
    public ResultBean<IPage<OperationLogVo>> page(OperationLogDto operationLogDto) {
        IPage<OperationLogVo> pageInfo = operationLogService.selectOperationLogPage(operationLogDto);
        return ResultBean.success(pageInfo);
    }

    @ApiOperation(value = "操作日志-列表")
    @ApiOperationSupport(order = 2)
    @GetMapping("/admin/operationLog/list")
    public ResultBean<List<OperationLogVo>> list(OperationLogDto operationLogDto) {
        List<OperationLogVo> list = operationLogService.selectOperationLogList(operationLogDto);
        return ResultBean.success(list);
    }

    @ApiOperation(value = "操作日志-添加")
    @ApiOperationSupport(order = 3)
    @PostMapping("/admin/operationLog/add")
    public ResultBean<OperationLog> add(@RequestBody @Validated OperationLogDto operationLogDto) {
        OperationLog operationLog = BeanUtil.copyProperties(operationLogDto, OperationLog.class);
        operationLogService.save(operationLog);
        return ResultBean.success(operationLog);
    }

    @ApiOperation(value = "操作日志-查询")
    @ApiOperationSupport(order = 4)
    @GetMapping("/admin/operationLog/{operationLogId}")
    public ResultBean<OperationLogVo> get(@PathVariable @NotBlank String operationLogId) {
        OperationLog operationLog = operationLogService.getById(operationLogId);
        OperationLogVo operationLogVo = BeanUtil.copyProperties(operationLog,OperationLogVo.class);
        return ResultBean.success(operationLogVo);
    }

    @ApiOperation(value = "操作日志-修改")
    @ApiOperationSupport(order = 5)
    @PostMapping("/admin/operationLog/update")
    public ResultBean<OperationLog> update(@RequestBody @Validated(UpdateGroup.class) OperationLogDto operationLogDto) {
        OperationLog operationLog = BeanUtil.copyProperties(operationLogDto, OperationLog.class);
        operationLogService.updateById(operationLog);
        return ResultBean.success(operationLog);
    }

    @ApiOperation(value = "操作日志-删除")
    @ApiOperationSupport(order = 6)
    @GetMapping("/admin/operationLog/delete/{operationLogId}")
    public ResultBean<OperationLog> delete(@PathVariable @NotBlank String operationLogId) {
        Boolean b = operationLogService.removeById(operationLogId);
        return ResultBean.success(b);
    }
}
