package cn.hdj.auth.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hdj.auth.service.SysLoginService;
import cn.hdj.auth.service.SysMenuPermissionService;
import cn.hdj.common.domain.dto.LoginFormDTO;
import cn.hdj.common.domain.dto.RegisterFormDTO;
import cn.hdj.common.domain.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 认证授权Token 控制器
 * @Author huangjiajian
 * @Date 2021/11/8 上午12:25
 */
@RestController
@RequestMapping("/api/auth")
public class TokenController {

    @Autowired
    private SysLoginService sysLoginService;

    @Autowired
    private SysMenuPermissionService sysMenuPermissionService;

    @PostConstruct
    public void loadMenuPermisson() {
        sysMenuPermissionService.reloadMenuPermission();
    }


    /**
     * 登陆
     *
     * @param user
     * @param response
     * @return
     */
    @PostMapping(value = "/signIn")
    public ResultVO login(@Validated @RequestBody LoginFormDTO user, HttpServletResponse response) {
        SaTokenInfo login = this.sysLoginService.login(user);
        return ResultVO.successJson(login);
    }

    /**
     * 登出
     *
     * @return
     */
    @PutMapping("/logout")
    public ResultVO logout() {
        // 当前会话注销登录
        StpUtil.logout();
        return ResultVO.successJson();
    }
}
