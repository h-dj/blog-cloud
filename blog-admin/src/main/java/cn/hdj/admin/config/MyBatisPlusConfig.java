package cn.hdj.admin.config;

import cn.hdj.common.config.BaseBatisPlusConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author huangjiajian
 * @Date 2021/10/28 下午11:50
 */
@MapperScan(basePackages = "cn.hdj.admin.mapper")
@Configuration
public class MyBatisPlusConfig extends BaseBatisPlusConfig {
}