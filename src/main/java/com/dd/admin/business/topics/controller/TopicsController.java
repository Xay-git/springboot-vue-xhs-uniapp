package com.dd.admin.business.topics.controller;

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
import com.dd.admin.business.topics.entity.Topics;
import com.dd.admin.business.topics.domain.TopicsVo;
import com.dd.admin.business.topics.domain.TopicsDto;
import com.dd.admin.business.topics.service.TopicsService;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 话题 前端控制器
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-04-14
 */
@Api(tags = "话题")
@RestController
public class TopicsController {

    @Autowired
    TopicsService topicsService;

    @ApiOperation(value = "话题-分页列表")
    @ApiOperationSupport(order = 1)
    @GetMapping("/admin/topics/page")
    public ResultBean<IPage<TopicsVo>> page(TopicsDto topicsDto) {
        IPage<TopicsVo> pageInfo = topicsService.selectTopicsPage(topicsDto);
        return ResultBean.success(pageInfo);
    }

    @ApiOperation(value = "话题-列表")
    @ApiOperationSupport(order = 2)
    @GetMapping("/admin/topics/list")
    public ResultBean<List<TopicsVo>> list(TopicsDto topicsDto) {
        List<TopicsVo> list = topicsService.selectTopicsList(topicsDto);
        return ResultBean.success(list);
    }

    @ApiOperation(value = "话题-添加")
    @ApiOperationSupport(order = 3)
    @PostMapping("/admin/topics/add")
    public ResultBean<Topics> add(@RequestBody @Validated TopicsDto topicsDto) {
        Topics topics = BeanUtil.copyProperties(topicsDto, Topics.class);
        topicsService.save(topics);
        return ResultBean.success(topics);
    }

    @ApiOperation(value = "话题-查询")
    @ApiOperationSupport(order = 4)
    @GetMapping("/admin/topics/{topicsId}")
    public ResultBean<TopicsVo> get(@PathVariable @NotBlank String topicsId) {
        Topics topics = topicsService.getById(topicsId);
        TopicsVo topicsVo = BeanUtil.copyProperties(topics,TopicsVo.class);
        return ResultBean.success(topicsVo);
    }

    @ApiOperation(value = "话题-修改")
    @ApiOperationSupport(order = 5)
    @PostMapping("/admin/topics/update")
    public ResultBean<Topics> update(@RequestBody @Validated(UpdateGroup.class) TopicsDto topicsDto) {
        Topics topics = BeanUtil.copyProperties(topicsDto, Topics.class);
        topicsService.updateById(topics);
        return ResultBean.success(topics);
    }

    @ApiOperation(value = "话题-删除")
    @ApiOperationSupport(order = 6)
    @GetMapping("/admin/topics/delete/{topicsId}")
    public ResultBean<Topics> delete(@PathVariable @NotBlank String topicsId) {
        Boolean b = topicsService.removeById(topicsId);
        return ResultBean.success(b);
    }
}
