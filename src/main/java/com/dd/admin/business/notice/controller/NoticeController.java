package com.dd.admin.business.notice.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.dd.admin.business.author.entity.Author;
import com.dd.admin.business.note.service.NoteService;
import com.dd.admin.business.notice.domain.SendNoticeDto;
import com.dd.admin.business.receive.entity.Receive;
import com.dd.admin.business.receive.service.ReceiveService;
import com.dd.admin.business.webSocket.util.TioUtil;
import com.dd.admin.common.exception.ApiException;
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
import com.dd.admin.business.notice.entity.Notice;
import com.dd.admin.business.notice.domain.NoticeVo;
import com.dd.admin.business.notice.domain.NoticeDto;
import com.dd.admin.business.notice.service.NoticeService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.tio.websocket.starter.TioWebSocketServerBootstrap;

import static com.dd.admin.business.webSocket.WsConst.HANDLER_NOTICE_COUNT;

/**
 * <p>
 * 通知公告表 前端控制器
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-01-14
 */
@Api(tags = "通知公告表")
@RestController
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    @Autowired
    ReceiveService receiveService;

    @Autowired
    private TioWebSocketServerBootstrap bootstrap;

    @ApiOperation(value = "发送通知")
    @ApiOperationSupport(order = 3)
    @PostMapping("/admin/notice/sendNotice")
    public ResultBean<Notice> sendNotice(@RequestBody @Validated SendNoticeDto sendNoticeDto) {
        System.out.println(sendNoticeDto);
        if(CollectionUtil.isNotEmpty(sendNoticeDto.getAuthorList())){
            Notice notice = noticeService.getById(sendNoticeDto.getNoticeId());
            List<Author> authorList = sendNoticeDto.getAuthorList();

            List<Receive> receiveList = new ArrayList<>();
            authorList.stream().forEach(author -> {
                Receive receive = new Receive();
                receive.setNoticeId(notice.getNoticeId());
                receive.setAuthorId(author.getAuthorId());
                receive.setAuthorName(author.getAuthorName());
                boolean add = receiveList.add(receive);
                //给指定用户发送
                if(add){
                    TioUtil.sendChatMessageToUser(bootstrap.getServerGroupContext(),author.getAuthorId(),HANDLER_NOTICE_COUNT,receive);
                }
            });

            receiveService.saveBatch(receiveList);

        }else{
            throw new ApiException("请选择发送对象~");
        }

        return ResultBean.success();
    }

    @ApiOperation(value = "通知公告表-分页列表")
    @ApiOperationSupport(order = 1)
    @GetMapping("/admin/notice/page")
    public ResultBean<IPage<NoticeVo>> page(NoticeDto noticeDto) {
        IPage<NoticeVo> pageInfo = noticeService.selectNoticePage(noticeDto);
        return ResultBean.success(pageInfo);
    }

    @ApiOperation(value = "通知公告表-列表")
    @ApiOperationSupport(order = 2)
    @GetMapping("/admin/notice/list")
    public ResultBean<List<NoticeVo>> list(NoticeDto noticeDto) {
        List<NoticeVo> list = noticeService.selectNoticeList(noticeDto);
        return ResultBean.success(list);
    }

    @ApiOperation(value = "通知公告表-添加")
    @ApiOperationSupport(order = 3)
    @PostMapping("/admin/notice/add")
    public ResultBean<Notice> add(@RequestBody @Validated NoticeDto noticeDto) {
        Notice notice = BeanUtil.copyProperties(noticeDto, Notice.class);
        noticeService.save(notice);
        return ResultBean.success(notice);
    }

    @ApiOperation(value = "通知公告表-查询")
    @ApiOperationSupport(order = 4)
    @GetMapping("/admin/notice/{noticeId}")
    public ResultBean<NoticeVo> get(@PathVariable @NotBlank String noticeId) {
        Notice notice = noticeService.getById(noticeId);
        NoticeVo noticeVo = BeanUtil.copyProperties(notice,NoticeVo.class);
        return ResultBean.success(noticeVo);
    }

    @ApiOperation(value = "通知公告表-修改")
    @ApiOperationSupport(order = 5)
    @PostMapping("/admin/notice/update")
    public ResultBean<Notice> update(@RequestBody @Validated(UpdateGroup.class) NoticeDto noticeDto) {
        Notice notice = BeanUtil.copyProperties(noticeDto, Notice.class);
        noticeService.updateById(notice);
        return ResultBean.success(notice);
    }

    @ApiOperation(value = "通知公告表-删除")
    @ApiOperationSupport(order = 6)
    @GetMapping("/admin/notice/delete/{noticeId}")
    public ResultBean<Notice> delete(@PathVariable @NotBlank String noticeId) {
        Boolean b = noticeService.removeById(noticeId);
        return ResultBean.success(b);
    }
}
