package com.dd.admin.business.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.business.order.vo.OrderVo;
import com.dd.admin.business.product.dto.ProductDto;
import com.dd.admin.business.product.service.ProductCategoryService;
import com.dd.admin.business.product.service.ProductService;
import com.dd.admin.business.product.vo.ProductCategoryVo;
import com.dd.admin.business.product.vo.ProductVo;
import com.dd.admin.business.cart.service.CartService;
import com.dd.admin.business.cart.dto.CartAddDto;
import com.dd.admin.business.cart.entity.Cart;
import com.dd.admin.business.cart.vo.CartVo;
import com.dd.admin.business.order.service.OrderService;
import com.dd.admin.business.order.dto.OrderAddDto;
import com.dd.admin.business.order.entity.Order;
import com.dd.admin.business.address.service.AddressService;
import com.dd.admin.business.address.dto.AddressDto;
import com.dd.admin.business.address.vo.AddressVo;
import com.dd.admin.business.address.entity.Address;
import com.dd.admin.business.review.service.ProductReviewService;
import com.dd.admin.business.review.dto.ProductReviewDto;
import com.dd.admin.business.review.vo.ProductReviewVo;
import com.dd.admin.common.aop.operationLog.aop.OperLog;
import com.dd.admin.common.aop.operationLog.aop.OperType;
import com.dd.admin.common.model.result.ResultBean;
import com.dd.admin.common.exception.ApiException;
import cn.hutool.core.bean.BeanUtil;
import java.util.ArrayList;
import java.util.List;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import com.dd.admin.common.utils.BigDecimalUtil;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * 移动端商品API接口
 * @author system
 */
@Api(tags = "移动端商品API")
@RestController
@Slf4j
public class AuthProductApi {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private com.dd.admin.business.author.service.AuthorService authorService;

    @Autowired
    private com.dd.admin.business.balance.service.BalanceLogService balanceLogService;

    @Autowired
    private ProductReviewService productReviewService;

    /**
     * 获取用户余额
     * @param request HTTP请求对象
     * @return 用户余额信息
     */
    @ApiOperation(value = "获取用户余额")
    @OperLog(operModule = "用户钱包", operType = OperType.QUERY, operDesc = "获取用户余额")
    @GetMapping("/api/auth/user/balance")
    public ResultBean<Map<String, Object>> getUserBalance(HttpServletRequest request) {
        try {
            // 从请求头获取用户ID（框架已验证）
            String authorId = String.valueOf(request.getAttribute("authorId"));

            log.info("获取用户余额，用户ID: {}", authorId);

            // 获取用户信息
            com.dd.admin.business.author.entity.Author author = authorService.getById(authorId);
            if (author == null) {
                throw new ApiException("用户不存在");
            }

            // 构造返回数据
            Map<String, Object> balanceInfo = new HashMap<>();
            balanceInfo.put("balance", author.getBalance() != null ? author.getBalance() : BigDecimal.ZERO);
            balanceInfo.put("authorId", authorId);
            balanceInfo.put("authorName", author.getAuthorName());

            log.info("获取用户余额成功，用户ID: {}，余额: {}", authorId, author.getBalance());
            return ResultBean.success(balanceInfo);

        } catch (Exception e) {
            log.error("获取用户余额失败", e);
            throw new ApiException("获取用户余额失败: " + e.getMessage());
        }
    }

    /**
     * 获取用户余额变动记录
     * @param request HTTP请求对象
     * @param date 查询日期（格式：YYYY-MM）
     * @return 余额变动记录列表
     */
    @ApiOperation(value = "获取用户余额变动记录")
    @OperLog(operModule = "用户钱包", operType = OperType.QUERY, operDesc = "获取用户余额变动记录")
    @GetMapping("/api/auth/wallet/records")
    public ResultBean<List<Map<String, Object>>> getBalanceRecords(
            HttpServletRequest request,
            @RequestParam(required = false) String date) {
        try {
            // 从请求头获取用户ID（框架已验证）
            String authorId = String.valueOf(request.getAttribute("authorId"));

            log.info("获取用户余额变动记录，用户ID: {}, 查询日期: {}", authorId, date);

            // 构造查询条件
            com.dd.admin.business.balance.dto.BalanceLogDto balanceLogDto = new com.dd.admin.business.balance.dto.BalanceLogDto();
            balanceLogDto.setAuthorId(authorId);

            // 如果有日期参数，设置时间范围
            if (date != null && !date.isEmpty()) {
                balanceLogDto.setStartTime(date + "-01 00:00:00");
                // 计算月末日期
                String[] dateParts = date.split("-");
                if (dateParts.length == 2) {
                    int year = Integer.parseInt(dateParts[0]);
                    int month = Integer.parseInt(dateParts[1]);
                    // 获取该月的最后一天
                    java.util.Calendar cal = java.util.Calendar.getInstance();
                    cal.set(year, month - 1, 1);
                    int lastDay = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
                    balanceLogDto.setEndTime(date + "-" + String.format("%02d", lastDay) + " 23:59:59");
                }
            }

            // 获取余额变动记录
            List<com.dd.admin.business.balance.vo.BalanceLogVo> balanceLogList = balanceLogService.selectBalanceLogList(balanceLogDto);

            // 转换为移动端需要的格式
            List<Map<String, Object>> recordList = new ArrayList<>();
            if (balanceLogList != null) {
                for (com.dd.admin.business.balance.vo.BalanceLogVo balanceLog : balanceLogList) {
                    Map<String, Object> recordMap = new HashMap<>();
                    recordMap.put("title", getRecordTitle(balanceLog.getType(), balanceLog.getRemark()));
                    recordMap.put("createTime", balanceLog.getCreateTime());
                    recordMap.put("amount", balanceLog.getAmount());
                    recordMap.put("type", balanceLog.getType()); // 直接返回数字类型：0-充值 1-消费 2-退款 3-管理员修改
                    recordMap.put("status", "余额 ¥" + (balanceLog.getAfterBalance() != null ? balanceLog.getAfterBalance() : "0.00"));
                    recordList.add(recordMap);
                }
            }

            log.info("获取余额变动记录成功，用户ID: {}，记录数量: {}", authorId, recordList.size());
            return ResultBean.success(recordList);

        } catch (Exception e) {
            log.error("获取余额变动记录失败", e);
            throw new ApiException("获取余额变动记录失败: " + e.getMessage());
        }
    }

    /**
     * 获取记录标题
     * @param type 变动类型
     * @param remark 变动描述
     * @return 记录标题
     */
    private String getRecordTitle(Integer type, String remark) {
        if (remark != null && !remark.isEmpty()) {
            return remark;
        }

        if (type == null) {
            return "余额变动";
        }

        switch (type) {
            case 0:
                return "充值";
            case 1:
                return "消费";
            case 2:
                return "退款";
            case 3:
                return "管理员增加";
            case 4:
                return "管理员减少";
            default:
                return "余额变动";
        }
    }

    /**
     * 用户充值接口
     * @param request HTTP请求对象
     * @param amount 充值金额
     * @param paymentMethod 支付方式
     * @return 充值结果
     */
    @ApiOperation(value = "用户充值")
    @OperLog(operModule = "用户钱包", operType = OperType.ADD, operDesc = "用户充值")
    @PostMapping("/api/auth/wallet/recharge")
    public ResultBean<Map<String, Object>> recharge(
            HttpServletRequest request,
            @RequestParam BigDecimal amount,
            @RequestParam String paymentMethod) {
        try {
            // 从请求头获取用户ID（框架已验证）
            String authorId = String.valueOf(request.getAttribute("authorId"));

            log.info("用户充值，用户ID: {}, 充值金额: {}, 支付方式: {}", authorId, amount, paymentMethod);

            // 验证充值金额
            if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new ApiException("充值金额必须大于0");
            }

            if (amount.compareTo(new BigDecimal("0.1")) < 0) {
                throw new ApiException("充值金额不能少于0.1元");
            }

            if (amount.compareTo(new BigDecimal("1000000")) > 0) {
                throw new ApiException("单次充值金额不能超过1000000元");
            }

            // 获取用户信息
            com.dd.admin.business.author.entity.Author author = authorService.getById(authorId);
            if (author == null) {
                throw new ApiException("用户不存在");
            }

            // 记录充值前余额
            BigDecimal beforeBalance = author.getBalance() != null ? author.getBalance() : BigDecimal.ZERO;

            // 更新用户余额
            BigDecimal afterBalance = beforeBalance.add(amount);
            author.setBalance(afterBalance);
            boolean updateResult = authorService.updateById(author);

            if (!updateResult) {
                throw new ApiException("充值失败，请重试");
            }

            // 记录余额变动
            com.dd.admin.business.balance.entity.BalanceLog balanceLog = new com.dd.admin.business.balance.entity.BalanceLog();
            balanceLog.setAuthorId(authorId);
            balanceLog.setAmount(amount);
            balanceLog.setBeforeBalance(beforeBalance);
            balanceLog.setAfterBalance(afterBalance);
            balanceLog.setType(0); // 0-充值
            balanceLog.setRemark("用户充值 - " + paymentMethod);
            balanceLog.setOperatorId(authorId);
            balanceLog.setOperatorName(author.getAuthorName());

            // 保存余额变动记录
            boolean saveLogResult = balanceLogService.save(balanceLog);
            if (!saveLogResult) {
                log.warn("余额变动记录保存失败，用户ID: {}, 充值金额: {}", authorId, amount);
            }

            // 构造返回数据
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("amount", amount);
            result.put("balance", afterBalance);
            result.put("paymentMethod", paymentMethod);
            result.put("message", "充值成功");

            log.info("用户充值成功，用户ID: {}, 充值金额: {}, 充值后余额: {}", authorId, amount, afterBalance);
            return ResultBean.success(result);

        } catch (Exception e) {
            log.error("用户充值失败", e);
            throw new ApiException("充值失败: " + e.getMessage());
        }
    }


    /**
     * 退款接口
     * @param request HTTP请求对象
     * @param amount 退款金额
     * @param relatedId 关联订单ID
     * @param remark 备注
     * @return 退款结果
     */
    @ApiOperation(value = "订单退款")
    @OperLog(operModule = "用户钱包", operType = OperType.UPDATE, operDesc = "订单退款")
    @PostMapping("/api/auth/wallet/refund")
    public ResultBean<Map<String, Object>> refundBalance(
            HttpServletRequest request,
            @RequestParam BigDecimal amount,
            @RequestParam String relatedId,
            @RequestParam(required = false) String remark) {
        try {
            // 从请求头获取用户ID
            // 从请求头获取用户ID（框架已验证）
            String authorId = String.valueOf(request.getAttribute("authorId"));

            log.info("订单退款，用户ID: {}, 退款金额: {}, 订单ID: {}, 备注: {}", authorId, amount, relatedId, remark);

            // 验证退款金额
            if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new ApiException("退款金额必须大于0");
            }

            // 获取用户信息
            com.dd.admin.business.author.entity.Author author = authorService.getById(authorId);
            if (author == null) {
                throw new ApiException("用户不存在");
            }

            // 记录退款前余额
            BigDecimal beforeBalance = author.getBalance() != null ? author.getBalance() : BigDecimal.ZERO;

            // 计算退款后余额
            BigDecimal afterBalance = beforeBalance.add(amount);

            // 更新用户余额
            author.setBalance(afterBalance);
            boolean updateResult = authorService.updateById(author);

            if (!updateResult) {
                throw new ApiException("退款失败，请重试");
            }

            // 记录余额变动
            com.dd.admin.business.balance.entity.BalanceLog balanceLog = new com.dd.admin.business.balance.entity.BalanceLog();
            balanceLog.setAuthorId(authorId);
            balanceLog.setAmount(amount);
            balanceLog.setBeforeBalance(beforeBalance);
            balanceLog.setAfterBalance(afterBalance);
            balanceLog.setType(2); // 2-退款
            balanceLog.setRelatedId(relatedId);
            balanceLog.setRemark(remark != null ? remark : "订单退款");
            balanceLog.setOperatorId(authorId);
            balanceLog.setOperatorName(author.getAuthorName());

            // 保存余额变动记录
            boolean saveLogResult = balanceLogService.save(balanceLog);
            if (!saveLogResult) {
                log.warn("余额变动记录保存失败，用户ID: {}, 退款金额: {}, 订单ID: {}", authorId, amount, relatedId);
            }

            // 构造返回数据
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("amount", amount);
            result.put("beforeBalance", beforeBalance);
            result.put("afterBalance", afterBalance);
            result.put("relatedId", relatedId);
            result.put("message", "退款成功");

            log.info("订单退款成功，用户ID: {}, 退款金额: {}, 退款后余额: {}, 订单ID: {}",
                    authorId, amount, afterBalance, relatedId);
            return ResultBean.success(result);

        } catch (Exception e) {
            log.error("订单退款失败", e);
            throw new ApiException("退款失败: " + e.getMessage());
        }
    }

    /**
     * 获取钱包信息（余额信息）
     * @param request HTTP请求对象
     * @return 钱包信息
     */
    @ApiOperation(value = "获取钱包信息")
    @OperLog(operModule = "用户钱包", operType = OperType.QUERY, operDesc = "获取钱包信息")
    @GetMapping("/api/auth/wallet/info")
    public ResultBean<Map<String, Object>> getWalletInfo(HttpServletRequest request) {
        try {
            // 从请求头获取用户ID（框架已验证）
            String authorId = String.valueOf(request.getAttribute("authorId"));

            log.info("获取钱包信息，用户ID: {}", authorId);

            // 获取用户信息
            com.dd.admin.business.author.entity.Author author = authorService.getById(authorId);
            if (author == null) {
                throw new ApiException("用户不存在");
            }

            // 构造返回数据
            Map<String, Object> walletInfo = new HashMap<>();
            walletInfo.put("balance", author.getBalance() != null ? author.getBalance().toString() : "0.00");
            walletInfo.put("authorId", authorId);
            walletInfo.put("authorName", author.getAuthorName());

            log.info("获取钱包信息成功，用户ID: {}，余额: {}", authorId, author.getBalance());
            return ResultBean.success(walletInfo);

        } catch (Exception e) {
            log.error("获取钱包信息失败", e);
            throw new ApiException("获取钱包信息失败: " + e.getMessage());
        }
    }

    /**
     * 获取订单列表
     * @param request HTTP请求对象
     * @return 订单列表
     */
    @ApiOperation(value = "获取订单列表")
    @OperLog(operModule = "订单管理", operType = OperType.QUERY, operDesc = "获取订单列表")
    @GetMapping("/api/auth/order/list")
    public ResultBean<List<Map<String, Object>>> getOrderList(
            HttpServletRequest request,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit) {
        try {
            // 从请求头获取用户ID（框架已验证）
            String authorId = String.valueOf(request.getAttribute("authorId"));

            log.info("获取用户订单列表，用户ID: {}, 状态: {}, 页码: {}, 每页数量: {}", authorId, status, page, limit);

            // 构造查询条件
            com.dd.admin.business.order.dto.OrderDto orderDto = new com.dd.admin.business.order.dto.OrderDto();
            orderDto.setAuthorId(authorId);
            orderDto.setOrderStatus(status);
            orderDto.setPage(page);
            orderDto.setLimit(limit);

            // 获取订单列表
            IPage<com.dd.admin.business.order.vo.OrderVo> orderPage = orderService.selectOrderList(orderDto);

            // 转换为移动端需要的格式
            List<Map<String, Object>> orderList = new ArrayList<>();
            if (orderPage != null && orderPage.getRecords() != null) {
                for (com.dd.admin.business.order.vo.OrderVo order : orderPage.getRecords()) {
                    Map<String, Object> orderMap = new HashMap<>();
                    orderMap.put("orderId", order.getOrderId());
                    orderMap.put("orderNo", order.getOrderNo());
                    orderMap.put("totalAmount", order.getTotalAmount());
                    orderMap.put("orderStatus", order.getOrderStatus());
                    orderMap.put("orderStatusName", getOrderStatusName(order.getOrderStatus()));
                    orderMap.put("createTime", order.getCreateTime());
                    orderMap.put("payTime", order.getPayTime());
                    orderMap.put("shipTime", order.getShipTime());
                    orderMap.put("completeTime", order.getCompleteTime());
                    orderMap.put("cancelTime", order.getCancelTime());
                    orderMap.put("cancelReason", order.getCancelReason());
                    orderMap.put("remark", order.getRemark());

                    // 添加订单详情信息
                    List<Map<String, Object>> itemsList = new ArrayList<>();
                    if (order.getItems() != null && !order.getItems().isEmpty()) {
                        // 按店铺分组（这里简化为一个店铺）
                        Map<String, Object> shopMap = new HashMap<>();
                        shopMap.put("shopName", "小红书商城");
                        shopMap.put("shopId", "1");

                        List<Map<String, Object>> goodsList = new ArrayList<>();
                        for (com.dd.admin.business.order.entity.OrderItem item : order.getItems()) {
                            Map<String, Object> goodsMap = new HashMap<>();
                            goodsMap.put("goods_img", item.getProductImage() != null ? item.getProductImage() : "/static/img/demo1.jpg");
                            goodsMap.put("goods_name", item.getProductName());
                            goodsMap.put("goods_price", item.getProductPrice());
                            goodsMap.put("goods_buynum", item.getQuantity());
                            goodsMap.put("productId", item.getProductId());
                            goodsList.add(goodsMap);
                        }
                        shopMap.put("goods", goodsList);
                        itemsList.add(shopMap);
                    } else {
                        // 如果没有订单详情，提供默认数据
                        Map<String, Object> shopMap = new HashMap<>();
                        shopMap.put("shopName", "小红书商城");
                        shopMap.put("shopId", "1");

                        List<Map<String, Object>> goodsList = new ArrayList<>();
                        Map<String, Object> goodsMap = new HashMap<>();
                        goodsMap.put("goods_img", "/static/img/demo1.jpg");
                        goodsMap.put("goods_name", "订单商品");
                        goodsMap.put("goods_price", order.getTotalAmount());
                        goodsMap.put("goods_buynum", 1);
                        goodsList.add(goodsMap);
                        shopMap.put("goods", goodsList);
                        itemsList.add(shopMap);
                    }
                    orderMap.put("items", itemsList);

                    orderList.add(orderMap);
                }
            }

            log.info("获取订单列表成功，用户ID: {}，订单数量: {}", authorId, orderList.size());
            return ResultBean.success(orderList);

        } catch (Exception e) {
            log.error("获取订单列表失败", e);
            throw new ApiException("获取订单列表失败: " + e.getMessage());
        }
    }

    /**
     * 获取订单状态名称
     * @param status 订单状态
     * @return 状态名称
     */
    private String getOrderStatusName(Integer status) {
        if (status == null) {
            return "未知";
        }
        switch (status) {
            case 0:
                return "待支付";
            case 1:
                return "待发货";
            case 2:
                return "已发货";
            case 3:
                return "已完成";
            case 4:
                return "已取消";
            case 5:
                return "申请退款";
            case 6:
                return "申请被拒绝";
            case 7:
                return "已评价";
            default:
                return "未知";
        }
    }

    /**
     * 获取商品分页列表
     */
    @ApiOperation(value = "获取商品分页列表")
    @OperLog(operModule = "商品管理", operType = OperType.QUERY, operDesc = "获取商品分页列表")
    @GetMapping("/api/auth/product")
    public ResultBean<IPage<ProductVo>> getProductList(
            @ApiParam(value = "页码", example = "1") @RequestParam(defaultValue = "1") Integer page,
            @ApiParam(value = "每页数量", example = "6") @RequestParam(defaultValue = "6") Integer limit,
            @ApiParam(value = "分类ID") @RequestParam(required = false) String categoryId) {

        log.info("移动端获取商品列表，页码: {}, 每页数量: {}, 分类ID: {}", page, limit, categoryId);

        try {
            // 创建分页对象
            Page<ProductVo> pageObj = new Page<>(page, limit);

            // 创建查询条件
            ProductDto productDto = new ProductDto();
            productDto.setCategoryId(categoryId);
            // 只查询上架状态的商品
            productDto.setProductStatus(0);

            log.info("查询条件 - 分类ID: {}, 商品状态: {}", categoryId, productDto.getProductStatus());

            // 分页查询商品
            IPage<ProductVo> result = productService.selectProductPage(pageObj, productDto);

            log.info("移动端商品列表查询成功，总数: {}, 当前页: {}", result.getTotal(), result.getCurrent());

            return ResultBean.success(result);
        } catch (Exception e) {
            log.error("移动端获取商品列表失败", e);
            return ResultBean.error("获取商品列表失败");
        }
    }

    /**
     * 获取商品分类列表
     */
    @ApiOperation(value = "获取商品分类列表")
    @OperLog(operModule = "商品管理", operType = OperType.QUERY, operDesc = "获取商品分类列表")
    @GetMapping("/api/auth/categories")
    public ResultBean<List<ProductCategoryVo>> getCategoryList() {

        log.info("移动端获取商品分类列表");

        try {
            // 获取所有启用的分类
            List<ProductCategoryVo> categoryList = productCategoryService.selectCategoryList();

            log.info("移动端商品分类列表查询成功，数量: {}", categoryList.size());

            return ResultBean.success(categoryList);
        } catch (Exception e) {
            log.error("移动端获取商品分类列表失败", e);
            return ResultBean.error("获取商品分类列表失败");
        }
    }

    /**
     * 获取商品详情
     */
    @ApiOperation(value = "获取商品详情")
    @OperLog(operModule = "商品管理", operType = OperType.QUERY, operDesc = "获取商品详情")
    @GetMapping("/api/auth/product/{productId}")
    public ResultBean<ProductVo> getProductDetail(
            @ApiParam(value = "商品ID", required = true) @PathVariable String productId) {

        log.info("移动端获取商品详情，商品ID: {}", productId);

        try {
            // 根据ID查询商品详情
            ProductVo productVo = productService.selectProductById(productId);

            if (productVo == null) {
                log.warn("商品不存在，商品ID: {}", productId);
                return ResultBean.error("商品不存在");
            }

            // 检查商品状态，只返回在售状态的商品
            if (productVo.getProductStatus() != null && productVo.getProductStatus() != 0) {
                log.warn("商品已下架或不可用，商品ID: {}, 状态: {}", productId, productVo.getProductStatus());
                return ResultBean.error("商品已下架或不可用");
            }

            // 更新浏览量（浏览数加1）
            try {
                boolean updateViewsSuccess = productService.incrementViews(productId);
                if (updateViewsSuccess) {
                    log.info("商品浏览量更新成功，商品ID: {}", productId);
                    // 重新查询商品详情以获取最新的浏览量
                    productVo = productService.selectProductById(productId);
                } else {
                    log.warn("商品浏览量更新失败，商品ID: {}", productId);
                }
            } catch (Exception viewsException) {
                log.error("更新商品浏览量时发生异常，商品ID: {}", productId, viewsException);
                // 浏览量更新失败不影响商品详情的返回
            }

            log.info("移动端商品详情查询成功，商品ID: {}, 商品名称: {}, 当前浏览量: {}",
                    productId, productVo.getProductName(), productVo.getProductViews());

            return ResultBean.success(productVo);
        } catch (Exception e) {
            log.error("移动端获取商品详情失败，商品ID: {}", productId, e);
            return ResultBean.error("获取商品详情失败");
        }
    }

    /**
     * 获取购物车列表
     */
    @ApiOperation(value = "获取购物车列表")
    @OperLog(operModule = "购物车管理", operType = OperType.QUERY, operDesc = "获取购物车列表")
    @GetMapping("/api/auth/cart/list")
    public ResultBean<List<CartVo>> getCartList(HttpServletRequest request) {

        // 从token中获取用户ID
        String authorId = String.valueOf(request.getAttribute("authorId"));

        log.info("移动端获取购物车列表，用户ID: {}", authorId);

        try {
            // 获取用户购物车列表
            List<Cart> cartList = cartService.getUserCartList(authorId);

            // 转换为VO对象并组装店铺信息
            List<CartVo> cartVoList = new ArrayList<>();

            for (Cart cart : cartList) {
                CartVo cartVo = new CartVo();
                BeanUtil.copyProperties(cart, cartVo);
                // 设置商品价格
                cartVo.setProductPrice(cart.getProductPrice());
                // 设置店铺信息（硬编码为官方自营）
                cartVo.setShopName("官方自营");
                cartVo.setShopId("1");

                // 获取商品详情以获取运费信息
                try {
                    ProductVo product = productService.selectProductById(cart.getProductId());
                    if (product != null) {
                        cartVo.setShippingFee(product.getShippingFee() != null ? product.getShippingFee() : BigDecimal.ZERO);
                    } else {
                        cartVo.setShippingFee(BigDecimal.ZERO);
                    }
                } catch (Exception e) {
                    log.warn("获取商品运费失败，商品ID: {}, 使用默认运费0", cart.getProductId(), e);
                    cartVo.setShippingFee(BigDecimal.ZERO);
                }

                cartVoList.add(cartVo);
            }

            log.info("移动端购物车列表查询成功，用户ID: {}, 商品数量: {}",
                    authorId, cartVoList.size());

            return ResultBean.success(cartVoList);
        } catch (Exception e) {
            log.error("移动端获取购物车列表失败，用户ID: {}", authorId, e);
            return ResultBean.error("获取购物车列表失败");
        }
    }

    /**
     * 更新购物车商品数量
     */
    @ApiOperation(value = "更新购物车商品数量")
    @OperLog(operModule = "购物车管理", operType = OperType.UPDATE, operDesc = "更新购物车商品数量")
    @PostMapping("/api/auth/cart/update")
    public ResultBean<CartVo> updateCartQuantity(@RequestBody Map<String, Object> requestData, HttpServletRequest request) {

        // 从token中获取用户ID
        String authorId = String.valueOf(request.getAttribute("authorId"));
        String productId = (String) requestData.get("productId");
        Integer quantity = (Integer) requestData.get("quantity");

        log.info("移动端更新购物车数量，用户ID: {}, 商品ID: {}, 数量: {}",
                authorId, productId, quantity);

        try {
            // 验证参数
            if (productId == null || quantity == null || quantity <= 0) {
                log.warn("参数错误，商品ID: {}, 数量: {}", productId, quantity);
                return ResultBean.error("参数错误");
            }

            // 获取商品信息以获取最新价格
            ProductVo productVo = productService.selectProductById(productId);
            if (productVo == null) {
                log.warn("商品不存在，商品ID: {}", productId);
                return ResultBean.error("商品不存在");
            }

            // 检查商品状态
            if (productVo.getProductStatus() != null && productVo.getProductStatus() != 0) {
                log.warn("商品已下架或不可用，商品ID: {}, 状态: {}",
                        productId, productVo.getProductStatus());
                return ResultBean.error("商品已下架或不可用");
            }

            // 检查库存
            if (productVo.getProductStock() != null && quantity > productVo.getProductStock()) {
                log.warn("库存不足，商品ID: {}, 请求数量: {}, 库存数量: {}",
                        productId, quantity, productVo.getProductStock());
                return ResultBean.error("库存不足");
            }

            // 查找购物车记录
            Cart existingCart = cartService.selectByAuthorIdAndProductId(authorId, productId);
            if (existingCart == null) {
                log.warn("购物车中不存在该商品，用户ID: {}, 商品ID: {}", authorId, productId);
                return ResultBean.error("购物车中不存在该商品");
            }

            // 更新购物车数量和价格
            existingCart.setQuantity(quantity);
            existingCart.setProductPrice(productVo.getProductPrice());
            existingCart.setProductName(productVo.getProductName());
            existingCart.setProductImage(productVo.getFirstPictureUrl());

            // 更新商品价格为最新价格
            existingCart.setProductPrice(productVo.getProductPrice());

            // 更新购物车记录
            boolean updated = cartService.updateById(existingCart);
            if (!updated) {
                log.error("更新购物车失败，用户ID: {}, 商品ID: {}", authorId, productId);
                return ResultBean.error("更新购物车失败");
            }

            // 返回更新后的购物车信息
            CartVo cartVo = new CartVo();
            BeanUtil.copyProperties(existingCart, cartVo);
            cartVo.setProductPrice(existingCart.getProductPrice());
            cartVo.setShopName("官方自营");
            cartVo.setShopId("1");

            log.info("移动端更新购物车数量成功，用户ID: {}, 商品ID: {}, 新数量: {}",
                    authorId, productId, quantity);

            return ResultBean.success(cartVo);
        } catch (Exception e) {
            log.error("移动端更新购物车数量失败，用户ID: {}, 商品ID: {}",
                    authorId, productId, e);
            return ResultBean.error("更新购物车数量失败");
        }
    }

    /**
     * 删除购物车商品
     */
    @ApiOperation(value = "删除购物车商品")
    @OperLog(operModule = "购物车管理", operType = OperType.DELETE, operDesc = "删除购物车商品")
    @PostMapping("/api/auth/cart/delete")
    public ResultBean<String> deleteCartItem(@RequestBody Map<String, Object> requestData, HttpServletRequest request) {

        // 从token中获取用户ID
        String authorId = String.valueOf(request.getAttribute("authorId"));
        String productId = (String) requestData.get("productId");

        log.info("移动端删除购物车商品，用户ID: {}, 商品ID: {}", authorId, productId);

        try {
            // 验证参数
            if (productId == null || productId.isEmpty()) {
                log.warn("商品ID不能为空");
                return ResultBean.error("商品ID不能为空");
            }

            // 查找购物车记录
            Cart existingCart = cartService.selectByAuthorIdAndProductId(authorId, productId);
            if (existingCart == null) {
                log.warn("购物车中不存在该商品，用户ID: {}, 商品ID: {}", authorId, productId);
                return ResultBean.error("购物车中不存在该商品");
            }

            // 删除购物车记录
            cartService.deleteCart(existingCart.getCartId());

            log.info("移动端删除购物车商品成功，用户ID: {}, 商品ID: {}", authorId, productId);

            return ResultBean.success("删除成功");
        } catch (Exception e) {
            log.error("移动端删除购物车商品失败，用户ID: {}, 商品ID: {}", authorId, productId, e);
            return ResultBean.error("删除购物车商品失败");
        }
    }

    /**
     * 加入购物车
     */
    @ApiOperation(value = "加入购物车")
    @OperLog(operModule = "购物车管理", operType = OperType.ADD, operDesc = "加入购物车")
    @PostMapping("/api/auth/cart/add")
    public ResultBean<String> addToCart(@RequestBody @Validated CartAddDto cartAddDto, HttpServletRequest request) {

        // 从token中获取用户ID
        String authorId = String.valueOf(request.getAttribute("authorId"));
        cartAddDto.setAuthorId(authorId);

        log.info("移动端加入购物车，用户ID: {}, 商品ID: {}, 数量: {}",
                authorId, cartAddDto.getProductId(), cartAddDto.getQuantity());

        try {
            // 验证商品是否存在且可购买
            ProductVo productVo = productService.selectProductById(cartAddDto.getProductId());
            if (productVo == null) {
                log.warn("商品不存在，商品ID: {}", cartAddDto.getProductId());
                throw new ApiException("商品不存在");
            }

            // 检查商品状态
            if (productVo.getProductStatus() != null && productVo.getProductStatus() != 0) {
                log.warn("商品已下架或不可用，商品ID: {}, 状态: {}",
                        cartAddDto.getProductId(), productVo.getProductStatus());
                throw new ApiException("商品已下架或不可用");
            }

            // 检查库存
            if (productVo.getProductStock() != null &&
                cartAddDto.getQuantity() > productVo.getProductStock()) {
                log.warn("库存不足，商品ID: {}, 请求数量: {}, 库存数量: {}",
                        cartAddDto.getProductId(), cartAddDto.getQuantity(), productVo.getProductStock());
                throw new ApiException("库存不足");
            }

            // 设置商品信息
            cartAddDto.setProductName(productVo.getProductName());
            cartAddDto.setPrice(productVo.getProductPrice());
            cartAddDto.setProductImage(productVo.getFirstPictureUrl());
            cartAddDto.setSelected(1); // 默认选中

            // 加入购物车
            cartService.addCart(cartAddDto);

            log.info("移动端加入购物车成功，用户ID: {}, 商品ID: {}",
                    authorId, cartAddDto.getProductId());

            return ResultBean.success("加入购物车成功");
        } catch (ApiException e) {
            log.error("移动端加入购物车失败: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("移动端加入购物车失败，用户ID: {}, 商品ID: {}",
                    authorId, cartAddDto.getProductId(), e);
            return ResultBean.error("加入购物车失败");
        }
    }

    /**
     * 创建订单
     */
    @ApiOperation(value = "创建订单")
    @OperLog(operModule = "订单管理", operType = OperType.ADD, operDesc = "创建订单")
    @PostMapping("/api/auth/order/create")
    @Transactional(rollbackFor = Exception.class)
    public ResultBean<Map<String, Object>> createOrder(@RequestBody @Validated OrderAddDto orderAddDto, HttpServletRequest request) {

        // 从请求中获取用户ID（框架已验证）
        String authorId = String.valueOf(request.getAttribute("authorId"));

        // 设置用户ID到订单DTO中
        orderAddDto.setAuthorId(authorId);

        log.info("移动端创建订单，用户ID: {}, 地址ID: {}, 总金额: {}",
                orderAddDto.getAuthorId(), orderAddDto.getAddressId(), orderAddDto.getTotalAmount());

        try {
            // 在创建订单前先检查所有商品的库存
            if (orderAddDto.getItems() != null && !orderAddDto.getItems().isEmpty()) {
                for (OrderAddDto.OrderItemDto item : orderAddDto.getItems()) {
                    // 检查库存是否充足，如果不足会抛出包含商品名称的异常
                    productService.checkStock(item.getProductId(), item.getQuantity());
                }
            }

            // 创建订单
            Order order = orderService.createOrder(orderAddDto);

            log.info("移动端创建订单成功，订单ID: {}, 订单号: {}", order.getOrderId(), order.getOrderNo());

            // 更新商品库存和销量
            if (orderAddDto.getItems() != null && !orderAddDto.getItems().isEmpty()) {
                for (OrderAddDto.OrderItemDto item : orderAddDto.getItems()) {
                    boolean updateSuccess = productService.updateStockAndSales(item.getProductId(), item.getQuantity());
                    if (!updateSuccess) {
                        throw new ApiException("更新商品库存失败，商品ID: " + item.getProductId());
                    }
                    log.info("更新商品库存和销量成功，商品ID: {}, 购买数量: {}", item.getProductId(), item.getQuantity());
                }
            }

            // 扣除用户余额
            boolean deductSuccess = authorService.deductBalance(authorId, order.getTotalAmount());
            if (!deductSuccess) {
                throw new ApiException("余额不足，扣除失败");
            }

            // 记录余额变动日志
            balanceLogService.addConsumptionLog(
                authorId,
                order.getTotalAmount(),
                order.getOrderId(),
                "订单消费 - 订单号: " + order.getOrderNo()
            );

            log.info("扣除用户余额成功，用户ID: {}, 扣除金额: {}", authorId, order.getTotalAmount());

            // 仅当从购物车下单时才删除购物车商品
            if ("cart".equals(orderAddDto.getOrderSource()) && orderAddDto.getItems() != null && !orderAddDto.getItems().isEmpty()) {
                for (OrderAddDto.OrderItemDto item : orderAddDto.getItems()) {
                    boolean deleteSuccess = cartService.deleteByAuthorIdAndProductId(authorId, item.getProductId());
                    if (!deleteSuccess) {
                        log.error("删除购物车商品失败，用户ID: {}, 商品ID: {}", authorId, item.getProductId());
                        throw new ApiException("删除购物车商品失败，商品ID: " + item.getProductId());
                    }
                    log.info("删除购物车商品成功，用户ID: {}, 商品ID: {}", authorId, item.getProductId());
                }
            }

            // 返回订单信息
            Map<String, Object> result = new HashMap<>();
            result.put("orderId", order.getOrderId());
            result.put("orderNo", order.getOrderNo());
            result.put("totalAmount", order.getTotalAmount());
            result.put("orderStatus", order.getOrderStatus());

            return ResultBean.success(result);
        } catch (ApiException e) {
            log.error("移动端创建订单失败: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("移动端创建订单失败", e);
            return ResultBean.error("创建订单失败");
        }
    }

    /**
     * 获取用户地址列表
     */
    @ApiOperation(value = "获取用户地址列表")
    @OperLog(operModule = "地址管理", operType = OperType.QUERY, operDesc = "获取用户地址列表")
    @GetMapping("/api/auth/address/list")
    public ResultBean<List<AddressVo>> getAddressList(HttpServletRequest request) {
        try {
            // 从请求头获取用户ID（框架已验证）
            String authorId = String.valueOf(request.getAttribute("authorId"));

            log.info("获取用户地址列表，用户ID: {}", authorId);

            // 获取地址列表
            List<Address> addressList = addressService.getUserAddressList(authorId);

            // 转换为VO
            List<AddressVo> addressVoList = new ArrayList<>();
            for (Address address : addressList) {
                AddressVo addressVo = new AddressVo();
                BeanUtil.copyProperties(address, addressVo);
                addressVoList.add(addressVo);
            }

            return ResultBean.success(addressVoList);
        } catch (ApiException e) {
            log.error("获取用户地址列表失败: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("获取用户地址列表失败", e);
            return ResultBean.error("获取地址列表失败");
        }
    }

    /**
     * 添加地址
     */
    @ApiOperation(value = "添加地址")
    @OperLog(operModule = "地址管理", operType = OperType.ADD, operDesc = "添加地址")
    @PostMapping("/api/auth/address/add")
    public ResultBean<String> addAddress(@RequestBody @Validated AddressDto addressDto, HttpServletRequest request) {
        try {
            // 从请求头获取用户ID（框架已验证）
            String authorId = String.valueOf(request.getAttribute("authorId"));

            // 设置用户ID
            addressDto.setAuthorId(authorId);

            log.info("添加地址，用户ID: {}, 收货人: {}", authorId, addressDto.getReceiverName());

            // 转换为实体
            Address address = new Address();
            BeanUtil.copyProperties(addressDto, address);

            // 如果没有设置默认值，设置为非默认
            if (address.getIsDefault() == null) {
                address.setIsDefault(0);
            }

            // 添加地址
            boolean success = addressService.addAddress(address);

            if (success) {
                log.info("添加地址成功，地址ID: {}", address.getAddressId());
                return ResultBean.success("添加地址成功");
            } else {
                return ResultBean.error("添加地址失败");
            }
        } catch (ApiException e) {
            log.error("添加地址失败: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("添加地址失败", e);
            return ResultBean.error("添加地址失败");
        }
    }

    /**
     * 更新地址
     */
    @ApiOperation(value = "更新地址")
    @OperLog(operModule = "地址管理", operType = OperType.UPDATE, operDesc = "更新地址")
    @PostMapping("/api/auth/address/update")
    public ResultBean<String> updateAddress(@RequestBody @Validated AddressDto addressDto, HttpServletRequest request) {
        try {
            // 从请求头获取用户ID（框架已验证）
            String authorId = String.valueOf(request.getAttribute("authorId"));

            // 设置用户ID
            addressDto.setAuthorId(authorId);

            log.info("更新地址，用户ID: {}, 地址ID: {}", authorId, addressDto.getAddressId());

            // 转换为实体
            Address address = new Address();
            BeanUtil.copyProperties(addressDto, address);

            // 更新地址
            boolean success = addressService.updateAddress(address);

            if (success) {
                log.info("更新地址成功，地址ID: {}", address.getAddressId());
                return ResultBean.success("更新地址成功");
            } else {
                return ResultBean.error("更新地址失败");
            }
        } catch (ApiException e) {
            log.error("更新地址失败: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("更新地址失败", e);
            return ResultBean.error("更新地址失败");
        }
    }

    /**
     * 设置默认地址
     */
    @ApiOperation(value = "设置默认地址")
    @OperLog(operModule = "地址管理", operType = OperType.UPDATE, operDesc = "设置默认地址")
    @PostMapping("/api/auth/address/setDefault")
    public ResultBean<String> setDefaultAddress(@RequestBody Map<String, Object> requestData, HttpServletRequest request) {
        try {
            // 从请求头获取用户ID（框架已验证）
            String authorId = String.valueOf(request.getAttribute("authorId"));

            // 从请求体获取地址ID
            String addressId = (String) requestData.get("addressId");
            if (addressId == null || addressId.isEmpty()) {
                throw new ApiException("地址ID不能为空");
            }

            log.info("设置默认地址，用户ID: {}, 地址ID: {}", authorId, addressId);

            // 设置默认地址
            boolean success = addressService.setDefaultAddress(addressId, authorId);

            if (success) {
                log.info("设置默认地址成功，地址ID: {}", addressId);
                return ResultBean.success("设置默认地址成功");
            } else {
                return ResultBean.error("设置默认地址失败");
            }
        } catch (ApiException e) {
            log.error("设置默认地址失败: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("设置默认地址失败", e);
            return ResultBean.error("设置默认地址失败");
        }
    }

    /**
     * 删除地址
     */
    @ApiOperation(value = "删除地址")
    @OperLog(operModule = "地址管理", operType = OperType.DELETE, operDesc = "删除地址")
    @PostMapping("/api/auth/address/delete")
    public ResultBean<String> deleteAddress(@RequestBody Map<String, Object> requestData, HttpServletRequest request) {
        try {
            // 从请求头获取用户ID（框架已验证）
            String authorId = String.valueOf(request.getAttribute("authorId"));

            // 从请求体获取地址ID
            String addressId = (String) requestData.get("addressId");
            if (addressId == null || addressId.isEmpty()) {
                throw new ApiException("地址ID不能为空");
            }

            log.info("删除地址，用户ID: {}, 地址ID: {}", authorId, addressId);

            // 删除地址
            boolean success = addressService.deleteAddress(addressId);

            if (success) {
                log.info("删除地址成功，地址ID: {}", addressId);
                return ResultBean.success("删除地址成功");
            } else {
                return ResultBean.error("删除地址失败");
            }
        } catch (ApiException e) {
            log.error("删除地址失败: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("删除地址失败", e);
            return ResultBean.error("删除地址失败");
        }
    }

    /**
     * 确认收货
     * @param request HTTP请求对象
     * @param orderNumber 订单号
     * @return 确认收货结果
     */
    @ApiOperation(value = "确认收货")
    @OperLog(operModule = "订单管理", operType = OperType.UPDATE, operDesc = "确认收货")
    @PostMapping("/api/auth/order/complete")
    @Transactional(rollbackFor = Exception.class)
    public ResultBean<Map<String, Object>> completeOrder(
            @RequestBody Map<String, Object> requestData,
            HttpServletRequest request) {

        // 从请求中获取用户ID（框架已验证）
        String authorId = String.valueOf(request.getAttribute("authorId"));

        // 从请求体获取订单号
        String orderNumber = (String) requestData.get("orderNumber");
        if (orderNumber == null || orderNumber.trim().isEmpty()) {
            throw new ApiException("订单号不能为空");
        }

        log.info("确认收货，用户ID: {}, 订单号: {}", authorId, orderNumber);

        try {
            // 根据订单号获取订单详情
            com.dd.admin.business.order.vo.OrderVo order = orderService.selectOrderByOrderNo(orderNumber);

            if (order == null) {
                log.warn("订单不存在，订单号: {}", orderNumber);
                return ResultBean.error("订单不存在");
            }

            // 验证订单是否属于当前用户
            if (!authorId.equals(order.getAuthorId())) {
                log.warn("无权操作该订单，用户ID: {}, 订单用户ID: {}", authorId, order.getAuthorId());
                return ResultBean.error("无权操作该订单");
            }

            // 验证订单状态是否为已发货
            // if (order.getOrderStatus() != 2) {
            //     log.warn("订单状态不正确，无法确认收货，当前状态: {}", order.getOrderStatus());
            //     return ResultBean.error("订单状态不正确，无法确认收货");
            // }

            // 调用订单服务完成订单
            boolean success = orderService.completeOrder(order.getOrderId());

            if (!success) {
                log.error("确认收货失败，订单ID: {}", order.getOrderId());
                return ResultBean.error("确认收货失败");
            }

            // 构造返回数据
            Map<String, Object> result = new HashMap<>();
            result.put("orderId", order.getOrderId());
            result.put("orderNo", order.getOrderNo());
            result.put("message", "确认收货成功");

            log.info("确认收货成功，用户ID: {}, 订单号: {}", authorId, orderNumber);
            return ResultBean.success(result);

        } catch (ApiException e) {
            log.error("确认收货失败: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("确认收货失败，订单号: {}", orderNumber, e);
            return ResultBean.error("确认收货失败: " + e.getMessage());
        }
    }

    /**
     * 获取订单详情
     * @param request HTTP请求对象
     * @param orderNumber 订单号
     * @return 订单详情
     */
    @ApiOperation(value = "获取订单详情")
    @OperLog(operModule = "订单管理", operType = OperType.QUERY, operDesc = "获取订单详情")
    @GetMapping("/api/auth/order/getOrderDetail")
    public ResultBean<Map<String, Object>> getOrderDetail(
            @RequestParam String orderNumber,
            HttpServletRequest request) {
        try {
            // 从请求头获取用户ID（框架已验证）
            String authorId = String.valueOf(request.getAttribute("authorId"));

            log.info("获取订单详情，用户ID: {}, 订单号: {}", authorId, orderNumber);

            // 根据订单号获取订单详情
            com.dd.admin.business.order.vo.OrderVo order = orderService.selectOrderByOrderNo(orderNumber);

            if (order == null) {
                log.warn("订单不存在，订单号: {}", orderNumber);
                return ResultBean.error("订单不存在");
            }

            // 验证订单是否属于当前用户
            if (!authorId.equals(order.getAuthorId())) {
                log.warn("无权访问该订单，用户ID: {}, 订单号: {}", authorId, orderNumber);
                return ResultBean.error("无权访问该订单");
            }

            // 构造返回数据
            Map<String, Object> orderDetail = new HashMap<>();
            orderDetail.put("orderId", order.getOrderId());
            orderDetail.put("orderNo", order.getOrderNo());
            orderDetail.put("totalAmount", order.getTotalAmount());
            orderDetail.put("orderStatus", order.getOrderStatus());
            orderDetail.put("createTime", order.getCreateTime());
            orderDetail.put("payTime", order.getPayTime() != null ? order.getPayTime() : order.getCreateTime()); // 使用余额支付，下单即支付
            orderDetail.put("shipTime", order.getShipTime());
            orderDetail.put("completeTime", order.getCompleteTime());
            orderDetail.put("cancelTime", order.getCancelTime());
            orderDetail.put("cancelReason", order.getCancelReason());
            orderDetail.put("remark", order.getRemark());

            // 退款相关信息
            orderDetail.put("refundAmount", order.getRefundAmount());
            orderDetail.put("refundReason", order.getRefundReason());
            orderDetail.put("refundApplyTime", order.getRefundApplyTime());
            orderDetail.put("refundProcessTime", order.getRefundProcessTime());
            orderDetail.put("refundProcessRemark", order.getRefundProcessRemark());
            orderDetail.put("refundRejectReason", order.getRefundRejectReason());
            orderDetail.put("rejectReason", order.getRefundRejectReason()); // 前端使用的字段名

            // 物流信息
            orderDetail.put("trackingNumber", order.getTrackingNumber());
            orderDetail.put("logisticsNumber", order.getTrackingNumber()); // 兼容字段

            // 金额信息
            orderDetail.put("goodsAmount", order.getGoodsAmount());
            orderDetail.put("shippingFee", order.getShippingFee());

            // 收货地址信息
            orderDetail.put("receiverName", order.getReceiverName());
            orderDetail.put("receiverPhone", order.getReceiverPhone());
            orderDetail.put("receiverAddress", order.getReceiverAddress());

            // 订单商品列表
            List<Map<String, Object>> itemsList = new ArrayList<>();
            if (order.getItems() != null && !order.getItems().isEmpty()) {
                for (com.dd.admin.business.order.entity.OrderItem item : order.getItems()) {
                    Map<String, Object> itemMap = new HashMap<>();
                    itemMap.put("productId", item.getProductId());
                    itemMap.put("productName", item.getProductName());
                    itemMap.put("productPrice", item.getProductPrice());
                    itemMap.put("productImage", item.getProductImage() != null ? item.getProductImage() : "/static/img/demo1.jpg");
                    itemMap.put("quantity", item.getQuantity());
                    itemsList.add(itemMap);
                }
            }
            orderDetail.put("items", itemsList);

            log.info("获取订单详情成功，用户ID: {}, 订单号: {}", authorId, orderNumber);
            return ResultBean.success(orderDetail);

        } catch (ApiException e) {
            log.error("获取订单详情失败: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("获取订单详情失败，订单号: {}", orderNumber, e);
            return ResultBean.error("获取订单详情失败: " + e.getMessage());
        }
    }

    /**
     * 申请退款
     * @param request HTTP请求对象
     * @param requestData 退款申请数据
     * @return 申请结果
     */
    @ApiOperation(value = "申请退款")
    @OperLog(operModule = "订单管理", operType = OperType.UPDATE, operDesc = "申请退款")
    @PostMapping("/api/auth/order/refund/apply")
    @Transactional(rollbackFor = Exception.class)
    public ResultBean<Map<String, Object>> applyRefund(
            @RequestBody Map<String, Object> requestData,
            HttpServletRequest request) {
        try {
            // 从请求头获取用户ID（框架已验证）
            String authorId = String.valueOf(request.getAttribute("authorId"));

            String orderNumber = (String) requestData.get("orderNumber");
            BigDecimal refundAmount = new BigDecimal(requestData.get("refundAmount").toString());
            String refundReason = (String) requestData.get("refundReason");
            String returnTrackingNumber = (String) requestData.get("returnTrackingNumber");

            log.info("申请退款，用户ID: {}, 订单号: {}, 退款金额: {}", authorId, orderNumber, refundAmount);

            // 获取订单信息
            com.dd.admin.business.order.vo.OrderVo order = orderService.selectOrderByOrderNo(orderNumber);
            if (order == null) {
                return ResultBean.error("订单不存在");
            }

            // 验证订单是否属于当前用户
            if (!authorId.equals(order.getAuthorId())) {
                return ResultBean.error("无权操作该订单");
            }

            // 验证订单状态（只有已付款、已发货、已完成、申请被拒绝的订单可以申请退款）
            if (order.getOrderStatus() != 1 && order.getOrderStatus() != 2 && order.getOrderStatus() != 3 && order.getOrderStatus() != 6) {
                return ResultBean.error("当前订单状态不支持退款");
            }

            // 验证退款金额
            if (refundAmount.compareTo(BigDecimal.ZERO) <= 0) {
                return ResultBean.error("退款金额必须大于0");
            }

            if (refundAmount.compareTo(order.getTotalAmount()) > 0) {
                return ResultBean.error("退款金额不能超过订单金额");
            }

            // 更新订单状态为申请退款（状态5）
            Order orderEntity = new Order();
            orderEntity.setOrderId(order.getOrderId());
            orderEntity.setOrderStatus(5); // 申请退款
            orderEntity.setRefundAmount(refundAmount);
            orderEntity.setRefundReason(refundReason);
            orderEntity.setReturnTrackingNumber(returnTrackingNumber);
            orderEntity.setRefundApplyTime(new java.util.Date());

            boolean updateResult = orderService.updateById(orderEntity);
            if (!updateResult) {
                return ResultBean.error("申请退款失败");
            }

            Map<String, Object> result = new HashMap<>();
            result.put("orderNumber", orderNumber);
            result.put("refundAmount", refundAmount);
            result.put("status", "申请已提交，等待处理");

            log.info("申请退款成功，用户ID: {}, 订单号: {}", authorId, orderNumber);
            return ResultBean.success(result);

        } catch (Exception e) {
            log.error("申请退款失败", e);
            return ResultBean.error("申请退款失败: " + e.getMessage());
        }
    }

    /**
     * 查看物流信息
     * @param request HTTP请求对象
     * @param orderNumber 订单号
     * @param trackingNumber 物流单号
     * @return 物流信息
     */
    @ApiOperation(value = "查看物流信息")
    @OperLog(operModule = "订单管理", operType = OperType.QUERY, operDesc = "查看物流信息")
    @GetMapping("/api/auth/order/logistics")
    public ResultBean<Map<String, Object>> getLogistics(
            HttpServletRequest request,
            @RequestParam String orderNumber,
            @RequestParam(required = false) String trackingNumber) {
        try {
            // 从请求头获取用户ID（框架已验证）
            String authorId = String.valueOf(request.getAttribute("authorId"));

            log.info("查看物流信息，用户ID: {}, 订单号: {}, 物流单号: {}", authorId, orderNumber, trackingNumber);

            // 获取订单信息
            com.dd.admin.business.order.vo.OrderVo order = orderService.selectOrderByOrderNo(orderNumber);
            if (order == null) {
                return ResultBean.error("订单不存在");
            }

            // 验证订单是否属于当前用户
            if (!authorId.equals(order.getAuthorId())) {
                return ResultBean.error("无权查看该订单物流信息");
            }

            // 验证订单状态（只有已发货、已完成的订单才有物流信息）
            if (order.getOrderStatus() != 2 && order.getOrderStatus() != 3) {
                return ResultBean.error("当前订单状态暂无物流信息");
            }

            Map<String, Object> logistics = new HashMap<>();
            logistics.put("orderNumber", orderNumber);
            logistics.put("trackingNumber", order.getTrackingNumber() != null ? order.getTrackingNumber() : trackingNumber);
            logistics.put("company", "顺丰速运"); // 默认物流公司，实际项目中可以从数据库获取
            logistics.put("status", getLogisticsStatus(order.getOrderStatus()));
            logistics.put("lastUpdate", order.getShipTime() != null ? order.getShipTime() : new java.util.Date());
            logistics.put("currentLocation", "运输中"); // 实际项目中可以调用第三方物流API获取

            // 模拟物流轨迹
            java.util.List<Map<String, Object>> traces = new java.util.ArrayList<>();
            Map<String, Object> trace1 = new HashMap<>();
            trace1.put("time", order.getShipTime());
            trace1.put("status", "已发货");
            trace1.put("location", "发货仓库");
            traces.add(trace1);

            if (order.getOrderStatus() == 3) {
                Map<String, Object> trace2 = new HashMap<>();
                trace2.put("time", order.getCompleteTime());
                trace2.put("status", "已签收");
                trace2.put("location", "目的地");
                traces.add(trace2);
            }

            logistics.put("traces", traces);

            log.info("查看物流信息成功，用户ID: {}, 订单号: {}", authorId, orderNumber);
            return ResultBean.success(logistics);

        } catch (Exception e) {
            log.error("查看物流信息失败", e);
            return ResultBean.error("查看物流信息失败: " + e.getMessage());
        }
    }

    /**
     * 获取物流状态描述
     * @param orderStatus 订单状态
     * @return 物流状态
     */
    private String getLogisticsStatus(Integer orderStatus) {
        switch (orderStatus) {
            case 2:
                return "运输中";
            case 3:
                return "已签收";
            default:
                return "未知状态";
        }
    }

    /**
     * 取消退款申请
     * @param request HTTP请求对象
     * @param requestData 取消数据
     * @return 取消结果
     */
    @ApiOperation(value = "取消退款申请")
    @OperLog(operModule = "订单管理", operType = OperType.UPDATE, operDesc = "取消退款申请")
    @PostMapping("/api/auth/order/refund/cancel")
    @Transactional(rollbackFor = Exception.class)
    public ResultBean<Map<String, Object>> cancelRefund(
            @RequestBody Map<String, Object> requestData,
            HttpServletRequest request) {
        try {
            // 从请求头获取用户ID（框架已验证）
            String authorId = String.valueOf(request.getAttribute("authorId"));
            String orderNumber = (String) requestData.get("orderNumber");

            log.info("取消退款申请，用户ID: {}, 订单号: {}", authorId, orderNumber);

            if (orderNumber == null || orderNumber.trim().isEmpty()) {
                return ResultBean.error("订单号不能为空");
            }

            // 获取订单信息
            com.dd.admin.business.order.vo.OrderVo orderVo = orderService.selectOrderByOrderNo(orderNumber);
            if (orderVo == null) {
                return ResultBean.error("订单不存在");
            }

            // 验证订单是否属于当前用户
            if (!authorId.equals(orderVo.getAuthorId())) {
                return ResultBean.error("无权操作该订单");
            }

            // 验证订单状态（只有申请退款状态的订单才能取消退款）
            if (orderVo.getOrderStatus() != 5) {
                return ResultBean.error("当前订单状态不允许取消退款申请");
            }

            // 获取订单实体进行更新
            com.dd.admin.business.order.entity.Order order = orderService.getById(orderVo.getOrderId());
            if (order == null) {
                return ResultBean.error("订单不存在");
            }

            // 更新订单状态
            // 根据之前的状态决定恢复到哪个状态
            // 如果有物流单号说明已发货，恢复到已发货状态(2)，否则恢复到已付款状态(1)
            Integer newStatus = (orderVo.getTrackingNumber() != null && !orderVo.getTrackingNumber().trim().isEmpty()) ? 2 : 1;
            order.setOrderStatus(newStatus);
            order.setRefundAmount(null);
            order.setRefundReason(null);
            order.setReturnTrackingNumber(null);
            order.setRefundApplyTime(null);

            boolean updateResult = orderService.updateById(order);
            if (!updateResult) {
                return ResultBean.error("取消退款申请失败");
            }

            Map<String, Object> result = new HashMap<>();
            result.put("orderNumber", orderNumber);
            result.put("newStatus", newStatus);
            result.put("message", "退款申请已取消");

            log.info("取消退款申请成功，用户ID: {}, 订单号: {}, 新状态: {}", authorId, orderNumber, newStatus);
            return ResultBean.success(result);

        } catch (Exception e) {
            log.error("取消退款申请失败", e);
            return ResultBean.error("取消退款申请失败: " + e.getMessage());
        }
    }


    @ApiOperation(value = "取消退款申请（从拒绝状态恢复）")
    @OperLog(operModule = "订单管理", operType = OperType.UPDATE, operDesc = "取消退款申请（从拒绝状态恢复）")
    @PostMapping("/api/auth/order/refund/cancelApplication")
    @Transactional(rollbackFor = Exception.class)
    public ResultBean<Map<String, Object>> cancelApplication(
            @RequestBody Map<String, Object> requestData,
            HttpServletRequest request) {
        try {
            String orderNumber = (String) requestData.get("orderNumber");

            log.info("取消退款申请，订单号: {}", orderNumber);

            // 获取订单信息
            com.dd.admin.business.order.vo.OrderVo order = orderService.selectOrderByOrderNo(orderNumber);
            if (order == null) {
                return ResultBean.error("订单不存在");
            }

            // 验证订单状态（只有申请被拒绝状态的订单可以取消申请）
            if (order.getOrderStatus() != 6) {
                return ResultBean.error("订单状态错误，无法取消申请");
            }

            Order orderEntity = new Order();
            orderEntity.setOrderId(order.getOrderId());

            // 根据发货状态恢复到相应状态
            // 如果有物流单号说明已发货，恢复到已发货状态(2)，否则恢复到已付款状态(1)
            if (order.getTrackingNumber() != null && !order.getTrackingNumber().trim().isEmpty()) {
                orderEntity.setOrderStatus(2); // 已发货
            } else {
                orderEntity.setOrderStatus(1); // 已付款
            }

            // 清空退款相关字段
            orderEntity.setRefundAmount(null);
            orderEntity.setRefundReason(null);
            orderEntity.setRefundApplyTime(null);
            orderEntity.setRefundProcessTime(null);
            orderEntity.setRefundProcessRemark(null);

            boolean updateResult = orderService.updateById(orderEntity);
            if (!updateResult) {
                return ResultBean.error("取消申请失败");
            }

            Map<String, Object> result = new HashMap<>();
            result.put("orderNumber", orderNumber);
            result.put("status", "申请已取消，订单状态已恢复");

            log.info("取消退款申请成功，订单号: {}", orderNumber);
            return ResultBean.success(result);

        } catch (Exception e) {
            log.error("取消退款申请失败", e);
            return ResultBean.error("取消退款申请失败: " + e.getMessage());
        }
    }

    /**
     * 获取订单商品评价信息
     * @param request HTTP请求对象
     * @param orderNumber 订单号
     * @return 订单商品评价信息
     */
    @ApiOperation(value = "获取订单商品评价信息")
    @OperLog(operModule = "商品评价", operType = OperType.QUERY, operDesc = "获取订单商品评价信息")
    @GetMapping("/api/auth/order/review/items")
    public ResultBean<List<ProductReviewVo>> getOrderReviewItems(
            HttpServletRequest request,
            @RequestParam String orderNumber) {
        return getOrderReviewInfo(request, orderNumber);
    }

    @ApiOperation(value = "获取订单商品评价信息")
    @OperLog(operModule = "商品评价", operType = OperType.QUERY, operDesc = "获取订单商品评价信息")
    @GetMapping("/api/auth/product/review/getOrderReviewInfo")
    public ResultBean<List<ProductReviewVo>> getOrderReviewInfo(
            HttpServletRequest request,
            @RequestParam String orderNumber) {
        try {
            // 从请求头获取用户ID
            String authorId = String.valueOf(request.getAttribute("authorId"));

            log.info("获取订单商品评价信息，用户ID: {}，订单号: {}", authorId, orderNumber);

            // 根据订单号获取订单ID
            com.dd.admin.business.order.vo.OrderVo orderVo = orderService.selectOrderByOrderNo(orderNumber);
            if (orderVo == null) {
                throw new ApiException("订单不存在");
            }

            // 转换为Order实体
            Order order = new Order();
            BeanUtil.copyProperties(orderVo, order);


            // 获取订单商品评价信息
            List<ProductReviewVo> reviewItems = productReviewService.getOrderItemsForReview(order.getOrderId(), authorId);

            log.info("获取订单商品评价信息成功，用户ID: {}，订单号: {}，商品数量: {}", authorId, orderNumber, reviewItems.size());
            return ResultBean.success(reviewItems);

        } catch (ApiException e) {
            log.error("获取订单商品评价信息失败: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("获取订单商品评价信息异常", e);
            throw new ApiException("获取订单商品评价信息失败");
        }
    }

    /**
     * 提交商品评价
     * @param request HTTP请求对象
     * @param requestData 请求数据
     * @return 提交结果
     */
    @ApiOperation(value = "提交商品评价")
    @OperLog(operModule = "商品评价", operType = OperType.ADD, operDesc = "提交商品评价")
    @PostMapping("/api/auth/order/review/submit")
    @Transactional(rollbackFor = Exception.class)
    public ResultBean<Map<String, Object>> submitReview(
            HttpServletRequest request,
            @RequestBody Map<String, Object> requestData) {
        return submitProductReview(request, requestData);
    }

    @ApiOperation(value = "提交商品评价")
    @OperLog(operModule = "商品评价", operType = OperType.ADD, operDesc = "提交商品评价")
    @PostMapping("/api/auth/product/review/submit")
    @Transactional(rollbackFor = Exception.class)
    public ResultBean<Map<String, Object>> submitProductReview(
            HttpServletRequest request,
            @RequestBody Map<String, Object> requestData) {
        try {
            // 从请求头获取用户ID
            String authorId = String.valueOf(request.getAttribute("authorId"));

            log.info("提交商品评价，用户ID: {}，请求数据: {}", authorId, requestData);

            // 获取请求参数
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> reviewsData = (List<Map<String, Object>>) requestData.get("productReviews");

            if (reviewsData == null || reviewsData.isEmpty()) {
                throw new ApiException("评价信息不能为空");
            }

            // 获取订单号
            String orderNumber = String.valueOf(requestData.get("orderNumber"));

            OrderVo orderVo = orderService.selectOrderByOrderNo(orderNumber);

            // 转换为DTO列表
            List<ProductReviewDto> reviewDtos = new ArrayList<>();
            for (Map<String, Object> reviewData : reviewsData) {
                ProductReviewDto dto = new ProductReviewDto();
                dto.setOrderId(orderVo.getOrderId()); // 使用订单号
                dto.setItemId(String.valueOf(reviewData.get("orderItemId")));
                dto.setProductId(String.valueOf(reviewData.get("productId")));
                dto.setContent(String.valueOf(reviewData.get("comment")));
                dto.setRating(Integer.valueOf(String.valueOf(reviewData.get("rating"))));
                String imgUrl = String.valueOf(reviewData.get("imgUrl"));
				if (!imgUrl.isEmpty() && !"null".equals(imgUrl)) {
					dto.setImgUrl(imgUrl);
					// 如果需要设置imgId，可以从其他字段获取
					String imgId = String.valueOf(reviewData.get("imgId"));
					if (!imgId.isEmpty() && !"null".equals(imgId)) {
						dto.setImgId(imgId);
					}
				}
                reviewDtos.add(dto);
            }

            // 批量提交评价
            boolean success = productReviewService.submitBatchReview(reviewDtos, authorId);

            if (success) {
                Map<String, Object> result = new HashMap<>();
                result.put("success", true);
                result.put("message", "评价提交成功");

                log.info("提交商品评价成功，用户ID: {}", authorId);
                return ResultBean.success(result);
            } else {
                throw new ApiException("评价提交失败");
            }

        } catch (ApiException e) {
            log.error("提交商品评价失败: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("提交商品评价异常", e);
            throw new ApiException("评价提交失败");
        }
    }

    /**
     * 获取订单评价列表
     * @param request HTTP请求对象
     * @param orderNumber 订单号
     * @return 评价列表
     */
    @ApiOperation(value = "获取订单评价列表")
    @OperLog(operModule = "商品评价", operType = OperType.QUERY, operDesc = "获取订单评价列表")
    @GetMapping("/api/auth/order/review/list")
    public ResultBean<List<ProductReviewVo>> getOrderReviews(
            HttpServletRequest request,
            @RequestParam String orderNumber) {
        try {
            // 从请求头获取用户ID
            String authorId = String.valueOf(request.getAttribute("authorId"));

            log.info("获取订单评价列表，用户ID: {}，订单号: {}", authorId, orderNumber);

            // 根据订单号获取订单ID
            com.dd.admin.business.order.vo.OrderVo orderVo = orderService.selectOrderByOrderNo(orderNumber);
            if (orderVo == null) {
                throw new ApiException("订单不存在");
            }

            // 转换为Order实体
            Order order = new Order();
            BeanUtil.copyProperties(orderVo, order);

            // 检查权限
            if (!authorId.equals(order.getAuthorId())) {
                throw new ApiException("无权限查看该订单评价");
            }

            // 获取订单评价列表
            List<ProductReviewVo> reviews = productReviewService.getReviewsByOrderId(order.getOrderId());

            log.info("获取订单评价列表成功，用户ID: {}，订单号: {}，评价数量: {}", authorId, orderNumber, reviews.size());
            return ResultBean.success(reviews);

        } catch (ApiException e) {
            log.error("获取订单评价列表失败: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("获取订单评价列表异常", e);
            throw new ApiException("获取订单评价列表失败");
        }
    }

    /**
     * 获取评价详情
     * @param request HTTP请求对象
     * @param orderNumber 订单号
     * @return 评价详情
     */
    @ApiOperation(value = "获取评价详情")
    @OperLog(operModule = "商品评价", operType = OperType.QUERY, operDesc = "获取评价详情")
    @GetMapping("/api/auth/product/getReviewDetail")
    public ResultBean<List<Map<String, Object>>> getReviewDetail(
            HttpServletRequest request,
            @RequestParam String orderNumber) {
        try {
            // 从请求头获取用户ID
            String authorId = String.valueOf(request.getAttribute("authorId"));

            log.info("获取评价详情，用户ID: {}，订单号: {}", authorId, orderNumber);

            // 根据订单号获取订单信息
            com.dd.admin.business.order.vo.OrderVo orderVo = orderService.selectOrderByOrderNo(orderNumber);
            if (orderVo == null) {
                throw new ApiException("订单不存在");
            }

            // 检查权限
            if (!authorId.equals(orderVo.getAuthorId())) {
                throw new ApiException("无权限查看该订单评价");
            }

            // 获取该订单的评价列表
            List<ProductReviewVo> reviews = productReviewService.getReviewsByOrderId(orderVo.getOrderId());

            // 转换为前端需要的格式
            List<Map<String, Object>> result = reviews.stream().map(review -> {
                Map<String, Object> reviewMap = new HashMap<>();
                reviewMap.put("productId", review.getProductId());
                reviewMap.put("productName", review.getProductName());
                reviewMap.put("productImage", review.getProductImage());
                reviewMap.put("rating", review.getRating());
                reviewMap.put("content", review.getContent());
                reviewMap.put("imgUrl", review.getImgUrl());
                reviewMap.put("imgId", review.getImgId());
                reviewMap.put("createTime", review.getCreateTime());
                return reviewMap;
            }).collect(Collectors.toList());

            log.info("获取评价详情成功，用户ID: {}，订单号: {}，评价数量: {}", authorId, orderNumber, result.size());
            return ResultBean.success(result);

        } catch (ApiException e) {
            log.error("获取评价详情失败: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("获取评价详情异常", e);
            throw new ApiException("获取评价详情失败");
        }
    }

    /**
     * 获取商品评论列表
     * @param productId 商品ID
     * @return 评论列表
     */
    @ApiOperation(value = "获取商品评论列表")
    @OperLog(operModule = "商品评价", operType = OperType.QUERY, operDesc = "获取商品评论列表")
    @GetMapping("/api/auth/product/{productId}/reviews")
    public ResultBean<List<ProductReviewVo>> getProductReviews(
            @ApiParam(value = "商品ID", required = true) @PathVariable String productId) {
        try {
            log.info("获取商品评论列表，商品ID: {}", productId);

            // 获取商品评论列表
            List<ProductReviewVo> reviews = productReviewService.getReviewsByProductId(productId);

            log.info("获取商品评论列表成功，商品ID: {}，评论数量: {}", productId, reviews.size());
            return ResultBean.success(reviews);

        } catch (Exception e) {
            log.error("获取商品评论列表异常", e);
            return ResultBean.error("获取商品评论列表失败");
        }
    }
}