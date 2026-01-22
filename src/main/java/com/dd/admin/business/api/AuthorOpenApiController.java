package com.dd.admin.business.api;

import com.dd.admin.business.author.domain.AuthorAdminAddDto;
import com.dd.admin.business.author.entity.Author;
import com.dd.admin.business.author.service.AuthorService;
import com.dd.admin.common.aop.operationLog.aop.OperLog;
import com.dd.admin.common.aop.operationLog.aop.OperType;
import com.dd.admin.common.model.result.ResultBean;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "开放接口-博主")
@ApiSupport(order = 99)
@RestController
public class AuthorOpenApiController {

    @Autowired
    private AuthorService authorService;

    @ApiOperation(value = "创建博主(无需权限)")
    @ApiOperationSupport(order = 1)
    @OperLog(operModule = "博主管理", operType = OperType.ADD, operDesc = "创建博主(无需权限)")
    @PostMapping("/api/open/author/create")
    public ResultBean<Author> createAuthor(@RequestBody @Validated AuthorAdminAddDto authorDto) {
        Author author = authorService.createAuthorByAdmin(authorDto);
        return ResultBean.success(author);
    }
}