package com.dd.admin.business.starNotes.controller;

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
import com.dd.admin.business.starNotes.entity.StarNotes;
import com.dd.admin.business.starNotes.domain.StarNotesVo;
import com.dd.admin.business.starNotes.domain.StarNotesDto;
import com.dd.admin.business.starNotes.service.StarNotesService;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 收藏笔记列表 前端控制器
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-24
 */
@Api(tags = "收藏笔记列表")
@RestController
public class StarNotesController {

    @Autowired
    StarNotesService starNotesService;

    @ApiOperation(value = "收藏笔记列表-分页列表")
    @ApiOperationSupport(order = 1)
    @GetMapping("/admin/starNotes/page")
    public ResultBean<IPage<StarNotesVo>> page(StarNotesDto starNotesDto) {
        IPage<StarNotesVo> pageInfo = starNotesService.selectStarNotesPage(starNotesDto);
        return ResultBean.success(pageInfo);
    }

    @ApiOperation(value = "收藏笔记列表-列表")
    @ApiOperationSupport(order = 2)
    @GetMapping("/admin/starNotes/list")
    public ResultBean<List<StarNotesVo>> list(StarNotesDto starNotesDto) {
        List<StarNotesVo> list = starNotesService.selectStarNotesList(starNotesDto);
        return ResultBean.success(list);
    }

    @ApiOperation(value = "收藏笔记列表-添加")
    @ApiOperationSupport(order = 3)
    @PostMapping("/admin/starNotes/add")
    public ResultBean<StarNotes> add(@RequestBody @Validated StarNotesDto starNotesDto) {
        StarNotes starNotes = BeanUtil.copyProperties(starNotesDto, StarNotes.class);
        starNotesService.save(starNotes);
        return ResultBean.success(starNotes);
    }

    @ApiOperation(value = "收藏笔记列表-查询")
    @ApiOperationSupport(order = 4)
    @GetMapping("/admin/starNotes/{starNotesId}")
    public ResultBean<StarNotesVo> get(@PathVariable @NotBlank String starNotesId) {
        StarNotes starNotes = starNotesService.getById(starNotesId);
        StarNotesVo starNotesVo = BeanUtil.copyProperties(starNotes,StarNotesVo.class);
        return ResultBean.success(starNotesVo);
    }

    @ApiOperation(value = "收藏笔记列表-修改")
    @ApiOperationSupport(order = 5)
    @PostMapping("/admin/starNotes/update")
    public ResultBean<StarNotes> update(@RequestBody @Validated(UpdateGroup.class) StarNotesDto starNotesDto) {
        StarNotes starNotes = BeanUtil.copyProperties(starNotesDto, StarNotes.class);
        starNotesService.updateById(starNotes);
        return ResultBean.success(starNotes);
    }

    @ApiOperation(value = "收藏笔记列表-删除")
    @ApiOperationSupport(order = 6)
    @GetMapping("/admin/starNotes/delete/{starNotesId}")
    public ResultBean<StarNotes> delete(@PathVariable @NotBlank String starNotesId) {
        Boolean b = starNotesService.removeById(starNotesId);
        return ResultBean.success(b);
    }
}
