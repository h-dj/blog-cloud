package cn.hdj.admin.utils;

import cn.dev33.satoken.secure.SaSecureUtil;
import org.junit.jupiter.api.Test;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author huangjiajian
 * @Date 2021/10/31 下午7:23
 */
public class SaSecureUtilTest {

    @Test
    public void test_password(){
        String encryptPassword = SaSecureUtil.md5BySalt("123456", "123456");
        System.out.println(encryptPassword);
    }
}
