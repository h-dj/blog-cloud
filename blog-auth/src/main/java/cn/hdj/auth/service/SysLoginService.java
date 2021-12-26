package cn.hdj.auth.service;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hdj.auth.feign.AdminUserFeignService;
import cn.hdj.common.consts.SysConst;
import cn.hdj.common.domain.dto.LoginFormDTO;
import cn.hdj.common.domain.dto.UserDetailDTO;
import cn.hdj.common.domain.vo.ResultVO;
import cn.hdj.common.exception.AccountInValidException;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 系统登录服务
 * @Author huangjiajian
 * @Date 2021/11/8 上午12:31
 */
@Service
@Slf4j
public class SysLoginService {

    @Autowired
    private AdminUserFeignService adminUserFeignService;

    public SaTokenInfo login(LoginFormDTO user) {
        //判断是否已登录
        if (StpUtil.isLogin()) {
            return StpUtil.getTokenInfo();
        }

        //查询用户
        ResultVO<UserDetailDTO> resultVO = adminUserFeignService.loadUserByUsername(user.getAccount());

        if (!BooleanUtil.isTrue(resultVO.getSuccess())) {
            log.error("登录错误： msg {}", resultVO.getMsg());
            throw new AccountInValidException("账号或密码错误！");
        }
        UserDetailDTO data = resultVO.getData();
        if (data.getDeleted()) {
            throw new AccountInValidException("账号已注销！");
        }
        if (!BooleanUtil.isTrue(data.getEnable())) {
            throw new AccountInValidException("账号被禁用！");
        }
        String encryptPassword = SaSecureUtil.md5BySalt(user.getPassword(), data.getSalt());
        if (!StrUtil.equals(encryptPassword, data.getPassword())) {
            throw new AccountInValidException("账号或密码错误！");
        }
        //保存当前登录的userID
        StpUtil.login(data.getId());

        //保存当前用户登录的 权限
        StpUtil.getSessionByLoginId(data.getId())
                .set(SysConst.LOGIN_USER_PREFIX + data.getId(), data)
                .set(SaSession.PERMISSION_LIST, data.getPermissions());

        //返回token
        return StpUtil.getTokenInfo();
    }
}
