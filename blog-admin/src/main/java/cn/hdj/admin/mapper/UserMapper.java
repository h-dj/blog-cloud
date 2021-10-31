package cn.hdj.admin.mapper;

import cn.hdj.admin.domain.dto.RoleMenuPermissionDTO;
import cn.hdj.admin.po.UserPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统用户 Mapper 接口
 * </p>
 *
 * @author huangjiajian
 * @since 2021-10-30
 */
public interface UserMapper extends BaseMapper<UserPO> {

    /**
     * 获取当前登录用户的权限标识
     * @param loginId
     * @return
     */
    List<RoleMenuPermissionDTO> getPermissionList(@Param("loginId") Object loginId);

    List<Long> findAllMenuForUser(Long userId);
}
