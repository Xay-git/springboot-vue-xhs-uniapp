package com.dd.admin.business.balance.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dd.admin.business.balance.entity.BalanceLog;
import com.dd.admin.business.balance.mapper.BalanceLogMapper;
import com.dd.admin.business.balance.service.BalanceLogService;
import com.dd.admin.business.balance.dto.BalanceLogDto;
import com.dd.admin.business.balance.dto.BalanceLogAddDto;
import com.dd.admin.business.balance.vo.BalanceLogVo;
import com.dd.admin.common.exception.ApiException;
import com.dd.admin.common.model.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 * 余额变动记录 服务实现类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-09-11
 */
@Service
public class BalanceLogServiceImpl extends ServiceImpl<BalanceLogMapper, BalanceLog> implements BalanceLogService {

    @Autowired
    private BalanceLogMapper balanceLogMapper;
    
    @Autowired
    private com.dd.admin.business.author.service.AuthorService authorService;
    
    @Override
    public IPage<BalanceLogVo> selectBalanceLogPage(BalanceLogDto balanceLogDto) {
        IPage<BalanceLogVo> page = PageFactory.defaultPage();
        return balanceLogMapper.selectBalanceLogPage(page, balanceLogDto);
    }

    @Override
    public List<BalanceLogVo> selectBalanceLogList(BalanceLogDto balanceLogDto) {
        return balanceLogMapper.selectBalanceLogList(balanceLogDto);
    }

    @Override
    public BalanceLogVo selectBalanceLogById(String balanceLogId) {
        BalanceLog balanceLog = balanceLogMapper.selectById(balanceLogId);
        if (balanceLog == null) {
            throw new ApiException("余额日志不存在！");
        }
        BalanceLogVo balanceLogVo = new BalanceLogVo();
        BeanUtil.copyProperties(balanceLog, balanceLogVo);
        return balanceLogVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addBalanceLog(BalanceLogAddDto balanceLogAddDto) {
        BalanceLog balanceLog = new BalanceLog();
        BeanUtil.copyProperties(balanceLogAddDto, balanceLog);

        int result = balanceLogMapper.insert(balanceLog);
        if (result <= 0) {
            throw new ApiException("新增余额日志失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBalanceLog(BalanceLogDto balanceLogDto) {
        BalanceLog balanceLog = new BalanceLog();
        BeanUtil.copyProperties(balanceLogDto, balanceLog);
        
        int result = balanceLogMapper.updateById(balanceLog);
        if (result <= 0) {
            throw new ApiException("修改余额日志失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBalanceLog(String balanceLogId) {
        int result = balanceLogMapper.deleteById(balanceLogId);
        if (result <= 0) {
            throw new ApiException("删除余额日志失败");
        }
    }
    
    @Override
    public Page<BalanceLog> getBalanceLogPage(Page<BalanceLog> page, BalanceLog balanceLog) {
        return balanceLogMapper.getBalanceLogPage(page, balanceLog);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addConsumptionLog(String authorId, BigDecimal amount, String relatedId, String remark) {
        try {
            // 注意：此方法应该在扣款前调用，传入扣款前的余额信息
            // 如果在扣款后调用，需要重新计算beforeBalance
            com.dd.admin.business.author.entity.Author author = authorService.getById(authorId);
            if (author == null) {
                throw new ApiException("用户不存在");
            }
            
            // 当前余额是扣款后的余额，需要加回扣款金额得到扣款前余额
            BigDecimal afterBalance = author.getBalance() != null ? author.getBalance() : BigDecimal.ZERO;
            BigDecimal beforeBalance = afterBalance.add(amount);
            
            BalanceLog balanceLog = new BalanceLog();
            balanceLog.setAuthorId(authorId);
            balanceLog.setAmount(amount);
            balanceLog.setBeforeBalance(beforeBalance);
            balanceLog.setAfterBalance(afterBalance);
            balanceLog.setType(1); // 消费类型
            balanceLog.setRelatedId(relatedId);
            balanceLog.setRemark(remark);
            
            return balanceLogMapper.insert(balanceLog) > 0;
        } catch (Exception e) {
            throw new ApiException("添加消费日志失败：" + e.getMessage());
        }
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addRefundLog(String authorId, BigDecimal amount, String relatedId, String remark) {
        BalanceLog balanceLog = new BalanceLog();
        // 余额日志ID由框架自动生成，无需手动设置
        balanceLog.setAuthorId(authorId);
        balanceLog.setAmount(amount);
        balanceLog.setType(2);
        balanceLog.setRelatedId(relatedId);
        balanceLog.setRemark(remark);
        return balanceLogMapper.insert(balanceLog) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addAdminModifyLog(String authorId, BigDecimal amount, String operatorId, String operatorName, String remark) {
        BalanceLog balanceLog = new BalanceLog();
        // 余额日志ID由框架自动生成，无需手动设置
        balanceLog.setAuthorId(authorId);
        balanceLog.setAmount(amount);
        balanceLog.setType(3);
        balanceLog.setOperatorId(operatorId);
        balanceLog.setOperatorName(operatorName);
        balanceLog.setRemark(remark);
        return balanceLogMapper.insert(balanceLog) > 0;
    }
}




