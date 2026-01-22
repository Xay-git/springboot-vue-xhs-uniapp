package com.dd.admin.business.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.business.api.domain.UnReadCountBean;
import com.dd.admin.business.author.entity.Author;
import com.dd.admin.business.author.service.AuthorService;
import com.dd.admin.business.chat.domain.ChatDto;
import com.dd.admin.business.chat.domain.ChatVo;
import com.dd.admin.business.chat.service.ChatService;
import com.dd.admin.business.file.service.FileService;
import com.dd.admin.business.follow.service.FollowService;
import com.dd.admin.business.note.service.NoteService;
import com.dd.admin.business.noteImg.service.NoteImgService;
import com.dd.admin.business.receive.service.ReceiveService;
import com.dd.admin.common.util.MinioUrlUtil;
import com.dd.admin.business.reply.service.ReplyService;
import com.dd.admin.business.starNotes.service.StarNotesService;
import com.dd.admin.business.upNotes.service.UpNotesService;
import com.dd.admin.business.upReplys.service.UpReplysService;
import com.dd.admin.common.aop.operationLog.aop.OperLog;
import com.dd.admin.common.aop.operationLog.aop.OperType;
import com.dd.admin.common.model.result.ResultBean;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@ApiModel("聊天相关Api")
@RestController
public class AuthChatApi {
    @Autowired
    HttpServletRequest request;
    @Value("${server.port}")
    String port;
    @Autowired
    ChatService chatService;
    @Autowired
    AuthorService authorService;
    @Autowired
    UpNotesService upNotesService;
    @Autowired
    StarNotesService starNotesService;
    @Autowired
    ReplyService replyService;
    @Autowired
    FollowService followService;
    @Autowired
    ReceiveService receiveService;
    @Autowired
    private MinioUrlUtil minioUrlUtil;

    @ApiOperation(value = "获取消息列表")
    @ApiOperationSupport(order = 1)
    @GetMapping("/api/auth/getMessageList")
    @OperLog(operModule = "获取消息列表",operType = OperType.QUERY,operDesc = "获取消息列表")
    public ResultBean<List<ChatVo>> getMessageList(ChatDto chatDto) {
        String followId = String.valueOf(request.getAttribute("authorId"));
        List<ChatVo> chatVos = chatService.getMessageList(followId);
        
        // 刷新MinIO URL
        chatVos.forEach(chatVo -> {
            chatVo.setAuthorAvatar(minioUrlUtil.refreshMinioUrl(chatVo.getAuthorAvatar()));
            // 刷新资源URL（图片、视频等）
            if (chatVo.getResourceUrl() != null && !chatVo.getResourceUrl().isEmpty()) {
                chatVo.setResourceUrl(minioUrlUtil.refreshMinioUrl(chatVo.getResourceUrl()));
            }
        });
        
        return ResultBean.success(chatVos);
    }

    @ApiOperation(value = "获取聊天记录")
    @ApiOperationSupport(order = 1)
    @GetMapping("/api/auth/getChatList")
    @OperLog(operModule = "获取所有笔记",operType = OperType.QUERY,operDesc = "获取聊天记录")
    public ResultBean<IPage<ChatVo>> getChatList(ChatDto chatDto) {
        String followId = String.valueOf(request.getAttribute("authorId"));
        Author follow = authorService.getById(followId);
        chatDto.setToId(followId);
        chatDto.setToName(follow.getAuthorName());
        IPage<ChatVo> chatVoIPage = chatService.selectChatPage(chatDto);
        
        // 刷新MinIO URL
         chatVoIPage.getRecords().forEach(chatVo -> {
             chatVo.setToAvatar(minioUrlUtil.refreshMinioUrl(chatVo.getToAvatar()));
             chatVo.setFromAvatar(minioUrlUtil.refreshMinioUrl(chatVo.getFromAvatar()));
             // 刷新资源URL（图片、视频等）
             if (chatVo.getResourceUrl() != null && !chatVo.getResourceUrl().isEmpty()) {
                 chatVo.setResourceUrl(minioUrlUtil.refreshMinioUrl(chatVo.getResourceUrl()));
             }
         });

        return ResultBean.success(chatVoIPage);
    }

    @ApiOperation(value = "读取消息")
    @ApiOperationSupport(order = 1)
    @PostMapping("/api/auth/readAuthorMessage")
    @OperLog(operModule = "读取消息",operType = OperType.OTHER,operDesc = "读取消息")
    public ResultBean readAuthorMessage(String authorId) {
        String loginId = String.valueOf(request.getAttribute("authorId"));
        chatService.readMessage(authorId,loginId);
        return ResultBean.success(loginId);
    };

    @ApiOperation(value = "读取点赞笔记消息")
    @ApiOperationSupport(order = 2)
    @PostMapping("/api/auth/readUpNotesMessage")
    @OperLog(operModule = "读取点赞笔记消息", operType = OperType.OTHER, operDesc = "读取点赞笔记消息")
    public ResultBean readUpNotesMessage() {
        String authorId = String.valueOf(request.getAttribute("authorId"));
        upNotesService.readMessage(authorId);
        return ResultBean.success();
    }

    @ApiOperation(value = "读取收藏笔记消息")
    @ApiOperationSupport(order = 3)
    @PostMapping("/api/auth/readStarNotesMessage")
    @OperLog(operModule = "读取收藏笔记消息", operType = OperType.OTHER, operDesc = "读取收藏笔记消息")
    public ResultBean readStarNotesMessage() {
        String authorId = String.valueOf(request.getAttribute("authorId"));
        starNotesService.readMessage(authorId);
        return ResultBean.success();
    }

    @ApiOperation(value = "读取回复消息")
    @ApiOperationSupport(order = 4)
    @PostMapping("/api/auth/readReplyMessage")
    @OperLog(operModule = "读取回复消息", operType = OperType.OTHER, operDesc = "读取回复消息")
    public ResultBean readReplyMessage() {
        String authorId = String.valueOf(request.getAttribute("authorId"));
        replyService.readMessage(authorId);
        return ResultBean.success();
    }

    @ApiOperation(value = "读取关注消息")
    @ApiOperationSupport(order = 5)
    @PostMapping("/api/auth/readFollowMessage")
    @OperLog(operModule = "读取关注消息", operType = OperType.OTHER, operDesc = "读取关注消息")
    public ResultBean readFollowMessage() {
        String authorId = String.valueOf(request.getAttribute("authorId"));
        followService.readMessage(authorId);
        return ResultBean.success();
    }

    @ApiOperation(value = "查询我的未读消息数量")
    @ApiOperationSupport(order = 1)
    @GetMapping("/api/auth/getUnReadCount")
    @OperLog(operModule = "查询我的未读消息数量",operType = OperType.OTHER,operDesc = "查询我的未读消息数量")
    public ResultBean getUnReadCount() {
        String loginId = String.valueOf(request.getAttribute("authorId"));

        Integer chatUnReadCount = chatService.selectUnReadCount(loginId);
        Integer upNotesUnReadCount = upNotesService.selectUnReadCount(loginId);
        Integer starNotesUnReadCount = starNotesService.selectUnReadCount(loginId);
        Integer replyUnReadCount = replyService.selectUnReadCount(loginId);
        Integer followUnReadCount = followService.selectUnReadCount(loginId);
        Integer receiveUnReadCount = receiveService.selectUnReadCount(loginId);
        Integer totalUnReadCount = 0;
        UnReadCountBean unReadCountBean = new UnReadCountBean(chatUnReadCount,upNotesUnReadCount,starNotesUnReadCount,replyUnReadCount,followUnReadCount,receiveUnReadCount,totalUnReadCount);
        unReadCountBean.calculateTotalUnReadCount();
        return ResultBean.success(unReadCountBean);
    };
}
