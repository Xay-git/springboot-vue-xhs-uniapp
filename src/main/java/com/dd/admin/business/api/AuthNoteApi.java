package com.dd.admin.business.api;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.dd.admin.business.author.entity.Author;
import com.dd.admin.business.author.service.AuthorService;
import com.dd.admin.business.chat.domain.ChatDto;
import com.dd.admin.business.chat.domain.ChatVo;
import com.dd.admin.business.chat.entity.Chat;
import com.dd.admin.business.chat.service.ChatService;
import com.dd.admin.business.file.entity.FileInfo;
import com.dd.admin.business.file.service.FileService;
import com.dd.admin.business.follow.domain.FollowDto;
import com.dd.admin.business.follow.domain.FollowVo;
import com.dd.admin.business.follow.entity.Follow;
import com.dd.admin.business.follow.service.FollowService;
import com.dd.admin.business.history.domain.HistoryDto;
import com.dd.admin.business.history.domain.HistoryVo;
import com.dd.admin.business.history.entity.History;
import com.dd.admin.business.history.service.HistoryService;
import com.dd.admin.business.note.domain.NoteDto;
import com.dd.admin.business.note.domain.NoteVo;
import com.dd.admin.business.note.domain.ReplayMeVo;
import com.dd.admin.business.note.domain.UpMeVo;
import com.dd.admin.business.note.entity.Note;
import com.dd.admin.business.note.service.NoteService;
import com.dd.admin.business.noteImg.entity.NoteImg;
import com.dd.admin.business.noteImg.service.NoteImgService;
import com.dd.admin.business.reply.domain.ReplyDto;
import com.dd.admin.business.reply.domain.ReplyVo;
import com.dd.admin.business.reply.entity.Reply;
import com.dd.admin.business.reply.service.ReplyService;
import com.dd.admin.business.starNotes.domain.StarNotesDto;
import com.dd.admin.business.starNotes.domain.StarNotesVo;
import com.dd.admin.business.starNotes.entity.StarNotes;
import com.dd.admin.business.starNotes.service.StarNotesService;
import com.dd.admin.business.upNotes.domain.UpNotesDto;
import com.dd.admin.business.upNotes.domain.UpNotesVo;
import com.dd.admin.business.upNotes.entity.UpNotes;
import com.dd.admin.business.upNotes.service.UpNotesService;
import com.dd.admin.business.upReplys.entity.UpReplys;
import com.dd.admin.business.upReplys.service.UpReplysService;
import com.dd.admin.common.aop.operationLog.aop.OperLog;
import com.dd.admin.common.aop.operationLog.aop.OperType;
import com.dd.admin.common.exception.ApiException;
import com.dd.admin.common.model.result.ResultBean;
import com.dd.admin.common.util.MinioUrlUtil;
import com.dd.admin.common.utils.AddressUtils;
import com.dd.admin.common.utils.CommonUtil;
import com.dd.admin.common.utils.IPUtils;
import com.dd.admin.common.utils.StringUtil;
import com.dd.admin.common.util.UrlUtil;
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
import javax.validation.constraints.NotBlank;
import java.util.*;
import java.util.stream.Collectors;

import static com.dd.admin.common.consts.XhsConst.TRUE;
import com.dd.admin.business.category.entity.Category;
import com.dd.admin.business.category.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dd.admin.common.consts.XhsConst;
import com.dd.admin.business.view.service.ViewService;
import com.dd.admin.business.noteImg.domain.NoteImgDto;
import com.dd.admin.business.noteImg.domain.NoteImgVo;

@ApiModel("笔记相关Api")
@RestController
public class AuthNoteApi {
    @Autowired
    AuthorService authorService;
    @Autowired
    HttpServletRequest request;
    @Autowired
    NoteService noteService;
    @Autowired
    FileService fileService;
    @Autowired
    NoteImgService noteImgService;
    @Value("${server.port}")
    String port;
    @Autowired
    FollowService followService;
    @Autowired
    ReplyService replyService;
    @Autowired
    HistoryService historyService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ViewService viewService;
    @Autowired
    private MinioUrlUtil minioUrlUtil;

    @ApiOperation(value = "获取所有关注笔记")
    @ApiOperationSupport(order = 1)
    @GetMapping("/api/auth/getFollowNotes")
    @OperLog(operModule = "获取所有关注笔记",operType = OperType.QUERY,operDesc = "获取所有关注笔记")
    public ResultBean<IPage<NoteVo>> getFollowNotes() {
        String followId = String.valueOf(request.getAttribute("authorId"));
        NoteDto noteDto = new NoteDto();
        noteDto.setNoteStatus(XhsConst.NOTE_NORMAL);
        noteDto.setFollowId(followId);
        List<Follow> follows = followService.selectFollowListByFollowId(followId);

        IPage<NoteVo> pageInfo = new IPage<NoteVo>() {
            @Override
            public List<OrderItem> orders() {
                return null;
            }

            @Override
            public List<NoteVo> getRecords() {
                return null;
            }

            @Override
            public IPage<NoteVo> setRecords(List<NoteVo> records) {
                return null;
            }

            @Override
            public long getTotal() {
                return 0;
            }

            @Override
            public IPage<NoteVo> setTotal(long total) {
                return null;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public IPage<NoteVo> setSize(long size) {
                return null;
            }

            @Override
            public long getCurrent() {
                return 0;
            }

            @Override
            public IPage<NoteVo> setCurrent(long current) {
                return null;
            }
        };
        if(CollectionUtil.isNotEmpty(follows)){
            String authorIds = CommonUtil.getIds(follows, "authorId");
            noteDto.setAuthorIds(authorIds);
            pageInfo = noteService.selectNotePage(noteDto);
        }
        return ResultBean.success(pageInfo);
    }

    @ApiOperation(value = "获取所有笔记")
    @ApiOperationSupport(order = 1)
    @GetMapping("/api/auth/notes")
    @OperLog(operModule = "获取所有笔记",operType = OperType.QUERY,operDesc = "获取所有笔记")
    public ResultBean<IPage<NoteVo>> page(NoteDto noteDto) {
        String followId = String.valueOf(request.getAttribute("authorId"));
        noteDto.setFollowId(followId);
        IPage<NoteVo> pageInfo = noteService.selectNotePage(noteDto);
        return ResultBean.success(pageInfo);
    }

    @ApiOperation(value = "模糊查询笔记")
    @ApiOperationSupport(order = 1)
    @GetMapping("/api/auth/getNotesLike")
    @OperLog(operModule = "模糊查询笔记",operType = OperType.QUERY,operDesc = "模糊查询笔记")
    public ResultBean<IPage<NoteVo>> getNotesLike(NoteDto noteDto) {
        IPage<NoteVo> pageInfo = noteService.selectNoteListLike(noteDto);
        return ResultBean.success(pageInfo);
    }

    //外层倒叙 回复正序
    @ApiOperation(value = "获取笔记评论")
    @ApiOperationSupport(order = 1)
    @GetMapping("/api/auth/getNoteReply")
    @OperLog(operModule = "获取笔记评论",operType = OperType.QUERY,operDesc = "获取笔记评论")
    public ResultBean<Map> getNoteReply( ReplyDto replyDto) {
        String fellowId = String.valueOf(request.getAttribute("authorId"));


        String noteId = replyDto.getNoteId();
        List<ReplyVo> replyList = replyService.selectReplyList(new ReplyDto().setNoteId(noteId).setFollowId(fellowId));
        Integer totalCount = replyList.size();
        // 先构建一个以回复ID为键，回复对象为值的Map，方便后续快速查找回复对象
        // 构建以replyId为键，ReplyVo对象为值的Map，方便后续快速查找回复对象
        Map<String, ReplyVo> replyMap = replyList.stream()
                .collect(Collectors.toMap(ReplyVo::getReplyId, reply -> reply));

        List<ReplyVo> secondLevelReplies = replyList
                .stream()
                .filter(reply ->
                        (StringUtil.isNotEmpty(reply.getParentId())||StringUtil.isNotEmpty(reply.getTopParentId()))
                ).sorted(Comparator.comparing(ReplyVo::getCreateTime)).collect(Collectors.toList());
        secondLevelReplies.forEach(replyVo -> {
            if(StringUtil.isNotEmpty(replyVo.getParentId())){
                replyVo.setParentAuthorId(replyMap.get(replyVo.getParentId()).getAuthorId());
                replyVo.setParentAuthorName(replyMap.get(replyVo.getParentId()).getAuthorName());
            }
        });

        // 获取第一层回复（topId为空且parentId也为空）
        List<ReplyVo> firstLevelReplies = replyList.stream()
                .filter(reply -> reply.getTopParentId() == null && reply.getParentId() == null)
                .collect(Collectors.toList());

        // 将第二层回复添加到对应的第一层回复下（通过topId与replyId关联）
        firstLevelReplies.forEach(firstLevelReply -> {
            String firstLevelReplyId = firstLevelReply.getReplyId();
            List<ReplyVo> relatedSecondLevelReplies = secondLevelReplies.stream()
                    .filter(secondLevelReply -> firstLevelReplyId.equals(secondLevelReply.getTopParentId()))
                    .collect(Collectors.toList());
            if (firstLevelReply.getReply() == null) {
                firstLevelReply.setReply(new ArrayList<>());
            }
            firstLevelReply.getReply().addAll(relatedSecondLevelReplies);
        });


        // 对返回的评论列表中的图片URL进行MinIO转换
        firstLevelReplies.forEach(reply -> {
            if (reply.getReplayImgUrl() != null) {
                reply.setReplayImgUrl(minioUrlUtil.refreshMinioUrl(reply.getReplayImgUrl()));
            }
            // 处理二级回复的图片URL
            if (reply.getReply() != null) {
                reply.getReply().forEach(subReply -> {
                    if (subReply.getReplayImgUrl() != null) {
                        subReply.setReplayImgUrl(minioUrlUtil.refreshMinioUrl(subReply.getReplayImgUrl()));
                    }
                });
            }
        });

        Map map = new HashMap();
        map.put("totalCount",totalCount);
        map.put("replys",firstLevelReplies);


        return ResultBean.success(map);
    }


    @ApiOperation(value = "获取目标博主笔记")
    @ApiOperationSupport(order = 1)
    @GetMapping("/api/auth/getAuthorNotes")
    @OperLog(operModule = "获取目标博主笔记",operType = OperType.QUERY,operDesc = "获取目标博主笔记")
    public ResultBean<IPage<NoteVo>> getAuthorNotes(String authorId) {
        String followId = String.valueOf(request.getAttribute("authorId"));
        NoteDto noteDto = new NoteDto();
        noteDto.setFollowId(followId);
        noteDto.setAuthorId(authorId);
        IPage<NoteVo> pageInfo = noteService.selectNotePage(noteDto);
        return ResultBean.success(pageInfo);
    }

    @ApiOperation(value = "获取当前博主笔记")
    @ApiOperationSupport(order = 1)
    @GetMapping("/api/auth/getMineNotes")
    @OperLog(operModule = "获取当前博主笔记",operType = OperType.QUERY,operDesc = "获取当前博主笔记")
    public ResultBean<IPage<NoteVo>> getMineNotes() {
        String authorId = String.valueOf(request.getAttribute("authorId"));
        NoteDto noteDto = new NoteDto();
        noteDto.setFollowId(authorId);
        noteDto.setAuthorId(authorId);
        noteDto.setNoteStatus(XhsConst.NOTE_NORMAL);

        IPage<NoteVo> pageInfo = noteService.selectNotePage(noteDto);
        return ResultBean.success(pageInfo);
    }


    @ApiOperation(value = "获取所有点赞笔记")
    @ApiOperationSupport(order = 1)
    @GetMapping("/api/auth/getUpNotes")
    @OperLog(operModule = "获取所有点赞笔记",operType = OperType.QUERY,operDesc = "获取所有点赞笔记")
    public ResultBean<IPage<NoteVo>> getUpNotes() {
        String followId = String.valueOf(request.getAttribute("authorId"));
        NoteDto noteDto = new NoteDto();
        noteDto.setFollowId(followId);
        noteDto.setMyUpById(followId);
        IPage<NoteVo> pageInfo = noteService.selectNotePage(noteDto);
        return ResultBean.success(pageInfo);
    }


    @ApiOperation(value = "获取所有收藏笔记")
    @ApiOperationSupport(order = 1)
    @GetMapping("/api/auth/getStarNotes")
    @OperLog(operModule = "获取所有收藏笔记",operType = OperType.QUERY,operDesc = "获取所有收藏笔记")
    public ResultBean<IPage<NoteVo>> getStarNotes() {
        String followId = String.valueOf(request.getAttribute("authorId"));
        NoteDto noteDto = new NoteDto();
        noteDto.setFollowId(followId);
        noteDto.setMyStarById(followId);
        IPage<NoteVo> pageInfo = noteService.selectNotePage(noteDto);
        return ResultBean.success(pageInfo);
    }

    @ApiOperation(value = "创建搜索记录")
    @ApiOperationSupport(order = 1)
    @PostMapping("/api/auth/addHistory")
    @OperLog(operModule = "创建搜索记录",operType = OperType.ADD,operDesc = "创建搜索记录")
    public ResultBean addHistory(@RequestBody History history) {
        String authorId = String.valueOf(request.getAttribute("authorId"));
        history.setAuthorId(authorId);
        historyService.save(history);
        return ResultBean.success();
    }

    @ApiOperation(value = "删除搜索记录")
    @ApiOperationSupport(order = 1)
    @GetMapping("/api/auth/deleteHistory")
    @OperLog(operModule = "删除搜索记录",operType = OperType.ADD,operDesc = "删除搜索记录")
    public ResultBean deleteHistory() {
        String authorId = String.valueOf(request.getAttribute("authorId"));
        LambdaUpdateWrapper<History>  deleteWrapper = new LambdaUpdateWrapper<>();
        deleteWrapper.eq(History::getAuthorId,authorId);
        historyService.remove(deleteWrapper);
        return ResultBean.success();
    }

    @ApiOperation(value = "获取搜索记录")
    @ApiOperationSupport(order = 1)
    @GetMapping("/api/auth/getHistory")
    @OperLog(operModule = "获取搜索记录",operType = OperType.ADD,operDesc = "获取搜索记录")
    public ResultBean getHistory() {
        String authorId = String.valueOf(request.getAttribute("authorId"));
        HistoryDto history = new HistoryDto();
        history.setAuthorId(authorId);
        List<HistoryVo> historyList = historyService.selectHistoryList(history);
        return ResultBean.success(historyList);
    }

    @ApiOperation(value = "获取热搜记录")
    @ApiOperationSupport(order = 1)
    @GetMapping("/api/auth/getHotHistory")
    @OperLog(operModule = "获取热搜记录",operType = OperType.ADD,operDesc = "获取热搜记录")
    public ResultBean getHotHistory() {
        List<HistoryVo> historyList = historyService.selectHotHistoryList();
        return ResultBean.success(historyList);
    }

    @ApiOperation(value = "获取热搜笔记")
    @ApiOperationSupport(order = 1)
    @GetMapping("/api/auth/getHotNotes")
    @OperLog(operModule = "获取热搜笔记",operType = OperType.ADD,operDesc = "获取热搜笔记")
    public ResultBean getHotNotes() {
        List<NoteVo> historyList = noteService.selectHotNoteList();
        return ResultBean.success(historyList);
    }

    @ApiOperation(value = "获取所有分类")
    @ApiOperationSupport(order = 1)
    @GetMapping("/api/getCategories")
    @OperLog(operModule = "获取所有分类",operType = OperType.QUERY,operDesc = "获取所有分类")
    public ResultBean<List<Category>> getCategories() {
        List<Category> list = categoryService.list(new QueryWrapper<Category>().lambda().orderByAsc(Category::getSort));
        return ResultBean.success(list);
    }

    @ApiOperation(value = "创建笔记")
    @ApiOperationSupport(order = 1)
    @PostMapping("/api/auth/addNote")
    @OperLog(operModule = "创建笔记",operType = OperType.ADD,operDesc = "创建笔记")
    @Transactional
    public ResultBean addNote(@RequestBody NoteDto noteDto) {
        String authorId = String.valueOf(request.getAttribute("authorId"));
        Author author = authorService.getById(authorId);

        Note note = BeanUtil.copyProperties(noteDto, Note.class);
        note.setAuthorId(author.getAuthorId());
        note.setAuthorName(author.getAuthorName());
        note.setIpAddress(IPUtils.getIpAddr(request)); // 请求IP
        note.setIpRealAddress(AddressUtils.getRealAddress(note.getIpAddress()));

        // 如果是视频笔记，设置视频地址
        if (noteDto.getNoteType() == 2 && noteDto.getVideoUrl() != null) {
            note.setVideoUrl(noteDto.getVideoUrl());
        }

        // 如果是音乐笔记，设置音乐地址
        if (noteDto.getNoteType() == 3 && noteDto.getMusicUrl() != null) {
            note.setMusicUrl(noteDto.getMusicUrl());
        }

        // 设置关联商品ID
        if (noteDto.getProductId() != null && !noteDto.getProductId().isEmpty()) {
            note.setProductId(noteDto.getProductId());
        }

        noteService.save(note);

        List<String> imgs = noteDto.getImgs();
        if (imgs != null && !imgs.isEmpty()) {
            List<NoteImg> noteImgList = new ArrayList<>();
            for(int i=0;i<imgs.size();i++){
                NoteImg noteImg = new NoteImg();
                noteImg.setAuthorId(authorId);
                noteImg.setNoteId(note.getNoteId());
                noteImg.setImgSort(i);
                FileInfo file = fileService.selectFileByFileId(imgs.get(i));
                // 直接保存文件URL，不进行路径拼接
                noteImg.setImgUrl(file.getFileUrl());
                noteImgList.add(noteImg);
            }
            noteImgService.saveBatch(noteImgList);

            // 如果首图还没设置，则使用这张图片作为首图
            if (note.getFirstPicture() == null || note.getFirstPicture().isEmpty()) {
                Note updateNote = noteService.getById(note.getNoteId());
                updateNote.setFirstPicture(noteImgList.get(0).getImgUrl());
                noteService.updateById(updateNote);
            }

        }
        return ResultBean.success();
    }


    @ApiOperation(value = "修改笔记")
    @ApiOperationSupport(order = 1)
    @PostMapping("/api/auth/updateNote")
    @OperLog(operModule = "修改笔记",operType = OperType.ADD,operDesc = "修改笔记")
    @Transactional
    public ResultBean updateNote(@RequestBody NoteDto noteDto) {
        String authorId = String.valueOf(request.getAttribute("authorId"));
        Author author = authorService.getById(authorId);

        Note note = BeanUtil.copyProperties(noteDto, Note.class);
        note.setAuthorId(author.getAuthorId());
        note.setAuthorName(author.getAuthorName());
        note.setIpAddress(IPUtils.getIpAddr(request)); // 请求IP
        note.setIpRealAddress(AddressUtils.getRealAddress(note.getIpAddress()));

        // 如果是视频笔记，设置视频地址
        if (noteDto.getNoteType() == 2 && noteDto.getVideoUrl() != null) {
            note.setVideoUrl(noteDto.getVideoUrl());
        }

        // 如果前端已经设置了首图URL，则直接使用
        if (noteDto.getFirstPicture() != null && !noteDto.getFirstPicture().isEmpty()) {
            note.setFirstPicture(noteDto.getFirstPicture());
        }

        noteService.updateById(note);

        // 先删除旧的图片关联
        noteImgService.deleteNoteImgByNoteId(note.getNoteId());

        List<String> imgs = noteDto.getImgs();
        if (imgs != null && !imgs.isEmpty()) {
            List<NoteImg> noteImgList = new ArrayList<>();
            for(int i=0;i<imgs.size();i++){
                NoteImg noteImg = new NoteImg();
                noteImg.setAuthorId(authorId);
                noteImg.setNoteId(note.getNoteId());
                noteImg.setImgSort(i);
                FileInfo file = fileService.selectFileByFileId(imgs.get(i));
                // 直接保存文件URL，不进行路径拼接
                noteImg.setImgUrl(file.getFileUrl());
                noteImgList.add(noteImg);
            }
            noteImgService.saveBatch(noteImgList);

            // 如果首图还没设置，则使用这张图片作为首图
            if (note.getFirstPicture() == null || note.getFirstPicture().isEmpty()) {
                Note updateNote = noteService.getById(note.getNoteId());
                updateNote.setFirstPicture(noteImgList.get(0).getImgUrl());
                noteService.updateById(updateNote);
            }
        }

        return ResultBean.success();
    }

    @ApiOperation(value = "删除笔记")
    @ApiOperationSupport(order = 1)
    @PostMapping("/api/auth/deleteNote")
    @OperLog(operModule = "删除笔记",operType = OperType.ADD,operDesc = "删除笔记")
    @Transactional
    public ResultBean deleteNote(@RequestBody NoteDto noteDto) {
       String noteId = noteDto.getNoteId();
       noteService.removeById(noteId);
       return ResultBean.success();
    }


    @ApiOperation(value = "获取所有收藏点赞我的笔记记录")
    @ApiOperationSupport(order = 1)
    @GetMapping("/api/auth/getUpMeNotes")
    @OperLog(operModule = "获取所有收藏点赞我的笔记记录",operType = OperType.QUERY,operDesc = "获取所有收藏点赞我的笔记记录")
    public ResultBean<IPage<UpMeVo>> getUpMeNotes() {
        String authorId = String.valueOf(request.getAttribute("authorId"));
        IPage<UpMeVo> upMeVoIPage = noteService.selectUpMeNotes(authorId);
        // 对返回的UpMeVo列表中的图片URL进行MinIO转换
        upMeVoIPage.getRecords().forEach(upMeVo -> {
            if (upMeVo.getAvatarUrl() != null) {
                upMeVo.setAvatarUrl(minioUrlUtil.refreshMinioUrl(upMeVo.getAvatarUrl()));
            }
            if (upMeVo.getFirstPicture() != null) {
                upMeVo.setFirstPicture(minioUrlUtil.refreshMinioUrl(upMeVo.getFirstPicture()));
            }
        });
        return ResultBean.success(upMeVoIPage);
    }

    @ApiOperation(value = "获取所有评论我的笔记记录")
    @ApiOperationSupport(order = 1)
    @GetMapping("/api/auth/getReplayMes")
    @OperLog(operModule = "获取所有评论我的笔记记录",operType = OperType.QUERY,operDesc = "获取所有评论我的笔记记录")
    public ResultBean<IPage<ReplayMeVo>> getReplayMes() {
        String authorId = String.valueOf(request.getAttribute("authorId"));
        IPage<ReplayMeVo> replyMeNotes = noteService.selectReplyMeNotes(authorId);
        // 对返回的ReplayMeVo列表中的图片URL进行MinIO转换
        replyMeNotes.getRecords().forEach(replayMeVo -> {
            if (replayMeVo.getAvatarUrl() != null) {
                replayMeVo.setAvatarUrl(minioUrlUtil.refreshMinioUrl(replayMeVo.getAvatarUrl()));
            }
            if (replayMeVo.getFirstPicture() != null) {
                replayMeVo.setFirstPicture(minioUrlUtil.refreshMinioUrl(replayMeVo.getFirstPicture()));
            }
        });
        return ResultBean.success(replyMeNotes);
    }

    @ApiOperation(value = "获取单个笔记")
    @ApiOperationSupport(order = 1)
    @GetMapping("/api/auth/getNote")
    @OperLog(operModule = "获取单个笔记",operType = OperType.QUERY,operDesc = "获取单个笔记")
    public ResultBean<NoteVo> getNoteById(NoteDto noteDto) {
        String followId = String.valueOf(request.getAttribute("authorId"));
        if(StringUtil.isNotEmpty(followId)){
            noteDto.setFollowId(followId);
            //读取笔记
            viewService.viewNote(followId,noteDto.getNoteId());
        }
        NoteVo noteVo = noteService.selectNoteDetail(noteDto);
        if(noteVo==null){
            throw new ApiException("笔记不存在");
        }
        return ResultBean.success(noteVo);
    }

    @ApiOperation(value = "获取视频笔记")
    @ApiOperationSupport(order = 2)
    @GetMapping("/api/auth/getVideoNotes")
    @OperLog(operModule = "获取视频笔记", operType = OperType.QUERY, operDesc = "获取视频笔记")
    public ResultBean<IPage<NoteVo>> getVideoNotes(NoteDto noteDto) {
        String followId = String.valueOf(request.getAttribute("authorId"));
        if (StringUtil.isNotEmpty(followId)) {
            noteDto.setFollowId(followId);
        }

        noteDto.setNoteStatus(XhsConst.NOTE_NORMAL);
        noteDto.setNoteType(2); // 1:图文 2:视频 3:音乐
        IPage<NoteVo> pageInfo = noteService.selectNotePage(noteDto);
        return ResultBean.success(pageInfo);
    }
}
