package com.dd.admin.business.balance.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.business.balance.entity.BalanceLog;
import com.dd.admin.business.balance.dto.BalanceLogDto;
import com.dd.admin.business.balance.vo.BalanceLogVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 * 余额变动记录 Mapper 接口
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2025-09-11
 */
@Mapper
public interface BalanceLogMapper extends BaseMapper<BalanceLog> {

    /**
     * 分页查询余额日志
     *
     * @param page 分页参数
     * @param balanceLogDto 查询条件
     * @return 分页结果
     */
    IPage<BalanceLogVo> selectBalanceLogPage(IPage<BalanceLogVo> page, @Param("dto") BalanceLogDto balanceLogDto);

    /**
     * 查询余额日志列表
     *
     * @param balanceLogDto 查询条件
     * @return 列表结果
     */
    List<BalanceLogVo> selectBalanceLogList(@Param("dto") BalanceLogDto balanceLogDto);

    /**
     * 分页查询余额日志
     *
     * @param page 分页参数
     * @param balanceLog 查询条件
     * @return 分页结果
     */
    Page<BalanceLog> getBalanceLogPage(Page<BalanceLog> page, @Param("balanceLog") BalanceLog balanceLog);

}






