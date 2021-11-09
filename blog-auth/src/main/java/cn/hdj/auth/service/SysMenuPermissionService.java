package cn.hdj.auth.service;

import cn.hdj.auth.feign.AdminMenuFeignService;
import cn.hdj.common.consts.SysConst;
import cn.hdj.common.domain.dto.RoleMenuPermissionDTO;
import cn.hdj.common.domain.dto.UserDetailDTO;
import cn.hdj.common.domain.vo.ResultVO;
import cn.hutool.core.util.BooleanUtil;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author huangjiajian
 * @Date 2021/11/9 下午10:59
 */
@Service
@Slf4j
public class SysMenuPermissionService {

    @Autowired
    private AdminMenuFeignService adminMenuFeignService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void reloadMenuPermission() {
        log.info("菜单权限刷新！ {}", SysConst.MENU_PERMISSON);
        ResultVO<List<RoleMenuPermissionDTO>> resultVO = adminMenuFeignService.queryDynamicPermissionList();
        if (resultVO.getSuccess()) {
            List<RoleMenuPermissionDTO> menuPermissionList = resultVO.getData();
            Map<String, String> map = menuPermissionList.stream()
                    .collect(Collectors.toMap(
                            RoleMenuPermissionDTO::getRequestUrl
                            , RoleMenuPermissionDTO::getPermissionCode)
                    );
            stringRedisTemplate.opsForHash().putAll(SysConst.MENU_PERMISSON, map);
        }

    }
}
