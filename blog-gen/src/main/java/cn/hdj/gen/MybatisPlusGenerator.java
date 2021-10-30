package cn.hdj.gen;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import cn.hutool.setting.dialect.PropsUtil;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author huangjiajian
 * @Date 2021/10/29 下午8:08
 */
public class MybatisPlusGenerator {

    public static void main(String[] args) {
        Props props = PropsUtil.get("generator.properties");
        List<String> tables = StrUtil.split(props.getStr("project.tables"), ",")
                .stream()
                .filter(StrUtil::isNotEmpty)
                .collect(Collectors.toList());


        String tablePrefix = props.getStr("table.remove.prefix");
        String packageParent = props.getStr("package.parent");
        String author = props.getStr("project.author");
        String outputDir = props.getStr("project.output.dir");
        String mapperPath = props.getStr("package.mapper.path");
        Boolean fileOverride = props.getBool("project.file.override",false);

        DataSourceConfig.Builder dataSourceConfigBuilder = new DataSourceConfig.Builder(
                props.getStr("dataSource.url"),
                props.getStr("dataSource.username"),
                props.getStr("dataSource.password")
        );
        FastAutoGenerator.create(dataSourceConfigBuilder)
                .globalConfig(builder -> {
                    builder.disableOpenDir()
                            .author(author) // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .outputDir(outputDir); // 指定输出目录

                    if(fileOverride){
                        builder.fileOverride();
                    }
                })
                .packageConfig(builder -> {
                    builder
                            .entity("po")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, mapperPath))// 设置mapperXml生成路径
                            .parent(packageParent); // 设置父包名
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tables) // 设置需要生成的表名
                            // 设置过滤表前缀
                            .addTablePrefix(tablePrefix)
                            .entityBuilder()
                            .addTableFills(new Column("create_time", FieldFill.INSERT))
                            .addTableFills(new Column("update_time", FieldFill.INSERT_UPDATE))
                            .idType(IdType.ASSIGN_ID)
                            .formatFileName("%sPO")
                            .enableLombok()
                            .build()
                            .controllerBuilder()
                            .enableRestStyle()

                    ;
                })
                .templateEngine(new VelocityTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
