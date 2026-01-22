package com.dd.admin.business.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商品新增Dto
 *
 * @author system
 * @date 2024-01-15
 */
@Data
@ApiModel(value = "ProductAddDto对象", description = "商品新增Dto")
public class ProductAddDto {

    @ApiModelProperty(value = "商品名称", required = true)
    @NotBlank(message = "商品名称不能为空")
    private String productName;

    @ApiModelProperty(value = "商品编号")
    private String productNo;

    @ApiModelProperty(value = "分类ID", required = true)
    @NotBlank(message = "分类ID不能为空")
    private String categoryId;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    @ApiModelProperty(value = "商品价格", required = true)
    @NotNull(message = "商品价格不能为空")
    private BigDecimal productPrice;

    @ApiModelProperty(value = "原价")
    private BigDecimal productOriginalPrice;

    @ApiModelProperty(value = "运费")
    private BigDecimal shippingFee;

    @ApiModelProperty(value = "商品描述")
    private String productDesc;

    @ApiModelProperty(value = "库存数量", required = true)
    @NotNull(message = "库存数量不能为空")
    private Integer productStock;

    @ApiModelProperty(value = "商品状态 0-在售 1-下架 2-草稿")
    private Integer productStatus;

    @ApiModelProperty(value = "首图ID")
    private String firstPictureId;

    @ApiModelProperty(value = "首图URL")
    private String firstPictureUrl;

    @ApiModelProperty(value = "销量")
    private Integer productSales;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "是否推荐 0-否 1-是")
    private Integer isRecommend;

    @ApiModelProperty(value = "是否热销 0-否 1-是")
    private Integer isHot;

    @ApiModelProperty(value = "是否新品 0-否 1-是")
    private Integer isNew;

    @ApiModelProperty(value = "商品标签")
    private String productTags;

    @ApiModelProperty(value = "商品详情")
    private String productDetail;

    @ApiModelProperty(value = "商品规格")
    private String productSpec;

    @ApiModelProperty(value = "商品参数")
    private String productParams;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "商品图片URL列表")
    private List<String> imageUrls;

    @ApiModelProperty(value = "商品图片URL列表（兼容字段）")
    private List<String> images;

    @ApiModelProperty(value = "商品图片对象列表（包含id和url）")
    private List<ImageDto> imageObjects;
}