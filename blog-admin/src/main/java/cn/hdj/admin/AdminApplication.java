package cn.hdj.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author huangjiajian
 * @Date 2021/10/28 下午11:50
 */
@EnableDiscoveryClient
@SpringBootApplication(
        scanBasePackages={
                "cn.hdj.common.config",
                "cn.hdj.admin"
        }
)
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}