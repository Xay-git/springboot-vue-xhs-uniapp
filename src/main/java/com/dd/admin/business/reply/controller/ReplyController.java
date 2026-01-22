package com.dd.admin.business.reply.controller;

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
import com.dd.admin.business.reply.entity.Reply;
import com.dd.admin.business.reply.domain.ReplyVo;
import com.dd.admin.business.reply.domain.ReplyDto;
import com.dd.admin.business.reply.service.ReplyService;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 回复表 前端控制器
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-25
 */
@Api(tags = "回复表")
@RestController
public class ReplyController {

    @Autowired
    ReplyService replyService;

    @ApiOperation(value = "回复表-分页列表")
    @ApiOperationSupport(order = 1)
    @GetMapping("/admin/reply/page")
    public ResultBean<IPage<ReplyVo>> page(ReplyDto replyDto) {
        IPage<ReplyVo> pageInfo = replyService.selectReplyPage(replyDto);
        return ResultBean.success(pageInfo);
    }

    @ApiOperation(value = "回复表-列表")
    @ApiOperationSupport(order = 2)
    @GetMapping("/admin/reply/list")
    public ResultBean<List<ReplyVo>> list(ReplyDto replyDto) {
        List<ReplyVo> list = replyService.selectReplyList(replyDto);
        return ResultBean.success(list);
    }

    @ApiOperation(value = "回复表-添加")
    @ApiOperationSupport(order = 3)
    @PostMapping("/admin/reply/add")
    public ResultBean<Reply> add(@RequestBody @Validated ReplyDto replyDto) {
        Reply reply = BeanUtil.copyProperties(replyDto, Reply.class);
        replyService.save(reply);
        return ResultBean.success(reply);
    }

    @ApiOperation(value = "回复表-查询")
    @ApiOperationSupport(order = 4)
    @GetMapping("/admin/reply/{replyId}")
    public ResultBean<ReplyVo> get(@PathVariable @NotBlank String replyId) {
        Reply reply = replyService.getById(replyId);
        ReplyVo replyVo = BeanUtil.copyProperties(reply,ReplyVo.class);
        return ResultBean.success(replyVo);
    }

    @ApiOperation(value = "回复表-修改")
    @ApiOperationSupport(order = 5)
    @PostMapping("/admin/reply/update")
    public ResultBean<Reply> update(@RequestBody @Validated(UpdateGroup.class) ReplyDto replyDto) {
        Reply reply = BeanUtil.copyProperties(replyDto, Reply.class);
        replyService.updateById(reply);
        return ResultBean.success(reply);
    }

    @ApiOperation(value = "回复表-删除")
    @ApiOperationSupport(order = 6)
    @GetMapping("/admin/reply/delete/{replyId}")
    public ResultBean<Reply> delete(@PathVariable @NotBlank String replyId) {
        Boolean b = replyService.removeById(replyId);
        return ResultBean.success(b);
    }
}
