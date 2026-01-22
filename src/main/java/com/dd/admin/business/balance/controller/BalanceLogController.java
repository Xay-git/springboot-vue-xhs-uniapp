package com.dd.admin.business.balance.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.business.balance.service.BalanceLogService;
import com.dd.admin.business.balance.vo.BalanceLogVo;
import com.dd.admin.business.balance.dto.BalanceLogDto;
import com.dd.admin.business.balance.dto.BalanceLogAddDto;
import com.dd.admin.common.aop.operationLog.aop.OperLog;
import com.dd.admin.common.aop.operationLog.aop.OperType;
import com.dd.admin.common.model.result.ResultBean;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * <p>
 * 余额变动记录 前端控制器
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-09-11
 */
@RestController
@Api(tags = "余额日志")
public class BalanceLogController {
    @Autowired
    private BalanceLogService balanceLogService;
    
    @GetMapping("/admin/balance/log/page")
    @ApiOperation("分页查询余额日志")
    @ApiOperationSupport(order = 1)
    @OperLog(operModule = "余额日志", operType = OperType.QUERY, operDesc = "分页查询余额日志")
    public ResultBean<IPage<BalanceLogVo>> selectBalanceLogPage(BalanceLogDto balanceLogDto) {
        IPage<BalanceLogVo> page = balanceLogService.selectBalanceLogPage(balanceLogDto);
        return ResultBean.success(page);
    }

    @GetMapping("/admin/balance/log/list")
    @ApiOperation("查询余额日志列表")
    @ApiOperationSupport(order = 2)
    @OperLog(operModule = "余额日志", operType = OperType.QUERY, operDesc = "查询余额日志列表")
    public ResultBean<List<BalanceLogVo>> selectBalanceLogList(BalanceLogDto balanceLogDto) {
        List<BalanceLogVo> list = balanceLogService.selectBalanceLogList(balanceLogDto);
        return ResultBean.success(list);
    }

    @GetMapping("/admin/balance/log/detail/{balanceLogId}")
    @ApiOperation("查询余额日志详情")
    @ApiOperationSupport(order = 3)
    @OperLog(operModule = "余额日志", operType = OperType.QUERY, operDesc = "查询余额日志详情")
    public ResultBean<BalanceLogVo> selectBalanceLogById(@PathVariable @NotBlank String balanceLogId) {
        BalanceLogVo balanceLogVo = balanceLogService.selectBalanceLogById(balanceLogId);
        return ResultBean.success(balanceLogVo);
    }
    
    @PostMapping("/admin/balance/log/add")
    @ApiOperation("新增余额日志")
    @ApiOperationSupport(order = 4)
    @OperLog(operModule = "余额日志", operType = OperType.ADD, operDesc = "新增余额日志")
    public ResultBean<String> addBalanceLog(@RequestBody @Validated BalanceLogAddDto balanceLogAddDto) {
        balanceLogService.addBalanceLog(balanceLogAddDto);
        return ResultBean.success("新增成功");
    }
    
    @PostMapping("/admin/balance/log/update")
    @ApiOperation("修改余额日志")
    @ApiOperationSupport(order = 5)
    @OperLog(operModule = "余额日志", operType = OperType.EDIT, operDesc = "修改余额日志")
    public ResultBean<String> updateBalanceLog(@RequestBody @Validated BalanceLogDto balanceLogDto) {
        balanceLogService.updateBalanceLog(balanceLogDto);
        return ResultBean.success("修改成功");
    }

    @PostMapping("/admin/balance/log/delete/{balanceLogId}")
    @ApiOperation("删除余额日志")
    @ApiOperationSupport(order = 6)
    @OperLog(operModule = "余额日志", operType = OperType.REMOVE, operDesc = "删除余额日志")
    public ResultBean<String> deleteBalanceLog(@PathVariable @NotBlank String balanceLogId) {
        balanceLogService.deleteBalanceLog(balanceLogId);
        return ResultBean.success("删除成功");
    }
}

