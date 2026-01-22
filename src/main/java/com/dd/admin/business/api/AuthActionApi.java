package com.dd.admin.business.api;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.dd.admin.business.author.entity.Author;
import com.dd.admin.business.author.service.AuthorService;
import com.dd.admin.business.chat.service.ChatService;
import com.dd.admin.business.file.service.FileService;
import com.dd.admin.business.follow.domain.FollowDto;
import com.dd.admin.business.follow.entity.Follow;
import com.dd.admin.business.follow.service.FollowService;
import com.dd.admin.business.note.domain.NoteDto;
import com.dd.admin.business.note.entity.Note;
import com.dd.admin.business.note.service.NoteService;
import com.dd.admin.business.noteImg.service.NoteImgService;
import com.dd.admin.business.reply.domain.ReplyDto;
import com.dd.admin.business.reply.domain.ReplyVo;
import com.dd.admin.business.reply.entity.Reply;
import com.dd.admin.business.reply.service.ReplyService;
import com.dd.admin.business.starNotes.entity.StarNotes;
import com.dd.admin.business.starNotes.service.StarNotesService;
import com.dd.admin.business.upNotes.entity.UpNotes;
import com.dd.admin.business.upNotes.service.UpNotesService;
import com.dd.admin.business.upReplys.entity.UpReplys;
import com.dd.admin.business.upReplys.service.UpReplysService;
import com.dd.admin.business.webSocket.MyWebSocketMsgHandler;
import com.dd.admin.business.webSocket.util.TioUtil;
import com.dd.admin.common.aop.operationLog.aop.OperLog;
import com.dd.admin.common.aop.operationLog.aop.OperType;
import com.dd.admin.common.exception.ApiException;
import com.dd.admin.common.model.result.ResultBean;
import com.dd.admin.common.utils.AddressUtils;
import com.dd.admin.common.utils.IPUtils;
import com.dd.admin.common.utils.SpringContextUtils;
import com.dd.admin.common.utils.StringUtil;
import com.dd.admin.common.util.MinioUrlUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.tio.websocket.starter.TioWebSocketServerBootstrap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.dd.admin.business.webSocket.WsConst.*;
import static com.dd.admin.common.consts.XhsConst.TRUE;
@ApiModel("用户操作类Api")
@RestController
public class AuthActionApi {
    @Autowired
    AuthorService authorService;
    @Autowired
    HttpServletRequest request;
    @Autowired
    NoteService noteService;
    @Value("${server.port}")
    String port;
    @Autowired
    UpNotesService upNotesService;
    @Autowired
    StarNotesService starNotesService;
    @Autowired
    FollowService followService;
    @Autowired
    ReplyService replyService;
    @Autowired
    UpReplysService upReplysService;
    @Autowired
    private TioWebSocketServerBootstrap bootstrap;
    @Autowired
    private MinioUrlUtil minioUrlUtil;

    @ApiOperation(value = "关注博主")
    @ApiOperationSupport(order = 1)
    @PostMapping("/api/auth/followAuthor")
    @OperLog(operModule = "关注博主",operType = OperType.ADD,operDesc = "关注博主")
    public ResultBean followAuthor(@RequestBody FollowDto followDto) {
        String followId = String.valueOf(request.getAttribute("authorId"));
        Author follow = authorService.getById(followId);
        //查看在不在点赞列表
        Follow oneByFollow= followService.selectOneByFollowId(followDto.getAuthorId(), followId);
        //不在证明是点赞
        if(oneByFollow==null){
            String authorId = followDto.getAuthorId();
            Author author = authorService.getById(authorId);

            oneByFollow = new Follow();
            oneByFollow.setAuthorId(author.getAuthorId());
            oneByFollow.setAuthorName(author.getAuthorName());
            oneByFollow.setFollowId(follow.getAuthorId());
            oneByFollow.setFollowName(follow.getAuthorName());
            followService.save(oneByFollow);

            TioUtil.sendChatMessageToUser( bootstrap.getServerGroupContext(),author.getAuthorId(),HANDLER_FOLLOW,oneByFollow);
        }else{
            throw new ApiException("已经关注过了~");
        }
        return ResultBean.success();
    };

    @ApiOperation(value = "取消关注博主")
    @ApiOperationSupport(order = 1)
    @PostMapping("/api/auth/cancelfollowAuthor")
    @OperLog(operModule = "取消关注博主",operType = OperType.ADD,operDesc = "取消关注博主")
    public ResultBean cancelfollowAuthor(@RequestBody FollowDto followDto) {
        String followId = String.valueOf(request.getAttribute("authorId"));
        Author follow = authorService.getById(followId);
        //查看在不在关注列表
        Follow oneByFollow= followService.selectOneByFollowId(followDto.getAuthorId(), followId);
        //不为空证明关注过
        if(oneByFollow!=null){
            String authorId = followDto.getAuthorId();
            Author author = authorService.getById(authorId);

            oneByFollow.setAuthorId(author.getAuthorId());
            oneByFollow.setAuthorName(author.getAuthorName());
            oneByFollow.setFollowId(follow.getAuthorId());
            oneByFollow.setFollowName(follow.getAuthorName());
            followService.removeById(oneByFollow);
        }else{
            throw new ApiException("你还未关注博主~");
        }
        return ResultBean.success();
    };

    @ApiOperation(value = "点赞笔记")
    @ApiOperationSupport(order = 1)
    @PostMapping("/api/auth/upNote")
    @OperLog(operModule = "点赞笔记",operType = OperType.ADD,operDesc = "点赞笔记")
    public ResultBean upNote(@RequestBody NoteDto noteDto) {
        String followId = String.valueOf(request.getAttribute("authorId"));
        Author follow = authorService.getById(followId);
        Boolean isUp = Boolean.FALSE;
        //查看在不在点赞列表
        UpNotes upNotes = upNotesService.selectOneByFollowId(noteDto.getNoteId(), followId);
        //不在证明是点赞
        if(upNotes==null){
            upNotes = new UpNotes();
            upNotes.setAuthorId(noteDto.getAuthorId());
            upNotes.setAuthorName(noteDto.getAuthorName());
            upNotes.setFollowId(follow.getAuthorId());
            upNotes.setFollowName(follow.getAuthorName());
            upNotes.setNoteId(noteDto.getNoteId());
            upNotes.setNoteTitle(noteDto.getNoteTitle());
            isUp = Boolean.TRUE;
            upNotesService.save(upNotes);

            //发送点赞信息
            TioUtil.sendChatMessageToUser( bootstrap.getServerGroupContext(),noteDto.getAuthorId(),HANDLER_UP,upNotes);

        }else{
            //在则表示取消赞删除数据
            upNotesService.removeById(upNotes);
        }
        return ResultBean.success(isUp);
    };

    @ApiOperation(value = "收藏笔记")
    @ApiOperationSupport(order = 1)
    @PostMapping("/api/auth/starNote")
    @OperLog(operModule = "收藏笔记",operType = OperType.ADD,operDesc = "收藏笔记")
    public ResultBean starNote(@RequestBody NoteDto noteDto) {
        String followId = String.valueOf(request.getAttribute("authorId"));
        Author follow = authorService.getById(followId);
        Boolean isStar = Boolean.FALSE;
        //查看在不在点赞列表
        StarNotes starNotes = starNotesService.selectOneByFollowId(noteDto.getNoteId(), followId);
        //不在证明是点赞
        if(starNotes==null){
            starNotes = new StarNotes();
            starNotes.setAuthorId(noteDto.getAuthorId());
            starNotes.setAuthorName(noteDto.getAuthorName());
            starNotes.setFollowId(follow.getAuthorId());
            starNotes.setFollowName(follow.getAuthorName());
            starNotes.setNoteId(noteDto.getNoteId());
            starNotes.setNoteTitle(noteDto.getNoteTitle());
            isStar = Boolean.TRUE;
            starNotesService.save(starNotes);

            //发送点赞信息
            TioUtil.sendChatMessageToUser( bootstrap.getServerGroupContext(),noteDto.getAuthorId(),HANDLER_STAR,starNotes);
        }else{
            //在则表示取消赞删除数据
            starNotesService.removeById(starNotes);
        }
        return ResultBean.success(isStar);
    };

    @ApiOperation(value = "点赞评论")
    @ApiOperationSupport(order = 1)
    @PostMapping("/api/auth/upReply")
    @OperLog(operModule = "点赞评论",operType = OperType.ADD,operDesc = "点赞评论")
    public ResultBean upReply(@RequestBody ReplyDto replyDto) {
        String followId = String.valueOf(request.getAttribute("authorId"));
        Author follow = authorService.getById(followId);
        Boolean isUp = Boolean.FALSE;
        //查看在不在点赞列表
        UpReplys upReplys = upReplysService.selectOneByFollowId(replyDto.getReplyId(), followId);
        //不在证明是点赞
        if(upReplys==null){
            upReplys = new UpReplys();
            upReplys.setAuthorId(replyDto.getAuthorId());
            upReplys.setAuthorName(replyDto.getAuthorName());
            upReplys.setFollowId(follow.getAuthorId());
            upReplys.setFollowName(follow.getAuthorName());
            upReplys.setReplyId(replyDto.getReplyId());
            upReplys.setReplyContent(replyDto.getReplayContent());
            isUp = Boolean.TRUE;
            upReplysService.save(upReplys);
        }else{
            //在则表示取消赞删除数据
            upReplysService.removeById(upReplys);
        }
        return ResultBean.success(isUp);
    };

    @ApiOperation(value = "回复笔记")
    @ApiOperationSupport(order = 1)
    @PostMapping("/api/auth/replyNote")
    @OperLog(operModule = "回复笔记",operType = OperType.ADD,operDesc = "回复笔记")
    public ResultBean replyNote(@RequestBody ReplyDto replyDto) {
        String authorId = String.valueOf(request.getAttribute("authorId"));
        Author author = authorService.getById(authorId);
        Note note = noteService.getById(replyDto.getNoteId());
        if (authorId.equals(note.getAuthorId())) {
            replyDto.setAuthorReplay(TRUE);
        }

        //如果上级pid不为空 设置 上级作者名和id
        if(StringUtil.isNotEmpty(replyDto.getParentId())){
            Reply parentReply = replyService.getById(replyDto.getParentId());
            replyDto.setParentAuthorName(parentReply.getAuthorName());
            replyDto.setParentAuthorId(parentReply.getAuthorId());
            //如果top不为空 设置第二级回复的 上级作者名和id
        }else if(StringUtil.isNotEmpty(replyDto.getTopParentId())){
            Reply parentReply = replyService.getById(replyDto.getTopParentId());
            replyDto.setParentAuthorName(parentReply.getAuthorName());
            replyDto.setParentAuthorId(parentReply.getAuthorId());
            //如果评论没有上级id和顶级id则回复的是笔记的作者
        }else if(StringUtil.isEmpty(replyDto.getParentId())&&StringUtil.isEmpty(replyDto.getTopParentId())){
            replyDto.setParentAuthorName(note.getAuthorName());
            replyDto.setParentAuthorId(note.getAuthorId());
        }


        List<ReplyVo> replyList = replyService.selectReplyList(new ReplyDto().setNoteId(replyDto.getNoteId()));
        if (CollectionUtil.isEmpty(replyList)) {
            replyDto.setFirstReplay(TRUE);
        }
        replyDto.setNoteId(note.getNoteId());
        replyDto.setNoteTitle(note.getNoteTitle());
        replyDto.setAuthorId(authorId);
        replyDto.setAuthorName(author.getAuthorName());
        replyDto.setAvatarUrl(minioUrlUtil.refreshMinioUrl(author.getAvatarUrl()));
        replyDto.setIpAddress(IPUtils.getIpAddr(request)); // 请求IP
        replyDto.setIpRealAddress(AddressUtils.getRealAddress(replyDto.getIpAddress()));
        
        // 处理回复图片URL
        if(StringUtil.isNotEmpty(replyDto.getReplayImgUrl())){
            replyDto.setReplayImgUrl(minioUrlUtil.refreshMinioUrl(replyDto.getReplayImgUrl()));
        }
        
        Reply reply = BeanUtil.copyProperties(replyDto, Reply.class);
        replyService.save(reply);

        TioUtil.sendChatMessageToUser( bootstrap.getServerGroupContext(),reply.getParentAuthorId(),HANDLER_REPLY,reply);

        return ResultBean.success();
    }
}
