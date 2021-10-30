package cn.hdj.admin.service.impl;

import cn.hdj.admin.domain.dto.UserDetailDTO;
import cn.hdj.admin.domain.dto.UserFormDTO;
import cn.hdj.admin.domain.dto.UserSearchForm;
import cn.hdj.admin.mapper.UserMapper;
import cn.hdj.admin.po.UserPO;
import cn.hdj.admin.service.IRoleService;
import cn.hdj.admin.service.IUserRoleService;
import cn.hdj.admin.service.IUserService;
import cn.hdj.common.domain.vo.PageVO;
import cn.hdj.common.domain.vo.ResultVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author huangjiajian
 * @since 2021-10-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPO> implements IUserService {


    @Autowired
    private IRoleService roleService;

    @Autowired
    private IUserRoleService userRoleService;


    @Override
    public ResultVO changePassword(String oldPassword, String newPassword) {
        return null;
    }




    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addUser(UserFormDTO user) {

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void editUser(Long userId, UserFormDTO user) {

    }


    @Override
    public UserDetailDTO getUserInfo(Long userId) {
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatch(List<Long> userIds) {

    }

    @Override
    public List<Long> findAllMenuForUser(Long userId) {
        return null;
    }


    @Override
    public PageVO listUser(UserSearchForm params) {
        return null;
    }

    @Override
    public void profile(UserFormDTO userForm) {

    }
}
