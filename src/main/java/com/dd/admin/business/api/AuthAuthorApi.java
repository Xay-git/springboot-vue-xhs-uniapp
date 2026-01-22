package com.dd.admin.business.api;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.business.author.domain.AuthorDto;
import com.dd.admin.business.author.domain.AuthorVo;
import com.dd.admin.business.author.entity.Author;
import com.dd.admin.business.author.service.AuthorService;
import com.dd.admin.business.chat.service.ChatService;
import com.dd.admin.business.file.entity.FileInfo;
import com.dd.admin.business.file.service.FileService;
import com.dd.admin.common.service.MinioService;
import com.dd.admin.common.util.MinioUrlUtil;
import com.dd.admin.business.follow.domain.FollowDto;
import com.dd.admin.business.follow.domain.FollowVo;
import com.dd.admin.business.follow.entity.Follow;
import com.dd.admin.business.follow.service.FollowService;
import com.dd.admin.business.note.service.NoteService;
import com.dd.admin.business.noteImg.service.NoteImgService;
import com.dd.admin.business.reply.service.ReplyService;
import com.dd.admin.business.starNotes.service.StarNotesService;
import com.dd.admin.business.upNotes.service.UpNotesService;
import com.dd.admin.business.upReplys.service.UpReplysService;
import com.dd.admin.common.aop.operationLog.aop.OperLog;
import com.dd.admin.common.aop.operationLog.aop.OperType;
import com.dd.admin.common.exception.ApiException;
import com.dd.admin.common.model.result.ResultBean;
import com.dd.admin.common.util.UrlUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@ApiModel("博主类Api")
@RestController
public class AuthAuthorApi {
    @Autowired
    AuthorService authorService;
    @Autowired
    HttpServletRequest request;
    @Autowired
    FileService fileService;
    @Autowired
    MinioService minioService;
    
    @Autowired
    private MinioUrlUtil minioUrlUtil;
    
    @Value("${server.port}")
    String port;
    @Autowired
    FollowService followService;
    @Autowired
    private UrlUtil urlUtil;
    @ApiOperation(value = "获取博主信息")
    @ApiOperationSupport(order = 1)
    @GetMapping("/api/auth/getMine")
    @OperLog(operModule = "获取当前博主信息",operType = OperType.QUERY,operDesc = "获取当前博主信息")
    public ResultBean<Author> getMine() {
        String authorId = String.valueOf(request.getAttribute("authorId"));
        Author author = authorService.getById(authorId);
        if(author==null){
            throw new ApiException(700,"当前用户信息不存在~");
        }
        if(author.getBirth()!=null){
            author.setAge(DateUtil.ageOfNow(author.getBirth()));
        }
        
        // 刷新MinIO URL
        refreshAuthorUrls(author);
        
        //关注我的列表
        List<FollowVo> followMes = followService.selectFollowList(new FollowDto().setAuthorId(author.getAuthorId()));
        List<FollowVo> myFollows = followService.selectFollowList(new FollowDto().setFollowId(author.getAuthorId()));
        author.setFollow(Long.valueOf(myFollows.size()));
        author.setFans(Long.valueOf(followMes.size()));
        Long upAndStarTotalCount = authorService.selectAuthorUpAndStarTotalCount(authorId);
        author.setUpAndStarCount(upAndStarTotalCount);
        return ResultBean.success(author);
    }

    @ApiOperation(value = "获取作者信息")
    @ApiOperationSupport(order = 1)
    @GetMapping("/api/auth/getAuthor")
    @OperLog(operModule = "获取作者信息",operType = OperType.QUERY,operDesc = "获取作者信息")
    public ResultBean<Author> getAuthor(String authorId) {
        String followId = String.valueOf(request.getAttribute("authorId"));
        Author author = authorService.getById(authorId);
        
        // 刷新MinIO URL
        refreshAuthorUrls(author);
        
        //我是否关注过
        Follow isFollow= followService.selectOneByFollowId(authorId, followId);
        if(isFollow!=null){
            author.setIsFollow(Boolean.TRUE);
        }
        //是否关注过我
        Follow isFollowMe= followService.selectOneByFollowId(followId, authorId);
        if(isFollowMe!=null){
            author.setIsFollowMe(Boolean.TRUE);
        }
        //关注我的列表
        List<FollowVo> followMes = followService.selectFollowList(new FollowDto().setAuthorId(author.getAuthorId()));
        List<FollowVo> myFollows = followService.selectFollowList(new FollowDto().setFollowId(author.getAuthorId()));
        
        // 对FollowVo列表中的头像URL进行MinIO转换
        followMes.forEach(followVo -> {
            if (followVo.getAvatarUrl() != null) {
                followVo.setAvatarUrl(minioUrlUtil.refreshMinioUrl(followVo.getAvatarUrl()));
            }
        });
        myFollows.forEach(followVo -> {
            if (followVo.getAvatarUrl() != null) {
                followVo.setAvatarUrl(minioUrlUtil.refreshMinioUrl(followVo.getAvatarUrl()));
            }
        });

        author.setFollow(Long.valueOf(myFollows.size()));
        author.setFans(Long.valueOf(followMes.size()));
        author.setFollowMes(followMes);
        author.setMyFollows(myFollows);
        Long upAndStarTotalCount = authorService.selectAuthorUpAndStarTotalCount(authorId);
        author.setUpAndStarCount(upAndStarTotalCount);
        return ResultBean.success(author);
    }

    @ApiOperation(value = "修改头像")
    @ApiOperationSupport(order = 1)
    @PostMapping("/api/auth/updateAuthorAvatar")
    @OperLog(operModule = "修改头像",operType = OperType.EDIT,operDesc = "修改头像")
    public ResultBean<Author> updateAuthorAvatar(String fileId) {
        String followId = String.valueOf(request.getAttribute("authorId"));
        Author author = authorService.getById(followId);
        author.setAuthorId(followId);
        author.setAvatarId(fileId);
        FileInfo file = fileService.selectFileByFileId(fileId);
        author.setAvatarUrl(file.getFileUrl());
        authorService.updateById(author);
        return ResultBean.success(author);
    };

    @ApiOperation(value = "修改背景图")
    @ApiOperationSupport(order = 1)
    @PostMapping("/api/auth/updateAuthorBackGround")
    @OperLog(operModule = "修改背景图",operType = OperType.EDIT,operDesc = "修改背景图")
    public ResultBean<Author> updateAuthorBackGround(String fileId) {
        String followId = String.valueOf(request.getAttribute("authorId"));
        Author author = authorService.getById(followId);
        author.setAuthorId(followId);
        author.setBackGroundId(fileId);
        FileInfo file = fileService.selectFileByFileId(fileId);
        author.setBackGroundUrl(file.getFileUrl());
        authorService.updateById(author);
        return ResultBean.success(author);
    };

    @ApiOperation(value = "修改我的信息")
    @ApiOperationSupport(order = 1)
    @PostMapping("/api/auth/updateMine")
    @OperLog(operModule = "修改我的信息",operType = OperType.EDIT,operDesc = "修改我的信息")
    public ResultBean<Author> updateAuthorBackGround(@RequestBody Author author) {
        authorService.updateById(author);
        return ResultBean.success(author);
    };

    @ApiOperation(value = "获取关注目标作者的关注的所有博主")
    @ApiOperationSupport(order = 1)
    @GetMapping("/api/auth/getMyFollows")
    @OperLog(operModule = "getMyFollows",operType = OperType.QUERY,operDesc = "获取关注目标作者的所有粉丝")
    public ResultBean getMyFollows(String authorId) {
        String myId = String.valueOf(request.getAttribute("authorId"));
        //关注我的列表
        List<FollowVo> myFollows = followService.selectMyFollowList(authorId,myId);
        // 对返回的FollowVo列表中的头像URL进行MinIO转换
        myFollows.forEach(followVo -> {
            if (followVo.getAvatarUrl() != null) {
                followVo.setAvatarUrl(minioUrlUtil.refreshMinioUrl(followVo.getAvatarUrl()));
            }
        });
        return ResultBean.success(myFollows);
    }

    @ApiOperation(value = "获取关注目标作者的所有粉丝")
    @ApiOperationSupport(order = 1)
    @GetMapping("/api/auth/getFollowMes")
    @OperLog(operModule = "获取关注目标作者的所有粉丝",operType = OperType.QUERY,operDesc = "获取  关注目标作者的所有粉丝")
    public ResultBean getFollowMes(String authorId) {
        String myId = String.valueOf(request.getAttribute("authorId"));
        //关注我的列表
        List<FollowVo> followMes = followService.selectFollowMeList(authorId,myId);
        // 对返回的FollowVo列表中的头像URL进行MinIO转换
        followMes.forEach(followVo -> {
            if (followVo.getAvatarUrl() != null) {
                followVo.setAvatarUrl(minioUrlUtil.refreshMinioUrl(followVo.getAvatarUrl()));
            }
        });
        return ResultBean.success(followMes);
    }

    @ApiOperation(value = "获取模糊查询博主")
    @ApiOperationSupport(order = 1)
    @GetMapping("/api/auth/getAuthorList")
    @OperLog(operModule = "获取模糊查询博主",operType = OperType.QUERY,operDesc = "获取模糊查询博主")
    public ResultBean getFollowMes(AuthorDto authorDto) {
        String authorId = String.valueOf(request.getAttribute("authorId"));
        authorDto.setAuthorId(authorId);
        //关注我的列表
        IPage<AuthorVo> authorVoIPage = authorService.selectAuthorList(authorDto);
        return ResultBean.success(authorVoIPage);
    }

    @ApiOperation(value = "获取关注目标互相关注的人")
    @ApiOperationSupport(order = 1)
    @GetMapping("/api/auth/getMyFriends")
    @OperLog(operModule = "getMyFriends",operType = OperType.QUERY,operDesc = "获取关注目标互相关注的人")
    public ResultBean getMyFriends(String authorId) {
        //关注我的列表
        List<FollowVo> myFollows = followService.getMyFriends(authorId);
        myFollows.stream().forEach(followVo -> {
            followVo.setIsFollow(Boolean.TRUE);
            followVo.setIsFollowMe(Boolean.TRUE);
            // 对头像URL进行MinIO转换
            if (followVo.getAvatarUrl() != null) {
                followVo.setAvatarUrl(minioUrlUtil.refreshMinioUrl(followVo.getAvatarUrl()));
            }
        });
        return ResultBean.success(myFollows);
    }
    
    /**
     * 刷新作者的MinIO URL
     */
    private void refreshAuthorUrls(Author author) {
        if (author == null) return;
        
        // 刷新头像URL
        author.setAvatarUrl(minioUrlUtil.refreshMinioUrl(author.getAvatarUrl()));
        
        // 刷新背景图URL
        author.setBackGroundUrl(minioUrlUtil.refreshMinioUrl(author.getBackGroundUrl()));
    }

}
