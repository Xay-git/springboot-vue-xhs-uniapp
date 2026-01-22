package com.dd.admin.business.file.controller;

import cn.hutool.core.bean.BeanUtil;
import com.dd.admin.common.model.result.UploadResultBean;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.dd.admin.common.model.UpdateGroup;
import com.dd.admin.common.model.result.ResultBean;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import com.dd.admin.business.file.entity.FileInfo;
import com.dd.admin.business.file.domain.FileVo;
import com.dd.admin.business.file.domain.FileDto;
import com.dd.admin.business.file.service.FileService;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.dd.admin.common.util.UrlUtil;

/**
 * <p>
 * 文件 前端控制器
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-05-23
 */
@RestController
@Api(tags = "文件管理")
public class FileController {

    @Autowired
    FileService fileService;
    @Autowired
    HttpServletRequest request;
    @Value("${server.port}")
    String port;

    @Autowired
    private UrlUtil urlUtil;

    @ApiOperation(value = "文件-分页列表")
    @ApiOperationSupport(order = 1)
    @GetMapping("/admin/file/page")
    public ResultBean<IPage<FileVo>> page(FileDto fileDto) {
        IPage<FileVo> pageInfo = fileService.selectFilePage(fileDto);

        return ResultBean.success(pageInfo);
    }

    @ApiOperation(value = "文件-列表")
    @ApiOperationSupport(order = 2)
    @GetMapping("/admin/file/list")
    public ResultBean<List<FileVo>> list(FileDto fileDto) {
        List<FileVo> list = fileService.selectFileList(fileDto);
        return ResultBean.success(list);
    }

    @ApiOperation(value = "文件-添加")
    @ApiOperationSupport(order = 3)
    @PostMapping("/admin/file/add")
    public ResultBean<FileInfo> add(@RequestBody @Validated FileDto fileDto) {
        FileInfo file = BeanUtil.copyProperties(fileDto, FileInfo.class);
        fileService.save(file);
        return ResultBean.success(file);
    }

    @ApiOperation(value = "文件-查询")
    @ApiOperationSupport(order = 4)
    @GetMapping("/admin/file/{fileId}")
    public ResultBean<FileVo> get(@PathVariable @NotBlank String fileId) {
        FileInfo file = fileService.getById(fileId);
        FileVo fileVo = BeanUtil.copyProperties(file,FileVo.class);
        return ResultBean.success(fileVo);
    }

    @ApiOperation(value = "文件-修改")
    @ApiOperationSupport(order = 5)
    @PostMapping("/admin/file/update")
    public ResultBean<FileInfo> update(@RequestBody @Validated(UpdateGroup.class) FileDto fileDto) {
        FileInfo file = BeanUtil.copyProperties(fileDto, FileInfo.class);
        fileService.updateById(file);
        return ResultBean.success(file);
    }

    @ApiOperation(value = "文件-删除")
    @ApiOperationSupport(order = 6)
    @GetMapping("/admin/file/delete/{fileId}")
    public ResultBean<Boolean> delete(@PathVariable @NotBlank String fileId) {
        Boolean b = fileService.deleteFile(fileId);
        return ResultBean.success(b);
    }

    @ApiOperation(value = "文件-上传")
    @PostMapping("/upload")
    @ResponseBody
    public ResultBean layuiUpload(@RequestPart("file") MultipartFile file, String fileSavePath) {
        FileVo uploadResult = fileService.uploadFile(file,fileSavePath);
        String fileId = uploadResult.getFileId();
        HashMap<String, Object> map = new HashMap<>();
        map.put("fileId", fileId);
        map.put("filePath", uploadResult.getFileUrl());
        return ResultBean.success(map);
    }

    @ApiOperation(value = "文件-上传")
    @PostMapping("/appUpload")
    @ResponseBody
    public UploadResultBean<FileVo> appUpload(@RequestPart("file") MultipartFile file, String fileSavePath) {
        FileVo uploadResult = fileService.uploadFile(file,fileSavePath);
        String fileId = uploadResult.getFileId();
        UploadResultBean resultBean = new UploadResultBean();
        resultBean.setResult(uploadResult);
        resultBean.setData(uploadResult.getFileUrl());
        return resultBean;
    }
}
