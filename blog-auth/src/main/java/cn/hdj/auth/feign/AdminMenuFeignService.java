package cn.hdj.auth.feign;

import cn.hdj.common.domain.dto.RoleMenuPermissionDTO;
import cn.hdj.common.domain.dto.UserDetailDTO;
import cn.hdj.common.domain.vo.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author huangjiajian
 * @Date 2021/11/7 下午9:34
 */
@FeignClient(name = "blog-admin", contextId = "menu")
public interface AdminMenuFeignService {
    /**
     * 加载菜单权限
     *
     * @return
     */
    @GetMapping("/api/admin/menus/queryDynamicPermissionList")
    ResultVO<List<RoleMenuPermissionDTO>> queryDynamicPermissionList();
}