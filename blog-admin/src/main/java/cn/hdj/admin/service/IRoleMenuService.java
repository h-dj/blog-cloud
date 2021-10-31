package cn.hdj.admin.service;

import cn.hdj.admin.po.RoleMenuPO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色菜单表 服务类
 * </p>
 *
 * @author huangjiajian
 * @since 2021-10-30
 */
public interface IRoleMenuService extends IService<RoleMenuPO> {

    /**
     * 关联角色与权限
     *
     * @param roleId
     * @param menuIds
     */
    void correlationPermissions(Long roleId, Set<Long> menuIds);

    /**
     * 通过角色id删除角色菜单关联
     *
     * @param roleIds
     * @return
     */
    void deleteBatchByRoleId(List<Long> roleIds);

}
