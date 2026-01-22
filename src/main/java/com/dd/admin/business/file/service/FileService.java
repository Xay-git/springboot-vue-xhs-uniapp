package com.dd.admin.business.file.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.business.file.entity.FileInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.admin.business.file.domain.FileVo;
import com.dd.admin.business.file.domain.FileDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 文件 服务类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-05-23
 */
public interface FileService extends IService<FileInfo> {

    //文件-分页列表
    IPage<FileVo> selectFilePage(FileDto fileDto);

    //文件-列表
    List<FileVo> selectFileList(FileDto fileDto);

    //文件-列表
    FileInfo selectFileByFileId(String fileId);

    //文件-上传
    public FileVo uploadFile(MultipartFile file, String fileSavePath);

    //传入文件id获取文件真实地址
    public String getFileAuthUrl(String fileId);

    //删除文件（包括MinIO中的文件）
    public boolean deleteFile(String fileId);
}
