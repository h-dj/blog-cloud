package cn.hdj.admin.service;

import cn.hdj.admin.po.UserRolePO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户角色 服务类
 * </p>
 *
 * @author huangjiajian
 * @since 2021-10-30
 */
public interface IUserRoleService extends IService<UserRolePO> {


    /**
     * 删除用户角色关联
     *
     * @param roleIds
     */
    void deleteBatchByRoleId(List<Long> roleIds);
}
