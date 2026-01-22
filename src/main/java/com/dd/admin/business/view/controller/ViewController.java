package com.dd.admin.business.view.controller;

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
import com.dd.admin.business.view.entity.View;
import com.dd.admin.business.view.domain.ViewVo;
import com.dd.admin.business.view.domain.ViewDto;
import com.dd.admin.business.view.service.ViewService;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 查看 前端控制器
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-01-15
 */
@Api(tags = "查看")
@RestController
public class ViewController {

    @Autowired
    ViewService viewService;

    @ApiOperation(value = "查看-分页列表")
    @ApiOperationSupport(order = 1)
    @GetMapping("/admin/view/page")
    public ResultBean<IPage<ViewVo>> page(ViewDto viewDto) {
        IPage<ViewVo> pageInfo = viewService.selectViewPage(viewDto);
        return ResultBean.success(pageInfo);
    }

    @ApiOperation(value = "查看-列表")
    @ApiOperationSupport(order = 2)
    @GetMapping("/admin/view/list")
    public ResultBean<List<ViewVo>> list(ViewDto viewDto) {
        List<ViewVo> list = viewService.selectViewList(viewDto);
        return ResultBean.success(list);
    }

    @ApiOperation(value = "查看-添加")
    @ApiOperationSupport(order = 3)
    @PostMapping("/admin/view/add")
    public ResultBean<View> add(@RequestBody @Validated ViewDto viewDto) {
        View view = BeanUtil.copyProperties(viewDto, View.class);
        viewService.save(view);
        return ResultBean.success(view);
    }

    @ApiOperation(value = "查看-查询")
    @ApiOperationSupport(order = 4)
    @GetMapping("/admin/view/{viewId}")
    public ResultBean<ViewVo> get(@PathVariable @NotBlank String viewId) {
        View view = viewService.getById(viewId);
        ViewVo viewVo = BeanUtil.copyProperties(view,ViewVo.class);
        return ResultBean.success(viewVo);
    }

    @ApiOperation(value = "查看-修改")
    @ApiOperationSupport(order = 5)
    @PostMapping("/admin/view/update")
    public ResultBean<View> update(@RequestBody @Validated(UpdateGroup.class) ViewDto viewDto) {
        View view = BeanUtil.copyProperties(viewDto, View.class);
        viewService.updateById(view);
        return ResultBean.success(view);
    }

    @ApiOperation(value = "查看-删除")
    @ApiOperationSupport(order = 6)
    @GetMapping("/admin/view/delete/{viewId}")
    public ResultBean<View> delete(@PathVariable @NotBlank String viewId) {
        Boolean b = viewService.removeById(viewId);
        return ResultBean.success(b);
    }
}
