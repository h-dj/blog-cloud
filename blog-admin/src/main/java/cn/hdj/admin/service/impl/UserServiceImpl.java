package cn.hdj.admin.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
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
import cn.hdj.common.domain.dto.LoginFormDTO;
import cn.hdj.common.domain.dto.RoleMenuPermissionDTO;
import cn.hdj.common.domain.dto.UserDetailDTO;
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
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
        userPO.setSalt(IdUtil.simpleUUID());
        userPO.setPassword(SaSecureUtil.md5BySalt(userPO.getPassword(), userPO.getSalt()));
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

        if (CollectionUtil.contains(userIds, StpUtil.getLoginIdAsLong())) {
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
        UserDetailDTO userDetailDTO = this.loadUserByUsername(user.getAccount());
        if (userDetailDTO.getDeleted()) {
            throw new AccountInValidException("账号已注销！");
        }
        if (!BooleanUtil.isTrue(userDetailDTO.getEnable())) {
            throw new AccountInValidException("账号被禁用！");
        }
        String encryptPassword = SaSecureUtil.md5BySalt(user.getPassword(), userDetailDTO.getSalt());
        if (!StrUtil.equals(encryptPassword, userDetailDTO.getPassword())) {
            throw new AccountInValidException("账号或密码错误！");
        }
        userDetailDTO.setPassword(null);
        //保存当前登录的userID
        StpUtil.login(userDetailDTO.getId());
        StpUtil.getSession(false)
                .set(SysConst.LOGIN_USER_PREFIX + userDetailDTO.getId(), userDetailDTO);

        //返回token
        return StpUtil.getTokenInfo();
    }

    @Override
    public List<RoleMenuPermissionDTO> getPermissionList(Object loginId) {
        return this.baseMapper.getPermissionList(loginId);
    }

    @Override
    public UserDetailDTO loadUserByUsername(String account) {

        LambdaQueryWrapper<UserPO> queryWrapper = Wrappers.<UserPO>lambdaQuery()
                .eq(UserPO::getDeleted, false)
                .eq(UserPO::getEnable, true)
                .eq(UserPO::getEmail, account);
        List<UserPO> userPOList = this.baseMapper.selectList(queryWrapper);
        if (CollectionUtil.isEmpty(userPOList)) {
            throw new UserNotFoundException();
        }
        if (userPOList.size() > 1) {
            log.error("账号重复: " + account);
            throw new AccountInValidException("账号或密码错误！");
        }
        UserDetailDTO userDetailDTO = BeanUtil.copyProperties(userPOList.get(0), UserDetailDTO.class);

        List<RoleMenuPermissionDTO> permissionList = this.getPermissionList(userDetailDTO.getId());
        if (CollectionUtil.isNotEmpty(permissionList)) {
            List<String> permissionCodeList = permissionList.stream()
                    .map(RoleMenuPermissionDTO::getPermissionCode)
                    .distinct()
                    .collect(Collectors.toList());
            userDetailDTO.setPermissions(permissionCodeList);
        }

        return userDetailDTO;
    }

}
