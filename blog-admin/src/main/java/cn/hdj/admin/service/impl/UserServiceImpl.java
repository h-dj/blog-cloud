package cn.hdj.admin.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hdj.admin.domain.dto.LoginFormDTO;
import cn.hdj.admin.domain.dto.RoleMenuPermissionDTO;
import cn.hdj.admin.domain.dto.UserFormDTO;
import cn.hdj.admin.domain.dto.UserSearchForm;
import cn.hdj.admin.domain.vo.UserDetailVO;
import cn.hdj.admin.mapper.UserMapper;
import cn.hdj.admin.po.UserPO;
import cn.hdj.admin.po.UserRolePO;
import cn.hdj.admin.service.IRoleService;
import cn.hdj.admin.service.IUserRoleService;
import cn.hdj.admin.service.IUserService;
import cn.hdj.common.consts.SysConst;
import cn.hdj.common.domain.vo.PageVO;
import cn.hdj.common.domain.vo.ResultVO;
import cn.hdj.common.enums.ResponseCodeEnum;
import cn.hdj.common.exception.AccountInValidException;
import cn.hdj.common.exception.BaseException;
import cn.hdj.common.exception.RecordRepeatException;
import cn.hdj.common.exception.UserNotFoundException;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        UserPO userPO = BeanUtil.copyProperties(user, UserPO.class);
        long count = this.count(Wrappers.<UserPO>lambdaQuery()
                .eq(UserPO::getEmail, userPO.getEmail())
                .eq(UserPO::getDeleted, false)
        );
        if (count > 0) {
            throw new RecordRepeatException("邮箱已注册！");
        }
        userPO.setSalt("123456");
        this.baseMapper.insert(userPO);
        Set<Long> roleIds = user.getRoleIds();
        if (CollectionUtil.isNotEmpty(roleIds)) {
            List<UserRolePO> userRoleList = roleIds.stream()
                    .map(roleId -> {
                        UserRolePO userRolePO = new UserRolePO();
                        userRolePO.setRoleId(roleId);
                        userRolePO.setUserId(userPO.getId());
                        return userRolePO;
                    })
                    .collect(Collectors.toList());
            this.userRoleService.saveBatch(userRoleList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void editUser(Long userId, UserFormDTO user) {
        long count = this.count(Wrappers.<UserPO>lambdaQuery()
                .eq(UserPO::getEmail, user.getEmail())
                .ne(UserPO::getId, userId)
                .eq(UserPO::getDeleted, false)
        );
        if (count > 0) {
            throw new RecordRepeatException("邮箱已注册！");
        }
        final UserPO userPO = BeanUtil.copyProperties(user, UserPO.class);
        userPO.setId(userId);

        this.baseMapper.updateById(userPO);
        this.userRoleService.remove(Wrappers.<UserRolePO>lambdaQuery().eq(UserRolePO::getUserId, userId));
        Set<Long> roleIds = user.getRoleIds();
        if (CollectionUtil.isNotEmpty(roleIds)) {
            List<UserRolePO> userRoleList = roleIds.stream()
                    .map(roleId -> {
                        UserRolePO userRolePO = new UserRolePO();
                        userRolePO.setRoleId(roleId);
                        userRolePO.setUserId(userPO.getId());
                        return userRolePO;
                    })
                    .collect(Collectors.toList());
            this.userRoleService.saveBatch(userRoleList);
        }
    }


    @Override
    public UserDetailVO getUserInfo(Long userId) {
        UserPO userDO = this.getById(userId);
        if (userDO == null) {
            throw new UserNotFoundException();
        }
        UserDetailVO userDetailVO = new UserDetailVO();
        BeanUtil.copyProperties(userDO, userDetailVO);
        Set<Long> roleIds = roleService.queryRoleIdList(userId);
        userDetailVO.setRoleIds(roleIds);
        return userDetailVO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatch(List<Long> userIds) {
        if (CollectionUtil.contains(userIds, SysConst.SUPER_ADMIN)) {
            throw new BaseException("系统管理员不能删除", ResponseCodeEnum.ERROR.getCode());
        }

        if (CollectionUtil.contains(userIds, 999)) {
            throw new BaseException("当前用户不能删除", ResponseCodeEnum.ERROR.getCode());
        }
        userRoleService.remove(Wrappers.<UserRolePO>lambdaQuery().in(UserRolePO::getUserId, userIds));
        baseMapper.update(null, Wrappers.<UserPO>lambdaUpdate()
                .set(UserPO::getDeleted, true)
                .in(UserPO::getId, userIds)
        );
    }

    @Override
    public List<Long> findAllMenuForUser(Long userId) {
        return baseMapper.findAllMenuForUser(userId);
    }


    @Override
    public PageVO listUser(UserSearchForm params) {

        IPage<UserPO> page = page(
                params.getIPage(),
                Wrappers.<UserPO>lambdaQuery()
                        .select(UserPO::getId,
                                UserPO::getEmail,
                                UserPO::getEnable,
                                UserPO::getIsLocked,
                                UserPO::getUserName,
                                UserPO::getAvatar,
                                UserPO::getRemark,
                                UserPO::getLoginTime)
                        .and(StrUtil.isNotEmpty(params.getKey()),
                                sql -> sql.like(UserPO::getEmail, params.getKey())
                                        .or()
                                        .like(UserPO::getUserName, params.getKey())
                        )
                        .eq(UserPO::getDeleted, false)
        );
        return PageVO.build(page);
    }

    @Override
    public void profile(UserFormDTO userForm) {

    }

    @Override
    public SaTokenInfo login(LoginFormDTO user) {
        //判断是否已登录
        if (StpUtil.isLogin()) {
            return StpUtil.getTokenInfo();
        }

        //查询用户
        List<UserPO> list = this.list(Wrappers.<UserPO>lambdaQuery()
                .eq(UserPO::getEmail, user.getAccount())
                .eq(UserPO::getDeleted, false)
        );
        if (CollectionUtil.isEmpty(list)) {
            throw new AccountInValidException("账号或密码错误！");
        }
        if (list.size() > 1) {
            throw new AccountInValidException();
        }
        UserPO userPO = list.get(0);
        if (userPO.getDeleted()) {
            throw new AccountInValidException("账号已注销！");
        }
        if (!BooleanUtil.isTrue(userPO.getEnable())) {
            throw new AccountInValidException("账号被禁用！");
        }
        String encryptPassword = SaSecureUtil.md5BySalt(user.getPassword(), userPO.getSalt());
        if (!StrUtil.equals(encryptPassword, userPO.getPassword())) {
            throw new AccountInValidException("账号或密码错误！");
        }
        //保存当前登录的userID
        StpUtil.login(userPO.getId());
        //返回token
        return StpUtil.getTokenInfo();
    }

    @Override
    public List<RoleMenuPermissionDTO> getPermissionList(Object loginId) {
        return this.baseMapper.getPermissionList(loginId);
    }

}
