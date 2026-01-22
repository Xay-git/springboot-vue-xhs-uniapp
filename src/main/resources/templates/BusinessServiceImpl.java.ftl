package ${package.ServiceImpl};

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.common.model.PageFactory;
import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;
import ${cfg.voPath}.${entity}Vo;
import ${cfg.dtoPath}.${entity}Dto;
import java.util.List;

/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    @Override
    public IPage<${entity}Vo> select${entity}Page(${entity}Dto ${package.ModuleName}Dto) {
        Page page = PageFactory.defaultPage();
        return baseMapper.select${entity}Page(page,${package.ModuleName}Dto);
    }

    @Override
    public List<${entity}Vo> select${entity}List(${entity}Dto ${package.ModuleName}Dto) {
        return baseMapper.select${entity}List(${package.ModuleName}Dto);
    }
}
</#if>
