package cn.hdj.admin.service;

import cn.hdj.admin.domain.dto.RoleFormDTO;
import cn.hdj.admin.domain.vo.RoleDetailVO;
import cn.hdj.admin.po.RolePO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 系统角色 服务类
 * </p>
 *
 * @author huangjiajian
 * @since 2021-10-30
 */
public interface IRoleService extends IService<RolePO> {

    Set<Long> queryRoleIdList(Long userId);


    /**
     * 添加角色
     *
     * @param role
     * @return
     */
    void addRole(RoleFormDTO role);

    /**
     * 修改角色
     *
     * @param roleId
     * @param role
     * @return
     */
    void editRole(Long roleId, RoleFormDTO role);


    /**
     * 获取角色详情
     *
     * @param roleId
     * @return
     */
    RoleDetailVO roleInfo(Long roleId);

    /**
     * 批量删除角色
     *
     * @param roleIds
     * @return
     */
    void deleteBatch(List<Long> roleIds);
}
