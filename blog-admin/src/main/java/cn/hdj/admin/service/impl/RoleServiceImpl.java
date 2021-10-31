package cn.hdj.admin.service.impl;

import cn.hdj.admin.domain.dto.RoleFormDTO;
import cn.hdj.admin.domain.vo.RoleDetailVO;
import cn.hdj.admin.mapper.RoleMapper;
import cn.hdj.admin.po.RoleMenuPO;
import cn.hdj.admin.po.RolePO;
import cn.hdj.admin.po.UserRolePO;
import cn.hdj.admin.service.IRoleMenuService;
import cn.hdj.admin.service.IRoleService;
import cn.hdj.admin.service.IUserRoleService;
import cn.hdj.common.consts.SysConst;
import cn.hdj.common.exception.RecordRepeatException;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统角色 服务实现类
 * </p>
 *
 * @author huangjiajian
 * @since 2021-10-30
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RolePO> implements IRoleService {

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IRoleMenuService roleMenuService;

    @Override
    public Set<Long> queryRoleIdList(Long userId) {
        if (SysConst.SUPER_ADMIN.equals(userId)) {
            return baseMapper.selectList(null).stream()
                    .map(r -> r.getId())
                    .collect(Collectors.toSet());
        }
        return userRoleService.list(Wrappers.<UserRolePO>lambdaQuery()
                .select(UserRolePO::getRoleId)
                .eq(UserRolePO::getUserId, userId)
        ).stream().map(ur -> ur.getRoleId()).collect(Collectors.toSet());
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addRole(RoleFormDTO role) {

        long count = this.count(Wrappers.<RolePO>lambdaQuery()
                .and(sql -> sql.eq(RolePO::getRoleName, role.getRoleName())
                        .or()
                        .eq(RolePO::getRoleCode, role.getRoleCode())
                )
                .eq(RolePO::getDeleted, false)
        );
        if (count > 0) {
            throw new RecordRepeatException("角色名称或者角色编码重复！");
        }

        RolePO rolePO = BeanUtil.copyProperties(role, RolePO.class);

        this.baseMapper.insert(rolePO);

        Set<Long> menuIds = role.getMenuIds();
        if (CollectionUtil.isNotEmpty(menuIds)) {
            this.roleMenuService.correlationPermissions(rolePO.getId(), menuIds);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void editRole(Long roleId, RoleFormDTO role) {
        long count = this.count(
                Wrappers.<RolePO>lambdaQuery()
                        .ne(RolePO::getId, roleId)
                        .eq(RolePO::getRoleName, role.getRoleName())
                        .eq(RolePO::getDeleted, false)
        );
        if (count > 0) {
            throw new RecordRepeatException("角色名称或者角色编码重复！");
        }
        RolePO rolePO = BeanUtil.copyProperties(role, RolePO.class);
        rolePO.setId(roleId);

        this.baseMapper.updateById(rolePO);

        //先删除角色与菜单关系
        this.roleMenuService.remove(Wrappers.<RoleMenuPO>lambdaQuery()
                .eq(RoleMenuPO::getRoleId, roleId));

        Set<Long> menuIds = role.getMenuIds();
        if (CollectionUtil.isNotEmpty(menuIds)) {
            this.roleMenuService.correlationPermissions(rolePO.getId(), menuIds);
        }
    }


    @Override
    public RoleDetailVO roleInfo(Long roleId) {
        RolePO rolePO = this.baseMapper.selectById(roleId);
        RoleDetailVO roleDetailVO = BeanUtil.copyProperties(rolePO, RoleDetailVO.class);

        List<RoleMenuPO> list = this.roleMenuService.list(Wrappers.<RoleMenuPO>lambdaQuery()
                .select(RoleMenuPO::getMenuId)
                .eq(RoleMenuPO::getRoleId, roleDetailVO.getId())
        );
        if (CollectionUtil.isNotEmpty(list)) {
            Set<Long> set = list.stream()
                    .map(v -> v.getMenuId())
                    .collect(Collectors.toSet());
            roleDetailVO.setMenuIds(set);
        }
        return roleDetailVO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatch(List<Long> roleIds) {
        if (CollectionUtil.isEmpty(roleIds)) {
            log.warn("删除的角色id为空");
            return;
        }
        //删除角色
        this.baseMapper.update(null, Wrappers.<RolePO>lambdaUpdate()
                .in(RolePO::getId, roleIds)
                .set(RolePO::getDeleted, true)
        );

        //删除角色与菜单关联
        this.roleMenuService.deleteBatchByRoleId(roleIds);

        //删除角色与用户关联
        this.userRoleService.deleteBatchByRoleId(roleIds);
    }
}
