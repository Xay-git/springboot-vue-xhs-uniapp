package ${package.Service};

import com.baomidou.mybatisplus.core.metadata.IPage;
import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import ${cfg.voPath}.${entity}Vo;
import ${cfg.dtoPath}.${entity}Dto;
import java.util.List;

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

    //${table.comment!}-分页列表
    IPage<${entity}Vo> select${entity}Page(${entity}Dto ${package.ModuleName}Dto);

    //${table.comment!}-列表
    List<${entity}Vo> select${entity}List(${entity}Dto ${package.ModuleName}Dto);

}
</#if>
