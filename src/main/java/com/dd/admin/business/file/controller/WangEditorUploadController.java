package com.dd.admin.business.file.controller;

import com.dd.admin.business.file.domain.FileVo;
import com.dd.admin.business.file.service.FileService;
import com.dd.admin.common.model.result.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
public class WangEditorUploadController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload/wangeditor")
    public Map<String, Object> upload(@RequestParam("file") MultipartFile file,
                                      @RequestParam(value = "fileSavePath", defaultValue = "wangeditor") String fileSavePath) {
        Map<String, Object> result = new HashMap<>();
        try {
            FileVo fileVo = fileService.uploadFile(file, fileSavePath);

            if (fileVo != null && fileVo.getFileUrl() != null) {
                Map<String, String> data = new HashMap<>();
                data.put("url", fileVo.getFileUrl());
                data.put("alt", fileVo.getFileName());
                data.put("href", "");

                result.put("errno", 0);
                result.put("data", data);
            } else {
                result.put("errno", 1);
                result.put("message", "上传失败，无法获取文件URL");
            }
        } catch (Exception e) {
            result.put("errno", 1);
            result.put("message", "上传出错：" + e.getMessage());
        }
        return result;
    }
} 