package com.grj.eurekauser;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @Description mybatis-plus代码生成器,执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
 * @Author guorenjie
 * @Date 2019/9/18 21:49
 **/

public class CodeGenerator {
    // 全局配置-开发人员
    private static String author = "guorenjie";
    //数据源配置
    private static String dataSourceDriverClassName = "com.mysql.cj.jdbc.Driver";
    private static String dataSourceUrl = "jdbc:mysql://localhost:3306/my?useUnicode=true&characterEncoding=utf8&useSSL=false";
    private static String dataSourceUsername = "root";
    private static String dataSourcePassword = "123456";


    public static void main(String[] args) {

        generator(author,dataSourceDriverClassName,dataSourceUrl,dataSourceUsername,dataSourcePassword);

    }
    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     * 代码生成
     * @param author 开发人员
     * @param dataSourceDriverClassName 数据源驱动
     * @param dataSourceUrl 数据源url
     * @param dataSourceUsername 数据源用户
     * @param dataSourcePassword 数据源密码
     */
    public static void generator(String author,String dataSourceDriverClassName,String dataSourceUrl,String dataSourceUsername,String dataSourcePassword){
        String moduleName = scanner("模块名");
        String packageName = scanner("包名");
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        //多模块项目：System.getProperty("user.dir") + "/子模块名称";单模块项目如下
        String projectPath = System.getProperty("user.dir")+"/"+moduleName;
        gc.setActiveRecord(false)
                .setEnableCache(false)
                .setAuthor(author)
                .setOutputDir(projectPath + "/src/main/java")
                .setFileOverride(true)
                .setOpen(false)
                //实体属性 Swagger2 注解
                .setSwagger2(true)
                .setServiceName("%sService");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(dataSourceUrl)
                //.setSchemaName("public")
                .setDriverName(dataSourceDriverClassName)
                .setUsername(dataSourceUsername)
                .setPassword(dataSourcePassword);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc
                //.setModuleName(moduleName)
                .setParent(packageName);
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/"
                        //+ pc.getModuleName() + "/"
                        + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录");
                return false;
            }
        });
        */
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setCapitalMode(true)
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                //这里结合了Lombok，所以设置为true，如果没有集成Lombok，可以设置为false
                .setEntityLombokModel(true)
                .setRestControllerStyle(true)
                .setInclude(scanner("表名，多个英文逗号分割").split(","));
        mpg.setStrategy(strategy);

        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
