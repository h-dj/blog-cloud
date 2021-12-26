package cn.hdj.portal.config;

import cn.hdj.common.config.BaseBatisPlusConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author huangjiajian
 */
@MapperScan(basePackages = "cn.hdj.portal.mapper")
@Configuration
public class MyBatisPlusConfig extends BaseBatisPlusConfig {

}