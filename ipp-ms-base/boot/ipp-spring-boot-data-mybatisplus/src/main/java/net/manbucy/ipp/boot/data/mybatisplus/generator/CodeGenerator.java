package net.manbucy.ipp.boot.data.mybatisplus.generator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ManBu
 * @date 2021/8/15 11:06
 */
@Slf4j
@Builder
public class CodeGenerator {
    private DataSourceConfig dataSourceConfig;
    @Builder.Default
    private String author = "CodeGenerator";
    private String parentPackage;
    private String moduleName;
    @Builder.Default
    private String tablePrefix = "ipp";
    private String[] tableNames;
    private boolean fileOverride;

    public void generate() {
        log.info("generator start");
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setServiceName("%sService");
        gc.setFileOverride(fileOverride);

        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor(System.getProperty("user.name", author));
        gc.setOpen(false);
        // 实体属性 Swagger2 注解
        gc.setSwagger2(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        if (dataSourceConfig == null) {
            throw new MybatisPlusException("dataSourceConfig can not be null");
        }
        mpg.setDataSource(dataSourceConfig);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(parentPackage);
        pc.setEntity(getFullNameOfModule("pojo.entity"));
        pc.setService(getFullNameOfModule("service"));
        pc.setServiceImpl(pc.getService() + ".impl");
        pc.setMapper(getFullNameOfModule("mapper"));
        pc.setController(getFullNameOfModule("controller"));
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        String templatePath = "/templates/mapper.xml.ftl";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + moduleName
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);


        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        templateConfig.setController(null);
        templateConfig.setEntity("/templates/ippEntity.java");
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass("net.manbucy.ipp.boot.data.mybatisplus.entity.BaseEntity");
        strategy.setEntityLombokModel(true);

        strategy.setEntityTableFieldAnnotationEnable(true);
        strategy.setTablePrefix(tablePrefix + "_");
        strategy.setSuperEntityColumns("id", "create_user", "create_time", "update_user", "update_time", "version", "is_deleted");
        strategy.setInclude(tableNames);
        strategy.setLogicDeleteFieldName("is_deleted");
        strategy.setVersionFieldName("version");
        strategy.setEntityBooleanColumnRemoveIsPrefix(true);

        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();

    }

    private String getFullNameOfModule(String name) {
        if (StringUtils.isNotBlank(moduleName)) {
            return name + StringPool.DOT + moduleName;
        }
        return name;
    }

}
