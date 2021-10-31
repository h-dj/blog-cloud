package cn.hdj.admin.service.impl;

import cn.hdj.admin.domain.dto.RoleMenuPermissionDTO;
import cn.hdj.admin.mapper.MenuMapper;
import cn.hdj.admin.po.MenuPO;
import cn.hdj.admin.service.IMenuService;
import cn.hdj.admin.service.IUserService;
import cn.hdj.common.consts.SysConst;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统菜单 服务实现类
 * </p>
 *
 * @author huangjiajian
 * @since 2021-10-30
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuPO> implements IMenuService {


    @Autowired
    private IUserService userService;

    @Override
    public List<MenuPO> listForUser(Long userId) {
        //系统管理员，拥有最高权限
        if (SysConst.SUPER_ADMIN.equals(userId)) {
            return getAllMenuList(null);
        }
        List<Long> menuIdList = userService.findAllMenuForUser(userId);
        return getAllMenuList(menuIdList);
    }

    @Override
    public List<RoleMenuPermissionDTO> queryDynamicPermissionList() {
        return this.baseMapper.queryDynamicPermissionList();
    }


    /**
     * 根据菜单id 查询菜单
     *
     * @param menuIdList
     * @return
     */
    private List<MenuPO> getAllMenuList(List<Long> menuIdList) {
        return list(Wrappers.<MenuPO>lambdaQuery()
                .eq(MenuPO::getDeleted, false)
                .in(CollectionUtil.isNotEmpty(menuIdList), MenuPO::getId, menuIdList)
                .orderByAsc(MenuPO::getCreateTime)
        );
    }
}
