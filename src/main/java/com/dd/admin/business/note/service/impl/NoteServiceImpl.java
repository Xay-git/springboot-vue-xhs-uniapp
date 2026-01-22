package com.dd.admin.business.note.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.business.note.domain.ReplayMeVo;
import com.dd.admin.business.note.domain.UpMeVo;
import com.dd.admin.common.model.PageFactory;
import com.dd.admin.business.note.entity.Note;
import com.dd.admin.business.note.mapper.NoteMapper;
import com.dd.admin.business.note.service.NoteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dd.admin.common.utils.StringUtil;
import org.springframework.stereotype.Service;
import com.dd.admin.business.note.domain.NoteVo;
import com.dd.admin.business.note.domain.NoteDto;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import javax.servlet.http.HttpServletRequest;
import com.dd.admin.business.file.entity.FileInfo;
import com.dd.admin.business.file.service.FileService;
import com.dd.admin.business.noteImg.entity.NoteImg;
import com.dd.admin.business.noteImg.service.NoteImgService;
import com.dd.admin.common.util.UrlUtil;
import com.dd.admin.common.util.MinioUrlUtil;

/**
 * <p>
 * 笔记表 服务实现类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-09
 */
@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements NoteService {

    @Autowired
    private FileService fileService;
    
    @Autowired
    private NoteImgService noteImgService;
    
    @Autowired
    private HttpServletRequest request;
    
    @Value("${server.port}")
    private String port;

    @Autowired
    private UrlUtil urlUtil;
    
    @Autowired
    private MinioUrlUtil minioUrlUtil;

    @Override
    public IPage<NoteVo> selectNotePage(NoteDto noteDto) {
        Page page = PageFactory.defaultPage();
        IPage<NoteVo> pageInfo = baseMapper.selectNotePage(page,noteDto);
        if (pageInfo != null && pageInfo.getRecords() != null) {
            for (NoteVo noteVo : pageInfo.getRecords()) {
                if (noteVo.getNoteType() == 1 || noteVo.getNoteType() == 3) { // Graphic-text and music notes
                    List<String> imgUrls = noteImgService.getImgsByNoteId(noteVo.getNoteId());
                    noteVo.setImgList(imgUrls);
                    // 设置首图URL
                    noteVo.setFirstPicture(minioUrlUtil.setFirstPictureUrl(noteVo.getFirstPicture(), imgUrls));
                } else if (noteVo.getNoteType() == 2) { // Video notes
                    // 刷新视频封面URL
                    noteVo.setFirstPicture(minioUrlUtil.refreshMinioUrl(noteVo.getFirstPicture()));
                }
                // 刷新作者头像URL
                noteVo.setAuthorAvatar(minioUrlUtil.refreshMinioUrl(noteVo.getAuthorAvatar()));
            }
        }
        return pageInfo;
    }

    @Override
    public List<NoteVo> selectNoteList(NoteDto noteDto) {
        List<NoteVo> noteList = baseMapper.selectNoteList(noteDto);
        if (noteList != null) {
            for (NoteVo noteVo : noteList) {
                if (noteVo.getNoteType() == 1 || noteVo.getNoteType() == 3) { // Graphic-text and music notes
                    List<String> imgUrls = noteImgService.getImgsByNoteId(noteVo.getNoteId());
                    noteVo.setImgList(imgUrls);
                    // 设置首图URL
                    noteVo.setFirstPicture(minioUrlUtil.setFirstPictureUrl(noteVo.getFirstPicture(), imgUrls));
                } else if (noteVo.getNoteType() == 2) { // Video notes
                    // 刷新视频封面URL
                    noteVo.setFirstPicture(minioUrlUtil.refreshMinioUrl(noteVo.getFirstPicture()));
                }
                // 刷新作者头像URL
                noteVo.setAuthorAvatar(minioUrlUtil.refreshMinioUrl(noteVo.getAuthorAvatar()));
            }
        }
        return noteList;
    }

    @Override
    public NoteVo selectNoteDetail(NoteDto noteDto) {
        NoteVo noteVo = baseMapper.selectNoteDetail(noteDto);
        if (noteVo != null) {
            // If it's a graphic-text or music note, retrieve image URLs
            if (noteVo.getNoteType() == 1 || noteVo.getNoteType() == 3) {
                List<String> imgUrls = noteImgService.getImgsByNoteId(noteVo.getNoteId());
                noteVo.setImgList(imgUrls);
                // 设置首图URL
                noteVo.setFirstPicture(minioUrlUtil.setFirstPictureUrl(noteVo.getFirstPicture(), imgUrls));
            } else if (noteVo.getNoteType() == 2) { // Video notes
                // 刷新视频封面URL
                noteVo.setFirstPicture(minioUrlUtil.refreshMinioUrl(noteVo.getFirstPicture()));
            }
            // 刷新作者头像URL
            noteVo.setAuthorAvatar(minioUrlUtil.refreshMinioUrl(noteVo.getAuthorAvatar()));
            
            // 刷新商品图片URL
            if (noteVo.getProductImage() != null && !noteVo.getProductImage().isEmpty()) {
                noteVo.setProductImage(minioUrlUtil.refreshMinioUrl(noteVo.getProductImage()));
            }
        }
        return noteVo;
    }

    @Override
    public IPage<NoteVo> selectMineUpNotes(String followId) {
        Page page = PageFactory.defaultPage();
        IPage<NoteVo> result = baseMapper.selectMineUpNotes(page,followId);
        if (result != null && result.getRecords() != null) {
            for (NoteVo noteVo : result.getRecords()) {
                if (noteVo.getNoteType() == 1 || noteVo.getNoteType() == 3) { // Graphic-text and music notes
                    List<String> imgUrls = noteImgService.getImgsByNoteId(noteVo.getNoteId());
                    noteVo.setImgList(imgUrls);
                    // 设置首图URL
                    noteVo.setFirstPicture(minioUrlUtil.setFirstPictureUrl(noteVo.getFirstPicture(), imgUrls));
                } else if (noteVo.getNoteType() == 2) { // Video notes
                    // 刷新视频封面URL
                    noteVo.setFirstPicture(minioUrlUtil.refreshMinioUrl(noteVo.getFirstPicture()));
                }
                // 刷新作者头像URL
                noteVo.setAuthorAvatar(minioUrlUtil.refreshMinioUrl(noteVo.getAuthorAvatar()));
            }
        }
        return result;
    }

    @Override
    public IPage<NoteVo> selectMineStarNotes(String followId) {
        Page page = PageFactory.defaultPage();
        IPage<NoteVo> result = baseMapper.selectMineStarNotes(page,followId);
        if (result != null && result.getRecords() != null) {
            for (NoteVo noteVo : result.getRecords()) {
                if (noteVo.getNoteType() == 1 || noteVo.getNoteType() == 3) { // Graphic-text and music notes
                    List<String> imgUrls = noteImgService.getImgsByNoteId(noteVo.getNoteId());
                    noteVo.setImgList(imgUrls);
                    // 设置首图URL
                    noteVo.setFirstPicture(minioUrlUtil.setFirstPictureUrl(noteVo.getFirstPicture(), imgUrls));
                } else if (noteVo.getNoteType() == 2) { // Video notes
                    // 刷新视频封面URL
                    noteVo.setFirstPicture(minioUrlUtil.refreshMinioUrl(noteVo.getFirstPicture()));
                }
                // 刷新作者头像URL
                noteVo.setAuthorAvatar(minioUrlUtil.refreshMinioUrl(noteVo.getAuthorAvatar()));
            }
        }
        return result;
    }

    @Override
    public IPage<UpMeVo> selectUpMeNotes(String authorId) {
        Page page = PageFactory.defaultPage();
        return baseMapper.selectUpMeNotes(page,authorId);
    }

    @Override
    public IPage<ReplayMeVo> selectReplyMeNotes(String authorId) {
        Page page = PageFactory.defaultPage();
        return baseMapper.selectReplyMeNotes(page,authorId);
    }

    @Override
    public IPage<NoteVo> selectNoteListLike( NoteDto noteDto) {
        Page page = PageFactory.defaultPage();
        IPage<NoteVo> result = baseMapper.selectNoteListLike(page,noteDto);
        if (result != null && result.getRecords() != null) {
            for (NoteVo noteVo : result.getRecords()) {
                if (noteVo.getNoteType() == 1 || noteVo.getNoteType() == 3) { // Graphic-text and music notes
                    List<String> imgUrls = noteImgService.getImgsByNoteId(noteVo.getNoteId());
                    noteVo.setImgList(imgUrls);
                    // 设置首图URL
                    noteVo.setFirstPicture(minioUrlUtil.setFirstPictureUrl(noteVo.getFirstPicture(), imgUrls));
                } else if (noteVo.getNoteType() == 2) { // Video notes
                    // 刷新视频封面URL
                    noteVo.setFirstPicture(minioUrlUtil.refreshMinioUrl(noteVo.getFirstPicture()));
                }
                // 刷新作者头像URL
                noteVo.setAuthorAvatar(minioUrlUtil.refreshMinioUrl(noteVo.getAuthorAvatar()));
            }
        }
        return result;
    }

    @Override
    public List<NoteVo> selectHotNoteList() {
        List<NoteVo> noteList = baseMapper.selectHotNoteList();
        if (noteList != null && !noteList.isEmpty()) {
            for (NoteVo noteVo : noteList) {
                if (noteVo.getNoteType() == 1 || noteVo.getNoteType() == 3) { // Graphic-text and music notes
                    List<String> imgUrls = noteImgService.getImgsByNoteId(noteVo.getNoteId());
                    noteVo.setImgList(imgUrls);
                    // 设置首图URL
                    noteVo.setFirstPicture(minioUrlUtil.setFirstPictureUrl(noteVo.getFirstPicture(), imgUrls));
                } else if (noteVo.getNoteType() == 2) { // Video notes
                    // 刷新视频封面URL
                    noteVo.setFirstPicture(minioUrlUtil.refreshMinioUrl(noteVo.getFirstPicture()));
                }
                // 刷新作者头像URL
                noteVo.setAuthorAvatar(minioUrlUtil.refreshMinioUrl(noteVo.getAuthorAvatar()));
            }
        }
        return noteList;
    }
    
    @Override
    public void handleNoteImages(String noteId, String authorId, List<String> imgs) {
        // If no images provided, delete existing ones and return.
        if (imgs == null || imgs.isEmpty()) {
            noteImgService.deleteNoteImgByNoteId(noteId);
            return;
        }

        // Delete old images associated with this note before adding new ones
        noteImgService.deleteNoteImgByNoteId(noteId);

        List<NoteImg> noteImgList = new ArrayList<>();
        for (int i = 0; i < imgs.size(); i++) {
            NoteImg noteImg = new NoteImg();
            noteImg.setAuthorId(authorId);
            noteImg.setNoteId(noteId);
            noteImg.setImgSort(i); // Assign correct sort order

            // Get file path from fileService using fileId (img from frontend is fileId)
            FileInfo file = fileService.selectFileByFileId(imgs.get(i));
            if (file != null) {
                noteImg.setImgUrl(file.getFileUrl());
                noteImgList.add(noteImg);
            }
        }
        // Save all new image associations
        if (!noteImgList.isEmpty()) {
            noteImgService.saveBatch(noteImgList);
        }
    }
}
