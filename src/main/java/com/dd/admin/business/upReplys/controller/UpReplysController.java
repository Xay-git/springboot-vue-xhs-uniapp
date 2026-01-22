package com.dd.admin.business.upReplys.controller;

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
import com.dd.admin.business.upReplys.entity.UpReplys;
import com.dd.admin.business.upReplys.domain.UpReplysVo;
import com.dd.admin.business.upReplys.domain.UpReplysDto;
import com.dd.admin.business.upReplys.service.UpReplysService;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 笔记点赞表 前端控制器
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-25
 */
@Api(tags = "笔记点赞表")
@RestController
public class UpReplysController {

    @Autowired
    UpReplysService upReplysService;

    @ApiOperation(value = "笔记点赞表-分页列表")
    @ApiOperationSupport(order = 1)
    @GetMapping("/admin/upReplys/page")
    public ResultBean<IPage<UpReplysVo>> page(UpReplysDto upReplysDto) {
        IPage<UpReplysVo> pageInfo = upReplysService.selectUpReplysPage(upReplysDto);
        return ResultBean.success(pageInfo);
    }

    @ApiOperation(value = "笔记点赞表-列表")
    @ApiOperationSupport(order = 2)
    @GetMapping("/admin/upReplys/list")
    public ResultBean<List<UpReplysVo>> list(UpReplysDto upReplysDto) {
        List<UpReplysVo> list = upReplysService.selectUpReplysList(upReplysDto);
        return ResultBean.success(list);
    }

    @ApiOperation(value = "笔记点赞表-添加")
    @ApiOperationSupport(order = 3)
    @PostMapping("/admin/upReplys/add")
    public ResultBean<UpReplys> add(@RequestBody @Validated UpReplysDto upReplysDto) {
        UpReplys upReplys = BeanUtil.copyProperties(upReplysDto, UpReplys.class);
        upReplysService.save(upReplys);
        return ResultBean.success(upReplys);
    }

    @ApiOperation(value = "笔记点赞表-查询")
    @ApiOperationSupport(order = 4)
    @GetMapping("/admin/upReplys/{upReplysId}")
    public ResultBean<UpReplysVo> get(@PathVariable @NotBlank String upReplysId) {
        UpReplys upReplys = upReplysService.getById(upReplysId);
        UpReplysVo upReplysVo = BeanUtil.copyProperties(upReplys,UpReplysVo.class);
        return ResultBean.success(upReplysVo);
    }

    @ApiOperation(value = "笔记点赞表-修改")
    @ApiOperationSupport(order = 5)
    @PostMapping("/admin/upReplys/update")
    public ResultBean<UpReplys> update(@RequestBody @Validated(UpdateGroup.class) UpReplysDto upReplysDto) {
        UpReplys upReplys = BeanUtil.copyProperties(upReplysDto, UpReplys.class);
        upReplysService.updateById(upReplys);
        return ResultBean.success(upReplys);
    }

    @ApiOperation(value = "笔记点赞表-删除")
    @ApiOperationSupport(order = 6)
    @GetMapping("/admin/upReplys/delete/{upReplysId}")
    public ResultBean<UpReplys> delete(@PathVariable @NotBlank String upReplysId) {
        Boolean b = upReplysService.removeById(upReplysId);
        return ResultBean.success(b);
    }
}
