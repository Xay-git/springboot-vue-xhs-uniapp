package com.dd.admin.business.author.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.common.exception.ApiException;
import com.dd.admin.common.model.PageFactory;
import com.dd.admin.business.author.entity.Author;
import com.dd.admin.business.author.mapper.AuthorMapper;
import com.dd.admin.business.author.service.AuthorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dd.admin.common.service.MinioService;
import com.dd.admin.common.utils.AddressUtils;
import com.dd.admin.common.utils.IPUtils;
import com.dd.admin.common.utils.RandomXiaohongshuAuthorName;
import com.dd.admin.common.utils.StringUtil;
import com.dd.admin.business.author.domain.AuthorAdminAddDto;
import com.dd.admin.business.file.service.FileService;
import com.dd.admin.common.util.MinioUrlUtil;
import com.dd.admin.business.balance.service.BalanceLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dd.admin.business.author.domain.AuthorVo;
import com.dd.admin.business.author.domain.AuthorDto;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 作者（博主） 服务实现类
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-12
 */
@Service
public class AuthorServiceImpl extends ServiceImpl<AuthorMapper, Author> implements AuthorService {

    @Autowired
    HttpServletRequest request;
    
    @Autowired
    private FileService fileService;

    @Autowired
    MinioService minioService;
    
    @Autowired
    private MinioUrlUtil minioUrlUtil;
    
    @Autowired
    private BalanceLogService balanceLogService;
    
    @Value("${server.port}")
    private String port;

    @Override
    public IPage<AuthorVo> selectAuthorPage(AuthorDto authorDto) {
        Page page = PageFactory.defaultPage();
        IPage<AuthorVo> result = baseMapper.selectAuthorPage(page,authorDto);
        
        // 刷新MinIO URL
        if (result != null && result.getRecords() != null) {
            result.getRecords().forEach(this::refreshAuthorUrls);
        }
        
        return result;
    }

    @Override
    public IPage<AuthorVo> selectAuthorList(AuthorDto authorDto) {
        Page page = PageFactory.defaultPage();
        IPage<AuthorVo> result = baseMapper.selectAuthorList(page,authorDto);
        
        // 刷新MinIO URL
        if (result != null && result.getRecords() != null) {
            result.getRecords().forEach(this::refreshAuthorUrls);
        }
        
        return result;
    }
    
    /**
     * 刷新作者的MinIO URL
     */
    private void refreshAuthorUrls(AuthorVo author) {
        if (author == null) return;
        
        // 刷新头像URL
        author.setAvatarUrl(minioUrlUtil.refreshMinioUrl(author.getAvatarUrl()));
        
        // 刷新背景图URL
        author.setBackGroundUrl(minioUrlUtil.refreshMinioUrl(author.getBackGroundUrl()));
    }

    @Override
    public Author selectAuthorByPhoneNumber(String phoneNumber) {
        return baseMapper.selectAuthorByPhoneNumber(phoneNumber);
    }

    @Override
    public Author createNewAuthor(String phoneNumber) {
        Author author = new Author();
        author.setPhoneNumber(phoneNumber);
        //随机生成昵称
        author.setAuthorName(RandomXiaohongshuAuthorName.generateAuthorName());
        author.setAuthorNo(StringUtil.getNumberForPK() + StringUtil.createCode(2));
        author.setDescription("我是" + author.getAuthorName());
        //生成随机头像因为没有点击上传所有没有头像id
        author.setAvatarUrl("http://8.146.211.120:8080/upload/avatar/avatar ("+ StringUtil.createCode(1) +").jpg");
        author.setBackGroundUrl("http://8.146.211.120:8080/upload/notes/note (6).jpg");
        author.setIpAddress(IPUtils.getIpAddr(request)); // 请求IP
        author.setIpRealAddress(AddressUtils.getRealAddress(author.getIpAddress())); //ip真实地址
        author.setBalance(BigDecimal.ZERO);
        this.save(author);
        return author;
    }

    @Override
    public Author createAuthorByAdmin(AuthorAdminAddDto authorDto) {
        if (selectAuthorByPhoneNumber(authorDto.getPhoneNumber()) != null) {
            throw new ApiException("该手机号已被注册");
        }
        // 检查昵称是否重复
        if (StringUtil.isNotEmpty(authorDto.getAuthorName())) {
            LambdaQueryWrapper<Author> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Author::getAuthorName, authorDto.getAuthorName());
            if (this.getOne(queryWrapper) != null) {
                throw new ApiException("该昵称已被使用");
            }
        }

        Author author = new Author();
        author.setPhoneNumber(authorDto.getPhoneNumber());

        author.setAuthorName(StringUtil.isNotEmpty(authorDto.getAuthorName()) ? authorDto.getAuthorName() : RandomXiaohongshuAuthorName.generateAuthorName());
        author.setDescription(StringUtil.isNotEmpty(authorDto.getDescription()) ? authorDto.getDescription() : "我是" + author.getAuthorName());
        author.setAuthorNo(StringUtil.getNumberForPK() + StringUtil.createCode(2));

        if (StringUtil.isNotEmpty(authorDto.getAvatarUrl())) {
            author.setAvatarUrl(authorDto.getAvatarUrl());
            author.setBackGroundUrl(authorDto.getAvatarUrl());
        } else {
            author.setAvatarUrl("http://8.146.211.120:8080/upload/avatar/avatar ("+ StringUtil.createCode(1) +").jpg");
            author.setBackGroundUrl("http://8.146.211.120:8080/upload/notes/note (6).jpg");
        }
        author.setIpAddress(IPUtils.getIpAddr(request)); // 请求IP
        author.setIpRealAddress(AddressUtils.getRealAddress(author.getIpAddress())); //ip真实地址
        this.save(author);
        return author;
    }

    @Override
    public Long selectAuthorUpAndStarTotalCount(String authorId) {
        return this.baseMapper.selectAuthorUpAndStarTotalCount(authorId);
    }

    @Override
    public boolean deductBalance(String authorId, BigDecimal amount) {
        try {
            // 获取用户信息
            Author author = this.getById(authorId);
            if (author == null) {
                throw new ApiException("用户不存在");
            }
            
            // 检查余额是否足够
            BigDecimal currentBalance = author.getBalance() != null ? author.getBalance() : BigDecimal.ZERO;
            if (currentBalance.compareTo(amount) < 0) {
                throw new ApiException("余额不足，当前余额：" + currentBalance + "，需要扣除：" + amount);
            }
            
            // 扣除余额
            BigDecimal newBalance = currentBalance.subtract(amount);
            author.setBalance(newBalance);
            
            // 更新数据库
            return this.updateById(author);
        } catch (Exception e) {
            throw new ApiException("扣除余额失败：" + e.getMessage());
        }
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateAuthorBalance(String authorId, Integer operationType, BigDecimal amount, String remark) {
        try {
            // 获取用户信息
            Author author = this.getById(authorId);
            if (author == null) {
                throw new ApiException("用户不存在");
            }
            
            BigDecimal currentBalance = author.getBalance() != null ? author.getBalance() : BigDecimal.ZERO;
            BigDecimal newBalance;
            
            // 根据操作类型计算新余额
            if (operationType == 3) { // 管理员增加
                newBalance = currentBalance.add(amount);
            } else if (operationType == 4) { // 管理员减少
                // 检查余额是否足够
                if (currentBalance.compareTo(amount) < 0) {
                    throw new ApiException("余额不足，当前余额：" + currentBalance + "，需要减少：" + amount);
                }
                newBalance = currentBalance.subtract(amount);
            } else {
                throw new ApiException("无效的操作类型：" + operationType);
            }
            
            // 更新用户余额
            author.setBalance(newBalance);
            boolean updateResult = this.updateById(author);
            
            if (updateResult) {
                // 记录余额变动日志
                com.dd.admin.business.balance.entity.BalanceLog balanceLog = new com.dd.admin.business.balance.entity.BalanceLog();
                balanceLog.setAuthorId(authorId);
                balanceLog.setAmount(amount);
                balanceLog.setBeforeBalance(currentBalance);
                balanceLog.setAfterBalance(newBalance);
                balanceLog.setType(operationType);
                balanceLog.setRemark(remark);
                balanceLog.setOperatorId("admin"); // 这里可以从当前登录用户获取
                balanceLog.setOperatorName("管理员"); // 这里可以从当前登录用户获取
                
                balanceLogService.save(balanceLog);
            }
            
            return updateResult;
        } catch (Exception e) {
            throw new ApiException("修改余额失败：" + e.getMessage());
        }
    }
    
    @Override
    public List<com.dd.admin.business.balance.entity.BalanceLog> getAuthorBalanceLog(String authorId) {
        try {
            com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<com.dd.admin.business.balance.entity.BalanceLog> queryWrapper = 
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
            queryWrapper.eq("author_id", authorId);
            queryWrapper.orderByDesc("create_time");
            return balanceLogService.list(queryWrapper);
        } catch (Exception e) {
            throw new ApiException("查询余额变动记录失败：" + e.getMessage());
        }
    }
}
