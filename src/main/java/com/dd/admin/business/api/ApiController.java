package com.dd.admin.business.api;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.file.FileNameUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.business.note.domain.NoteDto;
import com.dd.admin.business.note.domain.NoteVo;
import com.dd.admin.business.note.entity.Note;
import com.dd.admin.business.note.service.NoteService;
import com.dd.admin.business.noteImg.domain.NoteImgDto;
import com.dd.admin.business.noteImg.domain.NoteImgVo;
import com.dd.admin.business.noteImg.entity.NoteImg;
import com.dd.admin.business.noteImg.service.NoteImgService;
import com.dd.admin.business.upNotes.domain.UpNotesDto;
import com.dd.admin.business.upNotes.domain.UpNotesVo;
import com.dd.admin.business.upNotes.service.UpNotesService;
import com.dd.admin.business.view.service.ViewService;
import com.dd.admin.common.aop.operationLog.aop.OperLog;
import com.dd.admin.common.aop.operationLog.aop.OperType;
import com.dd.admin.common.consts.XhsConst;
import com.dd.admin.common.exception.ApiException;
import com.dd.admin.common.model.result.ResultBean;
import com.dd.admin.common.security.SecurityUtil;
import com.dd.admin.common.security.jwt.JwtTokenUtil;
import com.dd.admin.common.utils.AddressUtils;
import com.dd.admin.common.utils.IPUtils;
import com.dd.admin.common.utils.RandomXiaohongshuAuthorName;
import com.dd.admin.common.utils.StringUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ApiController {
    @Autowired
    NoteService noteService;
    @Autowired
    HttpServletRequest request;
    @Autowired
    NoteImgService noteImgService;
    @Autowired
    UpNotesService upNotesService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    ViewService viewService;
    @ApiOperation(value = "获取所有笔记")
    @ApiOperationSupport(order = 1)
    @GetMapping("/api/notes")
    @OperLog(operModule = "获取所有笔记", operType = OperType.QUERY, operDesc = "获取所有笔记")
    public ResultBean<IPage<NoteVo>> page(NoteDto noteDto) {
        // 3. 从请求头中获取名为"token"的token字符串，用于后续解析获取用户ID
        String token = request.getHeader("token");
        String followId = "";
        try {
            // 4. 尝试使用jwtTokenUtil工具类从获取到的token中解析出用户名（这里假设用户名就是用户ID），如果解析成功则赋值给authorId变量
            followId = jwtTokenUtil.getUsernameFromToken(token);
        } catch (Exception e) {
            // 5. 如果在解析token过程中出现异常，此处捕获异常但不做任何处理，authorId仍为空字符串，后续可根据此情况决定是否继续相关业务逻辑
        }
        if(StringUtil.isNotEmpty(followId)){
            noteDto.setFollowId(followId);
        }

        noteDto.setNoteStatus(XhsConst.NOTE_NORMAL);
        // 1. 通过noteService根据传入的NoteDto参数查询笔记的分页信息，获取包含笔记数据的分页对象
        IPage<NoteVo> pageInfo = noteService.selectNotePage(noteDto);
        // 20. 将处理好的包含笔记信息（已设置点赞状态和点赞数量）的分页对象包装在ResultBean中返回，以便前端或其他调用方获取处理后的结果数据
        return ResultBean.success(pageInfo);
    }

    @ApiOperation(value = "获取ip地址")
    @ApiOperationSupport(order = 1)
    @GetMapping("/api/getIpAddress")
    @OperLog(operModule = "获取ip地址",operType = OperType.QUERY,operDesc = "获取单个笔记")
    public ResultBean<String> getIpAddress() {
        String ipAddr = IPUtils.getIpAddr(request);
        String realAddress = AddressUtils.getRealAddress(ipAddr);
        return ResultBean.success(realAddress);
    }

    @ApiOperation(value = "获取单个笔记")
    @ApiOperationSupport(order = 1)
    @GetMapping("/api/getNote")
    @OperLog(operModule = "获取单个笔记",operType = OperType.QUERY,operDesc = "获取单个笔记")
    public ResultBean<NoteVo> getNoteById(NoteDto noteDto) {
        // 3. 从请求头中获取名为"token"的token字符串，用于后续解析获取用户ID
        String token = request.getHeader("token");
        String followId = "";
        try {
            // 4. 尝试使用jwtTokenUtil工具类从获取到的token中解析出用户名（这里假设用户名就是用户ID），如果解析成功则赋值给authorId变量
            followId = jwtTokenUtil.getUsernameFromToken(token);
        } catch (Exception e) {
            // 5. 如果在解析token过程中出现异常，此处捕获异常但不做任何处理，authorId仍为空字符串，后续可根据此情况决定是否继续相关业务逻辑
        }
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
}
