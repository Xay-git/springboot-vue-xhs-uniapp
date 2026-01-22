package com.dd.admin.business.balance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.business.balance.dto.BalanceLogDto;
import com.dd.admin.business.balance.dto.BalanceLogAddDto;
import com.dd.admin.business.balance.vo.BalanceLogVo;
import com.dd.admin.business.balance.entity.BalanceLog;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import java.math.BigDecimal;

import java.util.List;

/**
 * 余额日志服务接口
 *
 * @author 727869402@qq.com
 * @since 2025-09-11
 */
public interface BalanceLogService extends IService<BalanceLog> {

    /**
     * 分页查询余额日志
     *
     * @param balanceLogDto 查询条件
     * @return 分页结果
     */
    IPage<BalanceLogVo> selectBalanceLogPage(BalanceLogDto balanceLogDto);

    /**
     * 查询余额日志列表
     *
     * @param balanceLogDto 查询条件
     * @return 列表结果
     */
    List<BalanceLogVo> selectBalanceLogList(BalanceLogDto balanceLogDto);

    /**
     * 根据ID查询余额日志详情
     *
     * @param balanceLogId 余额日志ID
     * @return 余额日志详情
     */
    BalanceLogVo selectBalanceLogById(String balanceLogId);

    /**
     * 新增余额日志
     *
     * @param balanceLogAddDto 新增数据
     */
    void addBalanceLog(BalanceLogAddDto balanceLogAddDto);

    /**
     * 修改余额日志
     *
     * @param balanceLogDto 修改数据
     */
    void updateBalanceLog(BalanceLogDto balanceLogDto);

    /**
     * 删除余额日志
     *
     * @param balanceLogId 余额日志ID
     */
    void deleteBalanceLog(String balanceLogId);
    
    /**
     * 分页查询余额变动记录
     * @param page 分页参数
     * @param balanceLog 查询条件
     * @return 分页结果
     */
    Page<BalanceLog> getBalanceLogPage(Page<BalanceLog> page, BalanceLog balanceLog);
    
    /**
     * 添加余额变动记录(消费)
     * @param authorId 用户ID
     * @param amount 变动金额
     * @param relatedId 关联ID
     * @param remark 备注
     * @return 是否成功
     */
    boolean addConsumptionLog(String authorId, BigDecimal amount, String relatedId, String remark);
    
    /**
     * 添加余额变动记录(退款)
     * @param authorId 用户ID
     * @param amount 变动金额
     * @param relatedId 关联ID
     * @param remark 备注
     * @return 是否成功
     */
    boolean addRefundLog(String authorId, BigDecimal amount, String relatedId, String remark);
    
    /**
     * 添加余额变动记录(管理员修改)
     * @param authorId 用户ID
     * @param amount 变动金额
     * @param operatorId 操作人ID
     * @param operatorName 操作人姓名
     * @param remark 备注
     * @return 是否成功
     */
    boolean addAdminModifyLog(String authorId, BigDecimal amount, String operatorId, String operatorName, String remark);
}