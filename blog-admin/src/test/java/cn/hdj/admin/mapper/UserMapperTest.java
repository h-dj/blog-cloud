package cn.hdj.admin.mapper;

import cn.hdj.admin.po.UserPO;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;

import java.util.List;

/**
 * @author huangjiajian
 * @date 2021/10/29
 */
@Lazy
@SpringBootTest(
        properties = {
                "spring.main.lazy-initialization=true"
        }
)
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test_query_user_list() {
        List<UserPO> userPOS = this.userMapper.selectList(Wrappers.emptyWrapper());
        Assert.isTrue(CollectionUtil.isNotEmpty(userPOS));

    }


}