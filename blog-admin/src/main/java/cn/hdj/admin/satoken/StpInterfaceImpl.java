package cn.hdj.admin.satoken;

import cn.dev33.satoken.stp.StpInterface;
import cn.hdj.admin.service.IUserService;
import cn.hdj.common.domain.dto.RoleMenuPermissionDTO;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 自定义权限验证接口扩展(移植到网关，统一鉴权)
 * @Author huangjiajian
 * @Date 2021/10/31 下午10:24
 */
@Deprecated
//@Component
public class StpInterfaceImpl implements StpInterface {


    @Autowired
    private IUserService userService;


    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {

        List<RoleMenuPermissionDTO> permissionList = userService.getPermissionList(loginId);
        if (CollectionUtil.isNotEmpty(permissionList)) {
            return permissionList.stream()
                    .map(RoleMenuPermissionDTO::getPermissionCode)
                    .filter(StrUtil::isNotEmpty)
                    .distinct()
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<RoleMenuPermissionDTO> permissionList = userService.getPermissionList(loginId);
        if (CollectionUtil.isNotEmpty(permissionList)) {
            return permissionList.stream()
                    .map(RoleMenuPermissionDTO::getRoleCode)
                    .filter(StrUtil::isNotEmpty)
                    .distinct()
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
