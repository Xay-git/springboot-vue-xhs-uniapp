package com.dd.admin.business.upNotes.controller;

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
import com.dd.admin.business.upNotes.entity.UpNotes;
import com.dd.admin.business.upNotes.domain.UpNotesVo;
import com.dd.admin.business.upNotes.domain.UpNotesDto;
import com.dd.admin.business.upNotes.service.UpNotesService;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 点赞笔记列表 前端控制器
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-24
 */
@Api(tags = "点赞笔记列表")
@RestController
public class UpNotesController {

    @Autowired
    UpNotesService upNotesService;

    @ApiOperation(value = "点赞笔记列表-分页列表")
    @ApiOperationSupport(order = 1)
    @GetMapping("/admin/upNotes/page")
    public ResultBean<IPage<UpNotesVo>> page(UpNotesDto upNotesDto) {
        IPage<UpNotesVo> pageInfo = upNotesService.selectUpNotesPage(upNotesDto);
        return ResultBean.success(pageInfo);
    }

    @ApiOperation(value = "点赞笔记列表-列表")
    @ApiOperationSupport(order = 2)
    @GetMapping("/admin/upNotes/list")
    public ResultBean<List<UpNotesVo>> list(UpNotesDto upNotesDto) {
        List<UpNotesVo> list = upNotesService.selectUpNotesList(upNotesDto);
        return ResultBean.success(list);
    }

    @ApiOperation(value = "点赞笔记列表-添加")
    @ApiOperationSupport(order = 3)
    @PostMapping("/admin/upNotes/add")
    public ResultBean<UpNotes> add(@RequestBody @Validated UpNotesDto upNotesDto) {
        UpNotes upNotes = BeanUtil.copyProperties(upNotesDto, UpNotes.class);
        upNotesService.save(upNotes);
        return ResultBean.success(upNotes);
    }

    @ApiOperation(value = "点赞笔记列表-查询")
    @ApiOperationSupport(order = 4)
    @GetMapping("/admin/upNotes/{upNotesId}")
    public ResultBean<UpNotesVo> get(@PathVariable @NotBlank String upNotesId) {
        UpNotes upNotes = upNotesService.getById(upNotesId);
        UpNotesVo upNotesVo = BeanUtil.copyProperties(upNotes,UpNotesVo.class);
        return ResultBean.success(upNotesVo);
    }

    @ApiOperation(value = "点赞笔记列表-修改")
    @ApiOperationSupport(order = 5)
    @PostMapping("/admin/upNotes/update")
    public ResultBean<UpNotes> update(@RequestBody @Validated(UpdateGroup.class) UpNotesDto upNotesDto) {
        UpNotes upNotes = BeanUtil.copyProperties(upNotesDto, UpNotes.class);
        upNotesService.updateById(upNotes);
        return ResultBean.success(upNotes);
    }

    @ApiOperation(value = "点赞笔记列表-删除")
    @ApiOperationSupport(order = 6)
    @GetMapping("/admin/upNotes/delete/{upNotesId}")
    public ResultBean<UpNotes> delete(@PathVariable @NotBlank String upNotesId) {
        Boolean b = upNotesService.removeById(upNotesId);
        return ResultBean.success(b);
    }
}
