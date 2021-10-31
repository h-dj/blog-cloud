package cn.hdj.admin.service.impl;

import cn.hdj.admin.mapper.UserRoleMapper;
import cn.hdj.admin.po.UserRolePO;
import cn.hdj.admin.service.IUserRoleService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 用户角色 服务实现类
 * </p>
 *
 * @author huangjiajian
 * @since 2021-10-30
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRolePO> implements IUserRoleService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatchByRoleId(List<Long> roleIds) {
        baseMapper.delete(Wrappers.<UserRolePO>lambdaQuery()
                .in(UserRolePO::getRoleId, roleIds)
        );
    }
}
