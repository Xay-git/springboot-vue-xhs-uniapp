package com.dd.admin.business.file.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 文件
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-05-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("business_file")
@ApiModel(value="FileInfo对象", description="文件")
public class FileInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文件id")
    @TableId(value = "FILE_ID", type = IdType.ASSIGN_UUID)
    private String fileId;

    @ApiModelProperty(value = "上级id")
    @TableField("PARENT_ID")
    private String parentId;

    @ApiModelProperty(value = "文件仓库（oss仓库）")
    @TableField("FILE_BUCKET")
    private String fileBucket;

    @ApiModelProperty(value = "文件名称")
    @TableField("FILE_NAME")
    private String fileName;

    @ApiModelProperty(value = "文件后缀")
    @TableField("FILE_SUFFIX")
    private String fileSuffix;

    @ApiModelProperty(value = "文件大小kb")
    @TableField("FILE_SIZE_KB")
    private Long fileSizeKb;

    @ApiModelProperty(value = "文件唯一标识id")
    @TableField("FINAL_NAME")
    private String finalName;

    @ApiModelProperty(value = "存储路径")
    @TableField("FILE_PATH")
    private String filePath;

    @ApiModelProperty(value = "0文件夹 1文件")
    @TableField("FILE_TYPE")
    private Integer fileType;

    @ApiModelProperty(value = "存放的系统路径")
    @TableField("FILE_SYS_PATH")
    private String fileSysPath;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATE_TIME")
    private Date createTime;

    @ApiModelProperty(value = "文件访问URL")
    @TableField("FILE_URL")
    private String fileUrl;


}
