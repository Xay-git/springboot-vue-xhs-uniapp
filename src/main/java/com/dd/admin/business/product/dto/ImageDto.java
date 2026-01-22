package com.dd.admin.business.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 图片Dto
 *
 * @author system
 * @date 2024-01-15
 */
@Data
@ApiModel(value = "ImageDto对象", description = "图片Dto")
public class ImageDto {

    @ApiModelProperty(value = "图片ID")
    private String id;

    @ApiModelProperty(value = "图片URL")
    private String url;

    public ImageDto() {
    }

    public ImageDto(String id, String url) {
        this.id = id;
        this.url = url;
    }
}