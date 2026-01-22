package ${package.Mapper};

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${package.Entity}.${entity};
import ${superMapperClassPackage};
import ${cfg.voPath}.${entity}Vo;
import ${cfg.dtoPath}.${entity}Dto;

import java.util.List;

/**
 * <p>
 * ${table.comment!} Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
@Mapper
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

    IPage<${entity}Vo> select${entity}Page(Page<${entity}Vo> page, @Param("${package.ModuleName}Dto") ${entity}Dto ${package.ModuleName}Dto);

    List<${entity}Vo> select${entity}List(@Param("${package.ModuleName}Dto") ${entity}Dto ${package.ModuleName}Dto);
}
</#if>
