package ${package.Controller};

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
import ${package.Entity}.${entity};
import ${cfg.voPath}.${entity}Vo;
import ${cfg.dtoPath}.${entity}Dto;
import ${package.Service}.${table.serviceName};

import java.util.List;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Api(tags = "${table.comment!}")
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    @Autowired
    ${table.serviceName} ${package.ModuleName}Service;

    @ApiOperation(value = "${table.comment!}-分页列表")
    @ApiOperationSupport(order = 1)
    @GetMapping("/admin/${package.ModuleName}/page")
    public ResultBean<IPage<${entity}Vo>> page(${entity}Dto ${package.ModuleName}Dto) {
        IPage<${entity}Vo> pageInfo = ${package.ModuleName}Service.select${entity}Page(${package.ModuleName}Dto);
        return ResultBean.success(pageInfo);
    }

    @ApiOperation(value = "${table.comment!}-列表")
    @ApiOperationSupport(order = 2)
    @GetMapping("/admin/${package.ModuleName}/list")
    public ResultBean<List<${entity}Vo>> list(${entity}Dto ${package.ModuleName}Dto) {
        List<${entity}Vo> list = ${package.ModuleName}Service.select${entity}List(${package.ModuleName}Dto);
        return ResultBean.success(list);
    }

    @ApiOperation(value = "${table.comment!}-添加")
    @ApiOperationSupport(order = 3)
    @PostMapping("/admin/${package.ModuleName}/add")
    public ResultBean<${entity}> add(@RequestBody @Validated ${entity}Dto ${package.ModuleName}Dto) {
        ${entity} ${package.ModuleName} = BeanUtil.copyProperties(${package.ModuleName}Dto, ${entity}.class);
        ${package.ModuleName}Service.save(${package.ModuleName});
        return ResultBean.success(${package.ModuleName});
    }

    @ApiOperation(value = "${table.comment!}-查询")
    @ApiOperationSupport(order = 4)
    @GetMapping("/admin/${package.ModuleName}/{${package.ModuleName}Id}")
    public ResultBean<${entity}Vo> get(@PathVariable @NotBlank String ${package.ModuleName}Id) {
        ${entity} ${package.ModuleName} = ${package.ModuleName}Service.getById(${package.ModuleName}Id);
        ${entity}Vo ${package.ModuleName}Vo = BeanUtil.copyProperties(${package.ModuleName},${entity}Vo.class);
        return ResultBean.success(${package.ModuleName}Vo);
    }

    @ApiOperation(value = "${table.comment!}-修改")
    @ApiOperationSupport(order = 5)
    @PostMapping("/admin/${package.ModuleName}/update")
    public ResultBean<${entity}> update(@RequestBody @Validated(UpdateGroup.class) ${entity}Dto ${package.ModuleName}Dto) {
        ${entity} ${package.ModuleName} = BeanUtil.copyProperties(${package.ModuleName}Dto, ${entity}.class);
        ${package.ModuleName}Service.updateById(${package.ModuleName});
        return ResultBean.success(${package.ModuleName});
    }

    @ApiOperation(value = "${table.comment!}-删除")
    @ApiOperationSupport(order = 6)
    @GetMapping("/admin/${package.ModuleName}/delete/{${package.ModuleName}Id}")
    public ResultBean<${entity}> delete(@PathVariable @NotBlank String ${package.ModuleName}Id) {
        Boolean b = ${package.ModuleName}Service.removeById(${package.ModuleName}Id);
        return ResultBean.success(b);
    }
}
</#if>
