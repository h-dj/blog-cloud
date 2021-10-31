package cn.hdj.admin.service;

import cn.hdj.admin.domain.dto.UserFormDTO;
import cn.hdj.admin.domain.dto.UserSearchForm;
import cn.hdj.admin.domain.vo.UserDetailVO;
import cn.hdj.admin.po.UserPO;
import cn.hdj.common.domain.vo.PageVO;
import cn.hdj.common.domain.vo.ResultVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author huangjiajian
 * @since 2021-10-30
 */
public interface IUserService extends IService<UserPO> {


    /**
     * 修改密码
     *
     * @param oldPassword
     * @param newPassword
     * @return ResultVO
     */
    ResultVO changePassword(String oldPassword, String newPassword);


    /**
     * 创建用户
     *
     * @param user
     * @return
     */
    void addUser(UserFormDTO user);

    /**
     * 修改用户
     *
     * @param userId
     * @param user
     * @return
     */
    void editUser(Long userId, UserFormDTO user);


    /**
     * 获取用户详情
     *
     * @param userId
     * @return
     */
    UserDetailVO getUserInfo(Long userId);

    /**
     * 删除多个用户
     *
     * @param userIds
     * @return
     */
    void deleteBatch(List<Long> userIds);

    /**
     * 查找用户所拥有的菜单id
     *
     * @param userId
     * @return
     */
    List<Long> findAllMenuForUser(Long userId);

    /**
     * 用户列表
     *
     * @param params
     * @return
     */
    PageVO listUser(UserSearchForm params);

    /**
     * 更新profile
     *
     * @param userForm
     */
    void profile(UserFormDTO userForm);
}
