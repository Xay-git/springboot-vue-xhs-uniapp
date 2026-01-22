package com.dd.admin.business.file.domain;

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
import javax.validation.constraints.NotBlank;
import com.dd.admin.common.model.UpdateGroup;


/**
 * <p>
 * 文件返回对象
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-05-23
 */
@Data
@ApiModel(value="文件接收对象")
public class FileDto {


    @ApiModelProperty(value = "文件id")
    @NotBlank(message = "文件id不能为空",groups = UpdateGroup.class)
    private String fileId;

    @ApiModelProperty(value = "上级id")
    private String parentId;

    @ApiModelProperty(value = "文件仓库（oss仓库）")
    private String fileBucket;

    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @ApiModelProperty(value = "文件后缀")
    private String fileSuffix;

    @ApiModelProperty(value = "文件大小kb")
    private Long fileSizeKb;

    @ApiModelProperty(value = "文件唯一标识id")
    private String finalName;

    @ApiModelProperty(value = "存储路径")
    private String filePath;

    @ApiModelProperty(value = "0文件夹 1文件")
    private Integer fileType;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}
