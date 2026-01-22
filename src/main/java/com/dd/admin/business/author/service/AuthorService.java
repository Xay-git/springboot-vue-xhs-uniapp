package com.dd.admin.business.author.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.business.author.entity.Author;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.admin.business.author.domain.AuthorVo;
import com.dd.admin.business.author.domain.AuthorDto;
import com.dd.admin.business.author.domain.AuthorAdminAddDto;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 作者（博主） 服务类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-12
 */
public interface AuthorService extends IService<Author> {

    //作者（博主）-分页列表
    IPage<AuthorVo> selectAuthorPage(AuthorDto authorDto);

    //作者（博主）-列表
    IPage<AuthorVo> selectAuthorList(AuthorDto authorDto);

    Author selectAuthorByPhoneNumber(String phoneNumber);

    Author createNewAuthor(String phoneNumber);

    Author createAuthorByAdmin(AuthorAdminAddDto authorDto);

    Long selectAuthorUpAndStarTotalCount(String authorId);

    /**
     * 扣除用户余额
     * @param authorId 用户ID
     * @param amount 扣除金额
     * @return 是否成功
     */
    boolean deductBalance(String authorId, BigDecimal amount);

    /**
     * 管理员修改用户余额
     * @param authorId 用户ID
     * @param operationType 操作类型 3-管理员增加 4-管理员减少
     * @param amount 金额
     * @param remark 备注
     * @return 是否成功
     */
    boolean updateAuthorBalance(String authorId, Integer operationType, BigDecimal amount, String remark);

    /**
     * 查询作者余额变动记录
     * @param authorId 作者ID
     * @return 余额变动记录列表
     */
    List<com.dd.admin.business.balance.entity.BalanceLog> getAuthorBalanceLog(String authorId);

}
