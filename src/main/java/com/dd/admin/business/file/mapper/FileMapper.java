package com.dd.admin.business.file.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.business.file.entity.FileInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.admin.business.file.domain.FileVo;
import com.dd.admin.business.file.domain.FileDto;

import java.util.List;

/**
 * <p>
 * 文件 Mapper 接口
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-05-23
 */
@Mapper
public interface FileMapper extends BaseMapper<FileInfo> {

    IPage<FileVo> selectFilePage(Page<FileVo> page, @Param("fileDto") FileDto fileDto);

    List<FileVo> selectFileList(@Param("fileDto") FileDto fileDto);
}
