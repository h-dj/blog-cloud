package cn.hdj.admin.service;

import cn.hdj.admin.domain.dto.RoleMenuPermissionDTO;
import cn.hdj.admin.po.MenuPO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统菜单 服务类
 * </p>
 *
 * @author huangjiajian
 * @since 2021-10-30
 */
public interface IMenuService extends IService<MenuPO> {

    /**
     * 获取用户菜单
     *
     * @param userId
     * @return
     */
    List<MenuPO> listForUser(Long userId);

    /**
     * 获取权限编码与请求url 的映射关系
     *
     * @return
     */
    List<RoleMenuPermissionDTO> queryDynamicPermissionList();
}
