package cn.hdj.admin.mapper;

import cn.hdj.admin.domain.dto.RoleMenuPermissionDTO;
import cn.hdj.admin.po.MenuPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 系统菜单 Mapper 接口
 * </p>
 *
 * @author huangjiajian
 * @since 2021-10-30
 */
public interface MenuMapper extends BaseMapper<MenuPO> {

    List<RoleMenuPermissionDTO> queryDynamicPermissionList();

}
