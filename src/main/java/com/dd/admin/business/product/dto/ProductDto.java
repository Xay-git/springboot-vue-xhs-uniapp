package com.dd.admin.business.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品Dto
 *
 * @author system
 * @date 2024-01-15
 */
@Data
@ApiModel(value = "ProductDto对象", description = "商品Dto")
public class ProductDto {

    @ApiModelProperty(value = "商品ID")
    private String productId;

    @ApiModelProperty(value = "商品名称")
    private String productName;

    @ApiModelProperty(value = "商品编号")
    private String productNo;

    @ApiModelProperty(value = "分类ID")
    private String categoryId;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    @ApiModelProperty(value = "商品价格")
    private BigDecimal productPrice;

    @ApiModelProperty(value = "原价")
    private BigDecimal productOriginalPrice;

    @ApiModelProperty(value = "运费")
    private BigDecimal shippingFee;

    @ApiModelProperty(value = "商品描述")
    private String productDesc;

    @ApiModelProperty(value = "库存数量")
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

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "修改时间")
    private String updateTime;

    @ApiModelProperty(value = "乐观锁字段")
    private Long version;

    @ApiModelProperty(value = "0正常 1删除")
    private Integer deleted;

    @ApiModelProperty(value = "当前页")
    private Integer current;

    @ApiModelProperty(value = "每页条数")
    private Integer size;

    @ApiModelProperty(value = "商品图片URL列表")
    private List<String> imageUrls;

    @ApiModelProperty(value = "商品图片URL列表（兼容字段）")
    private List<String> images;

    @ApiModelProperty(value = "商品图片对象列表（包含id和url）")
    private List<ImageDto> imageObjects;

    @ApiModelProperty(value = "备注")
    private String remark;
}