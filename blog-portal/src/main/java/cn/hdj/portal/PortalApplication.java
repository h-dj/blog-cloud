package cn.hdj.portal;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author huangjiajian
 */
@EnableDiscoveryClient
@SpringBootApplication(
        scanBasePackages={
                "cn.hdj.common.config",
                "cn.hdj.portal"
        }
)
public class PortalApplication {
    public static void main(String[] args) {
        SpringApplication.run(PortalApplication.class, args);
    }

}