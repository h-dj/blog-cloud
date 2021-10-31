package cn.hdj.admin.service.impl;

import cn.hdj.admin.mapper.RoleMenuMapper;
import cn.hdj.admin.po.RoleMenuPO;
import cn.hdj.admin.service.IRoleMenuService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色菜单表 服务实现类
 * </p>
 *
 * @author huangjiajian
 * @since 2021-10-30
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenuPO> implements IRoleMenuService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void correlationPermissions(Long roleId, Set<Long> menuIds) {
        List<RoleMenuPO> list = new ArrayList<>(menuIds.size());
        for (Long menuId : menuIds) {
            RoleMenuPO roleMenuDO = new RoleMenuPO();
            roleMenuDO.setRoleId(roleId);
            roleMenuDO.setMenuId(menuId);
            list.add(roleMenuDO);
        }
        this.saveBatch(list);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatchByRoleId(List<Long> roleIds) {
        baseMapper.delete(Wrappers.<RoleMenuPO>lambdaQuery()
                .in(RoleMenuPO::getRoleId, roleIds)
        );
    }
}
