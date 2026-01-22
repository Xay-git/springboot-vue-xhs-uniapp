package com.dd.admin;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.*;

/**
 * Description:自动生成 自定义代码
 * <p>
 * date: 2021/7/6
 *
 * @author: wxl
 */

public class BusinessGenerator {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入").append(tip).append("：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }


    public static String PACKAGE_NAME = "com.dd.admin.business";
    public static String FIRST_MODULE = "business";

    /**
     * RUN THIS
     */
    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java/");
        gc.setAuthor("727869402@qq.com");
        gc.setOpen(false);
        gc.setFileOverride(true);
        gc.setSwagger2(true);
        gc.setBaseColumnList(true);
        gc.setBaseResultMap(true);
        gc.setServiceName("%sService");
        gc.setIdType(IdType.ASSIGN_UUID);
        gc.setDateType(DateType.ONLY_DATE);


        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://8.146.211.120:3306/ddxhs?useSSL=false&autoReconnect=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8");
        dsc.setUsername("root");
        dsc.setPassword("wxlwxl12");

        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");

        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(PACKAGE_NAME);
        pc.setModuleName(scanner("模块名"));
        mpg.setPackageInfo(pc);


        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                //传入自定义字符串  在模板中使用 ${cfg.xx} 调用
                // to do nothing
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("firstModule", FIRST_MODULE);
                map.put("packageName", PACKAGE_NAME);
                map.put("voPath", PACKAGE_NAME + "." + pc.getModuleName() + ".domain");
                map.put("dtoPath", PACKAGE_NAME + "." +  pc.getModuleName() + ".domain");
                this.setMap(map);
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();

        focList.add(new FileOutConfig("/templates/BusinessMapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/java/" +
                        "/com/dd/admin/" + FIRST_MODULE + "/" + pc.getModuleName() + "/mapper/xml/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        //生成自定义Vo
        focList.add(new FileOutConfig("/templates/Vo.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/java/\\com\\dd\\admin\\" + FIRST_MODULE + "/" + pc.getModuleName() + "/domain/"
                        + tableInfo.getEntityName() + "Vo.java";
            }
        });
        //生成自定义Dto
        focList.add(new FileOutConfig("/templates/Dto.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/java/\\com\\dd\\admin\\" + FIRST_MODULE + "/" + pc.getModuleName() + "/domain/"
                        + tableInfo.getEntityName() + "Dto.java";
            }
        });


        //生成自定义Js
        focList.add(new FileOutConfig("/templates/Js.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/web/\\src\\api\\" + FIRST_MODULE + "/" + pc.getModuleName() + "/"
                        + pc.getModuleName() + ".js";
            }
        });

        //生成自定义List
        focList.add(new FileOutConfig("/templates/List.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/web/\\src\\views\\" + FIRST_MODULE + "/" + pc.getModuleName() + "/"
                        + pc.getModuleName() + "List.vue";
            }
        });

        //生成自定义add
        focList.add(new FileOutConfig("/templates/addForm.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/web/\\src\\views\\" + FIRST_MODULE + "/" + pc.getModuleName() + "/"
                        + "add" + tableInfo.getEntityName() + ".vue";
            }
        });

        //生成自定义edit
        focList.add(new FileOutConfig("/templates/editForm.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/web/\\src\\views\\" + FIRST_MODULE + "/" + pc.getModuleName() + "/"
                        + "edit" + tableInfo.getEntityName() + ".vue";
            }
        });


        TemplateConfig templateConfig = new TemplateConfig();
        //自定义controller
        templateConfig.setController("/templates/BusinessController.java");
        templateConfig.setService("/templates/BusinessService.java");
        templateConfig.setServiceImpl("/templates/BusinessServiceImpl.java");
        templateConfig.setMapper("/templates/BusinessMapper.java");
        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        List<TableFill> tableFillList = new ArrayList<TableFill>();
        //如 每张表都有一个创建时间、修改时间
        //而且这基本上就是通用的了，新增时，创建时间和修改时间同时修改
        //修改时，修改时间会修改，
        //虽然像Mysql数据库有自动更新几只，但像ORACLE的数据库就没有了，
        //使用公共字段填充功能，就可以实现，自动按场景更新了。
        //如下是配置
        TableFill createName = new TableFill("CREATE_NAME", FieldFill.INSERT);
        TableFill createId = new TableFill("CREATE_ID", FieldFill.INSERT);
        TableFill createTime = new TableFill("CREATE_TIME", FieldFill.INSERT);
        TableFill deptId = new TableFill("DEPT_ID", FieldFill.INSERT);
        TableFill shopId = new TableFill("SHOP_ID", FieldFill.INSERT);
        TableFill deptName = new TableFill("DEPT_NAME", FieldFill.INSERT);
        TableFill shopName = new TableFill("SHOP_NAME", FieldFill.INSERT);
        tableFillList.add(createName);
        tableFillList.add(createId);
        tableFillList.add(createTime);
        tableFillList.add(deptId);
        tableFillList.add(deptName);
        tableFillList.add(shopId);
        tableFillList.add(deptName);
        tableFillList.add(shopName);

        TableFill modifyName = new TableFill("UPDATE_NAME", FieldFill.UPDATE);
        TableFill modifyId = new TableFill("UPDATE_ID", FieldFill.UPDATE);
        TableFill modifyTime = new TableFill("UPDATE_TIME", FieldFill.UPDATE);

        tableFillList.add(modifyName);
        tableFillList.add(modifyId);
        tableFillList.add(modifyTime);


        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setInclude("business_" + scanner("输入系统表名business_{}"));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix("business_");
        strategy.setRestControllerStyle(true);
        strategy.setTableFillList(tableFillList);
        //配置逻辑删除字段
        strategy.setLogicDeleteFieldName("DELETED");
        //配置乐观锁字段
        strategy.setVersionFieldName("VERSION");
        mpg.setStrategy(strategy);
        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}
