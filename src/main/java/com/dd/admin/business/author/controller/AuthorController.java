package com.dd.admin.business.author.controller;

import cn.hutool.core.bean.BeanUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.dd.admin.common.model.UpdateGroup;
import com.dd.admin.common.model.result.ResultBean;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotBlank;
import com.dd.admin.business.author.entity.Author;
import com.dd.admin.business.author.domain.AuthorVo;
import com.dd.admin.business.author.domain.AuthorDto;
import com.dd.admin.business.author.domain.AuthorAdminAddDto;
import com.dd.admin.business.author.domain.AuthorBalanceUpdateDto;
import com.dd.admin.business.author.service.AuthorService;
import com.dd.admin.common.util.MinioUrlUtil;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 作者（博主） 前端控制器
 * </p>
 *
 * @author 727869402@qq.com
 * @since 2024-12-12
 */
@Api(tags = "作者（博主）")
@RestController
public class AuthorController {

    @Autowired
    AuthorService authorService;
    
    @Autowired
    private MinioUrlUtil minioUrlUtil;

    @ApiOperation(value = "作者（博主）-分页列表")
    @ApiOperationSupport(order = 1)
    @GetMapping("/admin/author/page")
    public ResultBean<IPage<AuthorVo>> page(AuthorDto authorDto) {
        IPage<AuthorVo> pageInfo = authorService.selectAuthorPage(authorDto);
        return ResultBean.success(pageInfo);
    }

    @ApiOperation(value = "作者（博主）-列表")
    @ApiOperationSupport(order = 2)
    @GetMapping("/admin/author/list")
    public ResultBean<IPage<AuthorVo>> list(AuthorDto authorDto) {
        IPage<AuthorVo> list = authorService.selectAuthorList(authorDto);
        return ResultBean.success(list);
    }

    @ApiOperation(value = "作者（博主）-添加")
    @ApiOperationSupport(order = 3)
    @PostMapping("/admin/author/add")
    public ResultBean<Author> add(@RequestBody @Validated AuthorAdminAddDto authorDto) {
        Author author = authorService.createAuthorByAdmin(authorDto);
        return ResultBean.success(author);
    }

    @ApiOperation(value = "作者（博主）-查询")
    @ApiOperationSupport(order = 4)
    @GetMapping("/admin/author/{authorId}")
    public ResultBean<AuthorVo> get(@PathVariable @NotBlank String authorId) {
        Author author = authorService.getById(authorId);
        AuthorVo authorVo = BeanUtil.copyProperties(author,AuthorVo.class);
        // 刷新MinIO URL
        authorVo.setAvatarUrl(minioUrlUtil.refreshMinioUrl(authorVo.getAvatarUrl()));
        authorVo.setBackGroundUrl(minioUrlUtil.refreshMinioUrl(authorVo.getBackGroundUrl()));
        return ResultBean.success(authorVo);
    }

    @ApiOperation(value = "作者（博主）-修改")
    @ApiOperationSupport(order = 5)
    @PostMapping("/admin/author/update")
    public ResultBean<Author> update(@RequestBody @Validated(UpdateGroup.class) AuthorDto authorDto) {
        Author author = BeanUtil.copyProperties(authorDto, Author.class);
        authorService.updateById(author);
        return ResultBean.success(author);
    }

    @ApiOperation(value = "作者（博主）-删除")
    @ApiOperationSupport(order = 6)
    @GetMapping("/admin/author/delete/{authorId}")
    public ResultBean<Author> delete(@PathVariable @NotBlank String authorId) {
        boolean result = authorService.removeById(authorId);
        return ResultBean.success(result);
    }

    @ApiOperation(value = "作者（博主）-余额修改")
    @ApiOperationSupport(order = 7)
    @PostMapping("/admin/author/updateBalance")
    public ResultBean<Boolean> updateBalance(@RequestBody @Validated AuthorBalanceUpdateDto dto) {
        boolean result = authorService.updateAuthorBalance(dto.getAuthorId(), dto.getOperationType(), dto.getAmount(), dto.getRemark());
        return ResultBean.success(result);
    }

    @ApiOperation(value = "作者（博主）-余额变动记录")
    @ApiOperationSupport(order = 8)
    @GetMapping("/admin/author/balanceLog")
    public ResultBean<List<com.dd.admin.business.balance.entity.BalanceLog>> getBalanceLog(@RequestParam @NotBlank String authorId) {
        List<com.dd.admin.business.balance.entity.BalanceLog> result = authorService.getAuthorBalanceLog(authorId);
        return ResultBean.success(result);
    }
}
