package com.dd.admin.business.api;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.dd.admin.business.author.entity.Author;
import com.dd.admin.business.author.service.AuthorService;
import com.dd.admin.business.chat.domain.AuthorParam;
import com.dd.admin.business.file.entity.FileInfo;
import com.dd.admin.business.file.service.FileService;
import com.dd.admin.business.follow.entity.Follow;
import com.dd.admin.business.follow.service.FollowService;
import com.dd.admin.business.note.domain.NoteDto;
import com.dd.admin.business.note.domain.NoteVo;
import com.dd.admin.business.note.domain.ReplayMeVo;
import com.dd.admin.business.note.domain.UpMeVo;
import com.dd.admin.business.note.entity.Note;
import com.dd.admin.business.note.service.NoteService;
import com.dd.admin.business.noteImg.entity.NoteImg;
import com.dd.admin.business.noteImg.service.NoteImgService;
import com.dd.admin.business.receive.domain.ReceiveDto;
import com.dd.admin.business.receive.domain.ReceiveVo;
import com.dd.admin.business.receive.entity.Receive;
import com.dd.admin.business.receive.service.ReceiveService;
import com.dd.admin.business.reply.domain.ReplyDto;
import com.dd.admin.business.reply.domain.ReplyVo;
import com.dd.admin.business.reply.service.ReplyService;
import com.dd.admin.common.aop.operationLog.aop.OperLog;
import com.dd.admin.common.aop.operationLog.aop.OperType;
import com.dd.admin.common.model.result.ResultBean;
import com.dd.admin.common.utils.AddressUtils;
import com.dd.admin.common.utils.CommonUtil;
import com.dd.admin.common.utils.IPUtils;
import com.dd.admin.common.utils.StringUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@ApiModel("笔记相关Api")
@RestController
public class AuthNoticeApi {
    @Autowired
    HttpServletRequest request;
    @Autowired
    ReceiveService receiveService;

    @ApiOperation(value = "读取系统消息")
    @ApiOperationSupport(order = 4)
    @PostMapping("/api/auth/readAuthorReceive")
    @OperLog(operModule = "读取系统消息", operType = OperType.OTHER, operDesc = "读取系统消息")
    public ResultBean readAuthorReceive() {
        String authorId = String.valueOf(request.getAttribute("authorId"));
        receiveService.readMessage(authorId);
        return ResultBean.success();
    }


    @ApiOperation(value = "获取所有系统消息")
    @ApiOperationSupport(order = 1)
    @GetMapping("/api/auth/getReceivePage")
    @OperLog(operModule = "获取所有系统消息", operType = OperType.QUERY, operDesc = "获取所有系统消息")
    public ResultBean<IPage<ReceiveVo>> getReceivePage() {
        String authorId = String.valueOf(request.getAttribute("authorId"));
        ReceiveDto receiveDto = new ReceiveDto();
        receiveDto.setAuthorId(authorId);
        // 1. 通过noteService根据传入的NoteDto参数查询笔记的分页信息，获取包含笔记数据的分页对象
        IPage<ReceiveVo> receiveVoIPage = receiveService.selectReceivePage(receiveDto);
        // 20. 将处理好的包含笔记信息（已设置点赞状态和点赞数量）的分页对象包装在ResultBean中返回，以便前端或其他调用方获取处理后的结果数据
        return ResultBean.success(receiveVoIPage);
    }


    @ApiOperation(value = "获取最后一条系统消息")
    @ApiOperationSupport(order = 1)
    @GetMapping("/api/auth/getReceiveLast")
    @OperLog(operModule = "获取最后一条系统消息", operType = OperType.QUERY, operDesc = "获取最后一条系统消息")
    public ResultBean<ReceiveVo> getReceiveLast() {
        String authorId = String.valueOf(request.getAttribute("authorId"));
        // 1. 通过noteService根据传入的NoteDto参数查询笔记的分页信息，获取包含笔记数据的分页对象
        ReceiveVo lastReceive = receiveService.selectReceiveLast(authorId);
        // 20. 将处理好的包含笔记信息（已设置点赞状态和点赞数量）的分页对象包装在ResultBean中返回，以便前端或其他调用方获取处理后的结果数据
        return ResultBean.success(lastReceive);
    }
}
