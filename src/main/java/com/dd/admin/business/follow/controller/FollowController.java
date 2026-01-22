package com.dd.admin.business.follow.controller;

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
import com.dd.admin.business.follow.entity.Follow;
import com.dd.admin.business.follow.domain.FollowVo;
import com.dd.admin.business.follow.domain.FollowDto;
import com.dd.admin.business.follow.service.FollowService;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 关注表 前端控制器
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-24
 */
@Api(tags = "关注表")
@RestController
public class FollowController {

    @Autowired
    FollowService followService;

    @ApiOperation(value = "关注表-分页列表")
    @ApiOperationSupport(order = 1)
    @GetMapping("/admin/follow/page")
    public ResultBean<IPage<FollowVo>> page(FollowDto followDto) {
        IPage<FollowVo> pageInfo = followService.selectFollowPage(followDto);
        return ResultBean.success(pageInfo);
    }

    @ApiOperation(value = "关注表-列表")
    @ApiOperationSupport(order = 2)
    @GetMapping("/admin/follow/list")
    public ResultBean<List<FollowVo>> list(FollowDto followDto) {
        List<FollowVo> list = followService.selectFollowList(followDto);
        return ResultBean.success(list);
    }

    @ApiOperation(value = "关注表-添加")
    @ApiOperationSupport(order = 3)
    @PostMapping("/admin/follow/add")
    public ResultBean<Follow> add(@RequestBody @Validated FollowDto followDto) {
        Follow follow = BeanUtil.copyProperties(followDto, Follow.class);
        followService.save(follow);
        return ResultBean.success(follow);
    }

    @ApiOperation(value = "关注表-查询")
    @ApiOperationSupport(order = 4)
    @GetMapping("/admin/follow/{followId}")
    public ResultBean<FollowVo> get(@PathVariable @NotBlank String followId) {
        Follow follow = followService.getById(followId);
        FollowVo followVo = BeanUtil.copyProperties(follow,FollowVo.class);
        return ResultBean.success(followVo);
    }

    @ApiOperation(value = "关注表-修改")
    @ApiOperationSupport(order = 5)
    @PostMapping("/admin/follow/update")
    public ResultBean<Follow> update(@RequestBody @Validated(UpdateGroup.class) FollowDto followDto) {
        Follow follow = BeanUtil.copyProperties(followDto, Follow.class);
        followService.updateById(follow);
        return ResultBean.success(follow);
    }

    @ApiOperation(value = "关注表-删除")
    @ApiOperationSupport(order = 6)
    @GetMapping("/admin/follow/delete/{followId}")
    public ResultBean<Follow> delete(@PathVariable @NotBlank String followId) {
        Boolean b = followService.removeById(followId);
        return ResultBean.success(b);
    }
}
