package com.dd.admin.business.file.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.common.exception.ApiException;
import com.dd.admin.common.model.PageFactory;
import com.dd.admin.business.file.entity.FileInfo;
import com.dd.admin.business.file.mapper.FileMapper;
import com.dd.admin.business.file.service.FileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dd.admin.common.utils.FileUtil;
import com.dd.admin.common.utils.ToolUtil;
import com.dd.admin.common.util.UrlUtil;
import com.dd.admin.common.service.MinioService;
import com.dd.admin.common.service.QiniuService;
import com.dd.admin.system.setting.service.SystemSettingService;
import net.coobird.thumbnailator.Thumbnails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.dd.admin.business.file.domain.FileVo;
import com.dd.admin.business.file.domain.FileDto;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * <p>
 * 文件 服务实现类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-05-23
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, FileInfo> implements FileService {

    @Autowired
    private MinioService minioService;

    @Autowired
    private QiniuService qiniuService;

    @Autowired
    private SystemSettingService systemSettingService;

    @Autowired
    private HttpServletRequest request;

    @Value("${server.port}")
    private String port;

    @Value("${dd.uploadPath}")
    private String uploadPath;

    @Value("${dd.storage-mode:local}")
    private String defaultStorageMode;

    @Autowired
    private UrlUtil urlUtil;

    @Override
    public IPage<FileVo> selectFilePage(FileDto fileDto) {
        Page page = PageFactory.defaultPage();
        return baseMapper.selectFilePage(page,fileDto);
    }

    @Override
    public List<FileVo> selectFileList(FileDto fileDto) {
        return baseMapper.selectFileList(fileDto);
    }

    @Override
    public FileInfo selectFileByFileId(String fileId) {
        return getById(fileId);
    }

    /**
     * 获取系统文件上传路径
     */
    private String getSysUploadPath() {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            return uploadPath.replace("/",java.io.File.separator);
        } else {
            return uploadPath.replace("\\", java.io.File.separator);
        }
    }

    /**
     * 本地文件上传
     */
    private FileVo uploadFileLocal(MultipartFile file, String fileSavePath, String fileId, String fileSuffix, String originalFilename, String finalName) throws Exception {
        FileVo fileVo = new FileVo();
        fileVo.setFileId(fileId);
        fileVo.setFileSuffix(fileSuffix);
        fileVo.setOriginalFilename(originalFilename);
        fileVo.setFinalName(finalName);

        //获取系统文件上传路径
        String sysUploadPath = getSysUploadPath();

        // 使用Path（推荐）
        Path destPath = Paths.get(sysUploadPath, fileSavePath, finalName);
        Files.createDirectories(destPath.getParent());

        // 构建URL路径（用于访问）
        String filePath = "/upload/" + fileSavePath + "/" + finalName;
        
        // 设置文件路径和系统路径
        fileVo.setFilePath(filePath); // URL路径使用正斜杠
        fileVo.setFileSysPath(destPath.toString()); // 系统路径使用Path API

        String fileUrl = urlUtil.getFileUrl(request, filePath);
        fileVo.setFileUrl(fileUrl);

        // 判断文件类型，只对图片进行压缩
        String contentType = file.getContentType();
        if (contentType != null && contentType.startsWith("image")) {
            try {
                // 尝试压缩图片
                Thumbnails.of(file.getInputStream())
                        .scale(1)
                        .outputQuality(0.25f)
                        .toFile(destPath.toFile());
            } catch (IOException e) {
                // 如果压缩失败，则直接保存原图
                file.transferTo(destPath.toFile());
            }
        } else {
            // 对于非图片文件（视频、音频等），直接保存
            file.transferTo(destPath.toFile());
        }

        return fileVo;
    }

    /**
     * MinIO文件上传
     */
    private FileVo uploadFileMinIO(MultipartFile file, String fileSavePath, String fileId, String fileSuffix, String originalFilename, String finalName) throws Exception {
        FileVo fileVo = new FileVo();
        fileVo.setFileId(fileId);
        fileVo.setFileSuffix(fileSuffix);
        fileVo.setOriginalFilename(originalFilename);
        fileVo.setFinalName(finalName);
        
        // 构建MinIO对象名称
        String objectName = fileSavePath + "/" + finalName;
        
        // 设置MinIO的路径信息
        fileVo.setFilePath(fileSavePath + "/" + finalName);
        fileVo.setFileSysPath(objectName);

        String fileUrl;
        // 判断文件类型，只对图片进行压缩
        String contentType = file.getContentType();
        if (contentType != null && contentType.startsWith("image")) {
            try {
                // 尝试压缩图片并上传到MinIO
                java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();
                Thumbnails.of(file.getInputStream())
                        .scale(1)
                        .outputQuality(0.25f)
                        .toOutputStream(outputStream);
                
                java.io.ByteArrayInputStream compressedInputStream = new java.io.ByteArrayInputStream(outputStream.toByteArray());
                fileUrl = minioService.uploadFile(compressedInputStream, objectName, contentType, outputStream.size());
            } catch (IOException e) {
                // 如果压缩失败，则直接上传原图
                fileUrl = minioService.uploadFile(file, objectName);
            }
        } else {
            // 对于非图片文件（视频、音频等），直接上传
            fileUrl = minioService.uploadFile(file, objectName);
        }
        fileVo.setFileUrl(fileUrl);
        
        return fileVo;
    }

    /**
     * 七牛云文件上传
     */
    private FileVo uploadFileQiniu(MultipartFile file, String fileSavePath, String fileId, String fileSuffix, String originalFilename, String finalName) throws Exception {
        FileVo fileVo = new FileVo();
        fileVo.setFileId(fileId);
        fileVo.setFileSuffix(fileSuffix);
        fileVo.setOriginalFilename(originalFilename);
        fileVo.setFinalName(finalName);
        
        // 构建七牛云对象名称
        String objectName = fileSavePath + "/" + finalName;
        
        // 设置七牛云的路径信息
        fileVo.setFilePath(fileSavePath + "/" + finalName);
        fileVo.setFileSysPath(objectName);

        // 直接上传原始文件到七牛云，不进行压缩
        String fileUrl = qiniuService.uploadFile(file, fileSavePath);
        fileVo.setFileUrl(fileUrl);
        
        return fileVo;
    }

    @Override
    public FileVo uploadFile(MultipartFile file, String fileSavePath) {
        //生成文件的唯一id
        String fileId = IdWorker.get32UUID();

        //获取文件后缀
        String fileSuffix = ToolUtil.getFileSuffix(file.getOriginalFilename());

        //获取文件原始名称
        String originalFilename = file.getOriginalFilename();

        //生成文件的最终名称
        String finalName = fileId + "." + fileSuffix;

        try {
            FileVo fileVo;
            
            // 获取存储模式：优先从系统设置获取，如果不存在再从yml配置获取
            String currentStorageMode = getStorageMode();

            System.out.println("当前存储模式：" + currentStorageMode);
            // 根据配置选择存储方式
            if ("minio".equalsIgnoreCase(currentStorageMode)) {
                fileVo = uploadFileMinIO(file, fileSavePath, fileId, fileSuffix, originalFilename, finalName);
            } else if ("qiniu".equalsIgnoreCase(currentStorageMode)) {
                fileVo = uploadFileQiniu(file, fileSavePath, fileId, fileSuffix, originalFilename, finalName);
            } else {
                fileVo = uploadFileLocal(file, fileSavePath, fileId, fileSuffix, originalFilename, finalName);
            }

            FileInfo fileInfo = new FileInfo();
            //保存文件信息
            fileInfo.setFileId(fileId);
            fileInfo.setFileName(originalFilename);
            fileInfo.setFileSuffix(fileSuffix);
            fileInfo.setFileUrl(fileVo.getFileUrl()); // 访问URL
            fileInfo.setFinalName(finalName);
            
            // 根据存储方式设置文件路径和系统路径
            if ("minio".equalsIgnoreCase(currentStorageMode)) {
                fileInfo.setFilePath(fileVo.getFilePath()); // MinIO相对路径
                fileInfo.setFileSysPath(fileVo.getFileSysPath()); // MinIO对象名称
            } else if ("qiniu".equalsIgnoreCase(currentStorageMode)) {
                fileInfo.setFilePath(fileVo.getFilePath()); // 七牛云相对路径
                fileInfo.setFileSysPath(fileVo.getFileSysPath()); // 七牛云对象名称
            } else {
                fileInfo.setFilePath(fileVo.getFilePath()); // 本地相对路径
                fileInfo.setFileSysPath(fileVo.getFileSysPath()); // 本地完整系统路径
            }
            System.out.println("当前存储模式：" + currentStorageMode);
            System.out.println( "文件路径：" + fileInfo.getFilePath());
            System.out.println( "文件系统路径：" + fileInfo.getFileSysPath());

            //计算文件大小kb
            long kb = new BigDecimal(file.getSize())
                    .divide(BigDecimal.valueOf(1024), 0, BigDecimal.ROUND_HALF_UP)
                    .longValue();
            fileInfo.setFileSizeKb(kb);
            save(fileInfo);
            return fileVo;
        } catch (Exception e) {
            log.error("上传文件错误！", e);
            throw new ApiException("上传文件错误！");
        }
    }


    /**
     * 获取存储模式
     */
    private String getStorageMode() {
        // 优先从系统设置获取，如果不存在再从yml配置获取
        return systemSettingService.getSettingValue("STORAGE_MODE", defaultStorageMode);
    }

    @Override
    public String getFileAuthUrl(String fileId) {
        FileInfo file = getById(fileId);
        if(file!=null){
            String currentStorageMode = getStorageMode();
            // 根据存储模式处理文件URL
            if ("minio".equalsIgnoreCase(currentStorageMode)) {
                // MinIO模式：如果存储的是MinIO对象名称，重新生成访问URL
                if(file.getFileSysPath() != null && !file.getFileSysPath().startsWith("http")) {
                    // MinioService.getFileUrl现在会在MinIO不可用时返回原始URL，不再抛出异常
                    return minioService.getFileUrl(file.getFileSysPath());
                }
            } else {
                // 本地模式：直接返回文件路径
                return file.getFilePath();
            }
            return file.getFilePath();
        }
        return "";
    }

    @Override
    public boolean deleteFile(String fileId) {
        try {
            FileInfo file = getById(fileId);
            if(file != null) {
                String currentStorageMode = getStorageMode();
                // 根据存储模式删除文件
                if ("minio".equalsIgnoreCase(currentStorageMode)) {
                    // MinIO模式：删除MinIO中的文件
                    if(file.getFileSysPath() != null && !file.getFileSysPath().startsWith("http")) {
                        minioService.deleteFile(file.getFileSysPath());
                    }
                } else {
                    // 本地模式：删除本地文件
                    if(file.getFileSysPath() != null) {
                        try {
                            Path filePath = Paths.get(file.getFileSysPath());
                            Files.deleteIfExists(filePath);
                        } catch (Exception e) {
                            log.warn("删除本地文件失败: {}" +  e.getMessage());
                        }
                    }
                }
                // 删除数据库记录
                return removeById(fileId);
            }
            return false;
        } catch (Exception e) {
            log.error("删除文件失败: {}" +  e.getMessage());
            return false;
        }
    }


}
