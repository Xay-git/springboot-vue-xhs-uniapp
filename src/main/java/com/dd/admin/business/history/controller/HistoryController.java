package com.dd.admin.business.history.controller;

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
import com.dd.admin.business.history.entity.History;
import com.dd.admin.business.history.domain.HistoryVo;
import com.dd.admin.business.history.domain.HistoryDto;
import com.dd.admin.business.history.service.HistoryService;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 搜索历史表 前端控制器
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-01-16
 */
@Api(tags = "搜索历史表")
@RestController
public class HistoryController {

    @Autowired
    HistoryService historyService;

    @ApiOperation(value = "搜索历史表-分页列表")
    @ApiOperationSupport(order = 1)
    @GetMapping("/admin/history/page")
    public ResultBean<IPage<HistoryVo>> page(HistoryDto historyDto) {
        IPage<HistoryVo> pageInfo = historyService.selectHistoryPage(historyDto);
        return ResultBean.success(pageInfo);
    }

    @ApiOperation(value = "搜索历史表-列表")
    @ApiOperationSupport(order = 2)
    @GetMapping("/admin/history/list")
    public ResultBean<List<HistoryVo>> list(HistoryDto historyDto) {
        List<HistoryVo> list = historyService.selectHistoryList(historyDto);
        return ResultBean.success(list);
    }

    @ApiOperation(value = "搜索历史表-添加")
    @ApiOperationSupport(order = 3)
    @PostMapping("/admin/history/add")
    public ResultBean<History> add(@RequestBody @Validated HistoryDto historyDto) {
        History history = BeanUtil.copyProperties(historyDto, History.class);
        historyService.save(history);
        return ResultBean.success(history);
    }

    @ApiOperation(value = "搜索历史表-查询")
    @ApiOperationSupport(order = 4)
    @GetMapping("/admin/history/{historyId}")
    public ResultBean<HistoryVo> get(@PathVariable @NotBlank String historyId) {
        History history = historyService.getById(historyId);
        HistoryVo historyVo = BeanUtil.copyProperties(history,HistoryVo.class);
        return ResultBean.success(historyVo);
    }

    @ApiOperation(value = "搜索历史表-修改")
    @ApiOperationSupport(order = 5)
    @PostMapping("/admin/history/update")
    public ResultBean<History> update(@RequestBody @Validated(UpdateGroup.class) HistoryDto historyDto) {
        History history = BeanUtil.copyProperties(historyDto, History.class);
        historyService.updateById(history);
        return ResultBean.success(history);
    }

    @ApiOperation(value = "搜索历史表-删除")
    @ApiOperationSupport(order = 6)
    @GetMapping("/admin/history/delete/{historyId}")
    public ResultBean<History> delete(@PathVariable @NotBlank String historyId) {
        Boolean b = historyService.removeById(historyId);
        return ResultBean.success(b);
    }
}
