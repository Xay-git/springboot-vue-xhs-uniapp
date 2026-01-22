package com.dd.admin.business.receive.controller;

import cn.hutool.core.bean.BeanUtil;
import com.dd.admin.business.chat.domain.AuthorParam;
import com.dd.admin.common.aop.operationLog.aop.OperLog;
import com.dd.admin.common.aop.operationLog.aop.OperType;
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
import com.dd.admin.business.receive.entity.Receive;
import com.dd.admin.business.receive.domain.ReceiveVo;
import com.dd.admin.business.receive.domain.ReceiveDto;
import com.dd.admin.business.receive.service.ReceiveService;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 接收消息表 前端控制器
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-01-14
 */
@Api(tags = "接收消息表")
@RestController
public class ReceiveController {

    @Autowired
    ReceiveService receiveService;

    @ApiOperation(value = "接收消息表-分页列表")
    @ApiOperationSupport(order = 1)
    @GetMapping("/admin/receive/page")
    public ResultBean<IPage<ReceiveVo>> page(ReceiveDto receiveDto) {
        IPage<ReceiveVo> pageInfo = receiveService.selectReceivePage(receiveDto);
        return ResultBean.success(pageInfo);
    }




    @ApiOperation(value = "接收消息表-列表")
    @ApiOperationSupport(order = 2)
    @GetMapping("/admin/receive/list")
    public ResultBean<List<ReceiveVo>> list(ReceiveDto receiveDto) {
        List<ReceiveVo> list = receiveService.selectReceiveList(receiveDto);
        return ResultBean.success(list);
    }

    @ApiOperation(value = "接收消息表-添加")
    @ApiOperationSupport(order = 3)
    @PostMapping("/admin/receive/add")
    public ResultBean<Receive> add(@RequestBody @Validated ReceiveDto receiveDto) {
        Receive receive = BeanUtil.copyProperties(receiveDto, Receive.class);
        receiveService.save(receive);
        return ResultBean.success(receive);
    }

    @ApiOperation(value = "接收消息表-查询")
    @ApiOperationSupport(order = 4)
    @GetMapping("/admin/receive/{receiveId}")
    public ResultBean<ReceiveVo> get(@PathVariable @NotBlank String receiveId) {
        Receive receive = receiveService.getById(receiveId);
        ReceiveVo receiveVo = BeanUtil.copyProperties(receive,ReceiveVo.class);
        return ResultBean.success(receiveVo);
    }

    @ApiOperation(value = "接收消息表-修改")
    @ApiOperationSupport(order = 5)
    @PostMapping("/admin/receive/update")
    public ResultBean<Receive> update(@RequestBody @Validated(UpdateGroup.class) ReceiveDto receiveDto) {
        Receive receive = BeanUtil.copyProperties(receiveDto, Receive.class);
        receiveService.updateById(receive);
        return ResultBean.success(receive);
    }

    @ApiOperation(value = "接收消息表-删除")
    @ApiOperationSupport(order = 6)
    @GetMapping("/admin/receive/delete/{receiveId}")
    public ResultBean<Receive> delete(@PathVariable @NotBlank String receiveId) {
        Boolean b = receiveService.removeById(receiveId);
        return ResultBean.success(b);
    }
}
