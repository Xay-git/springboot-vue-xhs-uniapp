package com.dd.admin.business.noteImg.controller;

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
import com.dd.admin.business.noteImg.entity.NoteImg;
import com.dd.admin.business.noteImg.domain.NoteImgVo;
import com.dd.admin.business.noteImg.domain.NoteImgDto;
import com.dd.admin.business.noteImg.service.NoteImgService;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 笔记包含的图片 前端控制器
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-23
 */
@Api(tags = "笔记包含的图片")
@RestController
public class NoteImgController {

    @Autowired
    NoteImgService noteImgService;

    @ApiOperation(value = "笔记包含的图片-分页列表")
    @ApiOperationSupport(order = 1)
    @GetMapping("/admin/noteImg/page")
    public ResultBean<IPage<NoteImgVo>> page(NoteImgDto noteImgDto) {
        IPage<NoteImgVo> pageInfo = noteImgService.selectNoteImgPage(noteImgDto);
        return ResultBean.success(pageInfo);
    }

    @ApiOperation(value = "笔记包含的图片-列表")
    @ApiOperationSupport(order = 2)
    @GetMapping("/admin/noteImg/list")
    public ResultBean<List<NoteImgVo>> list(NoteImgDto noteImgDto) {
        List<NoteImgVo> list = noteImgService.selectNoteImgList(noteImgDto);
        return ResultBean.success(list);
    }

    @ApiOperation(value = "笔记包含的图片-添加")
    @ApiOperationSupport(order = 3)
    @PostMapping("/admin/noteImg/add")
    public ResultBean<NoteImg> add(@RequestBody @Validated NoteImgDto noteImgDto) {
        NoteImg noteImg = BeanUtil.copyProperties(noteImgDto, NoteImg.class);
        noteImgService.save(noteImg);
        return ResultBean.success(noteImg);
    }

    @ApiOperation(value = "笔记包含的图片-查询")
    @ApiOperationSupport(order = 4)
    @GetMapping("/admin/noteImg/{noteImgId}")
    public ResultBean<NoteImgVo> get(@PathVariable @NotBlank String noteImgId) {
        NoteImg noteImg = noteImgService.getById(noteImgId);
        NoteImgVo noteImgVo = BeanUtil.copyProperties(noteImg,NoteImgVo.class);
        return ResultBean.success(noteImgVo);
    }

    @ApiOperation(value = "笔记包含的图片-修改")
    @ApiOperationSupport(order = 5)
    @PostMapping("/admin/noteImg/update")
    public ResultBean<NoteImg> update(@RequestBody @Validated(UpdateGroup.class) NoteImgDto noteImgDto) {
        NoteImg noteImg = BeanUtil.copyProperties(noteImgDto, NoteImg.class);
        noteImgService.updateById(noteImg);
        return ResultBean.success(noteImg);
    }

    @ApiOperation(value = "笔记包含的图片-删除")
    @ApiOperationSupport(order = 6)
    @GetMapping("/admin/noteImg/delete/{noteImgId}")
    public ResultBean<NoteImg> delete(@PathVariable @NotBlank String noteImgId) {
        Boolean b = noteImgService.removeById(noteImgId);
        return ResultBean.success(b);
    }
}
