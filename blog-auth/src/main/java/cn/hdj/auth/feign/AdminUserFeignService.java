package cn.hdj.auth.feign;

import cn.hdj.common.domain.dto.UserDetailDTO;
import cn.hdj.common.domain.vo.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author huangjiajian
 * @Date 2021/11/7 下午9:34
 */
@FeignClient(name = "blog-admin",contextId = "user")
public interface AdminUserFeignService {
    /**
     * 获取用户信息
     *
     * @param account
     * @return
     */
    @GetMapping("/api/admin/user/loadUserByUsername")
    ResultVO<UserDetailDTO> loadUserByUsername(@RequestParam String account);
}