package com.dd.admin.business.note.controller;

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
import com.dd.admin.business.note.entity.Note;
import com.dd.admin.business.note.domain.NoteVo;
import com.dd.admin.business.note.domain.NoteDto;
import com.dd.admin.business.note.service.NoteService;
import com.dd.admin.business.noteImg.service.NoteImgService;
import com.dd.admin.business.file.service.FileService;
import com.dd.admin.business.author.service.AuthorService;
import com.dd.admin.business.author.entity.Author;
import com.dd.admin.common.utils.IPUtils;
import com.dd.admin.common.utils.AddressUtils;
import com.dd.admin.common.util.UrlUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import com.dd.admin.business.noteImg.entity.NoteImg;
import com.dd.admin.business.file.entity.FileInfo;

/**
 * <p>
 * 笔记表 前端控制器
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-09
 */
@Api(tags = "笔记表")
@RestController
public class NoteController {

    @Autowired
    NoteService noteService;

    @Autowired
    private NoteImgService noteImgService;

    @Autowired
    private FileService fileService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UrlUtil urlUtil;

    @Value("${server.port}")
    private String port;


    @ApiOperation(value = "笔记表-分页列表")
    @ApiOperationSupport(order = 1)
    @GetMapping("/admin/note/page")
    public ResultBean<IPage<NoteVo>> page(NoteDto noteDto) {
        IPage<NoteVo> pageInfo = noteService.selectNotePage(noteDto);
        return ResultBean.success(pageInfo);
    }

    @ApiOperation(value = "笔记表-列表")
    @ApiOperationSupport(order = 2)
    @GetMapping("/admin/note/list")
    public ResultBean<List<NoteVo>> list(NoteDto noteDto) {
        List<NoteVo> list = noteService.selectNoteList(noteDto);
        return ResultBean.success(list);
    }

    @ApiOperation(value = "笔记表-添加")
    @ApiOperationSupport(order = 3)
    @PostMapping("/admin/note/add")
    @Transactional // 添加事务注解
    public ResultBean<Note> add(@RequestBody @Validated NoteDto noteDto) {
        // String authorId = String.valueOf(request.getAttribute("authorId")); // 从请求属性中获取 authorId
        // Author author = authorService.getById(authorId);

        Note note = BeanUtil.copyProperties(noteDto, Note.class);
        // note.setAuthorId(author.getAuthorId());
        // note.setAuthorName(author.getAuthorName());
        // note.setIpAddress(IPUtils.getIpAddr(request)); // 请求IP
        // note.setIpRealAddress(AddressUtils.getRealAddress(note.getIpAddress()));

        // 如果是视频笔记，设置视频地址
        if (noteDto.getNoteType() == 2 && noteDto.getVideoUrl() != null) {
            note.setVideoUrl(noteDto.getVideoUrl());
        } else { // Clear videoUrl if not a video note
            note.setVideoUrl(null);
        }

        // 如果是音乐笔记，设置音乐地址
        if (noteDto.getNoteType() == 3 && noteDto.getMusicUrl() != null) {
            note.setMusicUrl(noteDto.getMusicUrl());
        } else { // Clear musicUrl if not a music note
            note.setMusicUrl(null);
        }

        // 如果前端已经设置了首图URL，则直接使用
        if (noteDto.getFirstPicture() != null && !noteDto.getFirstPicture().isEmpty()) {
            note.setFirstPicture(noteDto.getFirstPicture());
        }

        if(noteDto.getCreateTime()!=null){
            note.setCreateTime(noteDto.getCreateTime());
        }

        System.out.println("NoteController add method - Note object before first save: " + note); // Debugging log
        noteService.save(note); // 第一次保存，获取noteId

        // 先删除旧的图片关联 (对于新增是空操作，但为了与update保持一致)
        noteImgService.deleteNoteImgByNoteId(note.getNoteId());

        List<String> imgs = noteDto.getImgs();
        System.out.println("NoteController add method - imgs from DTO: " + imgs); // Debugging log
        if (imgs != null && !imgs.isEmpty()) {
            List<NoteImg> noteImgList = new ArrayList<>();
            for(int i=0;i<imgs.size();i++){
                NoteImg noteImg = new NoteImg();
                noteImg.setAuthorId(note.getAuthorId());
                noteImg.setNoteId(note.getNoteId());
                noteImg.setImgSort(i);
                
                String imgItem = imgs.get(i);
                String imgUrl = null;
                
                // 判断是fileId还是完整URL
                if (imgItem.startsWith("http")) {
                    // 如果是完整URL，直接使用
                    imgUrl = imgItem;
                } else {
                    // 如果是fileId，通过fileService获取文件信息
                    FileInfo file = fileService.selectFileByFileId(imgItem);
                    if (file != null) {
                        imgUrl = file.getFileUrl();
                    }
                }
                
                if (imgUrl != null) {
                    noteImg.setImgUrl(imgUrl);
                    noteImgList.add(noteImg);
                }
            }
            if (!noteImgList.isEmpty()) { // 只有当图片列表不为空时才保存
                noteImgService.saveBatch(noteImgList);
            }

            // 如果首图还没设置，则使用这张图片作为首图
            if (note.getFirstPicture() == null || note.getFirstPicture().isEmpty()) {
                Note updateNote = noteService.getById(note.getNoteId()); // 重新获取笔记，确保是最新状态
                if (updateNote != null && !noteImgList.isEmpty()) {
                    updateNote.setFirstPicture(noteImgList.get(0).getImgUrl());
                    System.out.println("NoteController add method - Setting firstPicture after img save: " + updateNote.getFirstPicture()); // Debugging log
                    noteService.updateById(updateNote); // 第二次更新，保存首图
                }
            }
        } else {
            // 如果前端没有传入图片，也需要确保封面图被清除
            note.setFirstPicture(null);
            noteService.updateById(note); // 更新笔记以清除封面图
        }

        return ResultBean.success(note);
    }

    @ApiOperation(value = "笔记表-查询")
    @ApiOperationSupport(order = 4)
    @GetMapping("/admin/note/{noteId}")
    public ResultBean<NoteVo> get(@PathVariable @NotBlank String noteId) {
        NoteDto noteDto = new NoteDto();
        noteDto.setNoteId(noteId);
        NoteVo noteVo = noteService.selectNoteDetail(noteDto);
        return ResultBean.success(noteVo);
    }

    @ApiOperation(value = "笔记表-修改")
    @ApiOperationSupport(order = 5)
    @PostMapping("/admin/note/update")
    @Transactional
    public ResultBean<Note> update(@RequestBody @Validated(UpdateGroup.class) NoteDto noteDto) {
        System.out.println("NoteController update method - received NoteDto: " + noteDto); // Debugging log

        // 1. 从数据库获取原始的笔记实体
        Note existingNote = noteService.getById(noteDto.getNoteId());
        if (existingNote == null) {
            return ResultBean.error(404, "笔记不存在");
        }

        // 2. 将DTO中的非空属性值复制到现有实体中
        BeanUtil.copyProperties(noteDto, existingNote, "createTime", "authorId", "authorName"); // 忽略createTime和作者信息

        // 3. 处理不同类型笔记的特殊字段
        // 如果是视频笔记，设置视频地址
        if (noteDto.getNoteType() == 2 && noteDto.getVideoUrl() != null) {
            existingNote.setVideoUrl(noteDto.getVideoUrl());
        } else { // Clear videoUrl if not a video note
            existingNote.setVideoUrl(null);
        }

        // 如果是音乐笔记，设置音乐地址
        if (noteDto.getNoteType() == 3 && noteDto.getMusicUrl() != null) {
            existingNote.setMusicUrl(noteDto.getMusicUrl());
        } else { // Clear musicUrl if not a music note
            existingNote.setMusicUrl(null);
        }

        // 如果前端已经设置了首图URL，则直接使用
        if (noteDto.getFirstPicture() != null && !noteDto.getFirstPicture().isEmpty()) {
            existingNote.setFirstPicture(noteDto.getFirstPicture());
        }

        System.out.println("NoteController update method - Note object before updateById: " + existingNote); // Debugging log
        noteService.updateById(existingNote); // 更新实体

        // 4. 处理图片关联
        // 先删除旧的图片关联
        noteImgService.deleteNoteImgByNoteId(existingNote.getNoteId());

        List<String> imgs = noteDto.getImgs();
        System.out.println("NoteController update method - imgs from DTO: " + imgs); // Debugging log
        if (imgs != null && !imgs.isEmpty()) {
            List<NoteImg> noteImgList = new ArrayList<>();
            for(int i=0;i<imgs.size();i++){
                NoteImg noteImg = new NoteImg();
                noteImg.setAuthorId(existingNote.getAuthorId());
                noteImg.setNoteId(existingNote.getNoteId());
                noteImg.setImgSort(i);
                
                String imgItem = imgs.get(i);
                String imgUrl = null;
                
                // 判断是fileId还是完整URL
                if (imgItem.startsWith("http")) {
                    // 如果是完整URL，直接使用
                    imgUrl = imgItem;
                } else {
                    // 如果是fileId，通过fileService获取文件信息
                    FileInfo file = fileService.selectFileByFileId(imgItem);
                    if (file != null) {
                        imgUrl = file.getFileUrl();
                    }
                }
                
                if (imgUrl != null) {
                    noteImg.setImgUrl(imgUrl);
                    noteImgList.add(noteImg);
                }
            }
            if (!noteImgList.isEmpty()) { // 只有当图片列表不为空时才保存
                noteImgService.saveBatch(noteImgList);
            }

            // 如果首图还没设置，则使用第一张图片作为首图
            if (existingNote.getFirstPicture() == null || existingNote.getFirstPicture().isEmpty()) {
                Note updateNote = noteService.getById(existingNote.getNoteId()); // 重新获取笔记，确保是最新状态
                if (updateNote != null && !noteImgList.isEmpty()) {
                    updateNote.setFirstPicture(noteImgList.get(0).getImgUrl());
                    System.out.println("NoteController update method - Setting firstPicture after img save: " + updateNote.getFirstPicture()); // Debugging log
                    noteService.updateById(updateNote); // 第二次更新，保存首图
                }
            }
        } else {
            // 如果前端没有传入图片，也需要确保封面图被清除（仅对图文笔记）
            if (noteDto.getNoteType() == 1 || noteDto.getNoteType() == 3) {
                existingNote.setFirstPicture(null);
                noteService.updateById(existingNote); // 更新笔记以清除封面图
            }
        }

        return ResultBean.success(existingNote);
    }

    @ApiOperation(value = "笔记表-删除")
    @ApiOperationSupport(order = 6)
    @GetMapping("/admin/note/delete/{noteId}")
    public ResultBean<Note> delete(@PathVariable @NotBlank String noteId) {
        Boolean b = noteService.removeById(noteId);
        return ResultBean.success(b);
    }
}
