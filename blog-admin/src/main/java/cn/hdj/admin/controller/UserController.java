package cn.hdj.admin.controller;


import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hdj.admin.domain.dto.UserFormDTO;
import cn.hdj.admin.domain.dto.UserSearchForm;
import cn.hdj.admin.domain.vo.UserDetailVO;
import cn.hdj.admin.service.IUserService;
import cn.hdj.common.domain.dto.LoginFormDTO;
import cn.hdj.common.domain.dto.RoleMenuPermissionDTO;
import cn.hdj.common.domain.dto.UserDetailDTO;
import cn.hdj.common.domain.vo.PageVO;
import cn.hdj.common.domain.vo.ResultVO;
import cn.hdj.common.validator.ValidatorUtils;
import cn.hdj.common.validator.group.AddGroup;
import cn.hdj.common.validator.group.UpdateGroup;
import cn.hutool.core.collection.CollectionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 系统用户 前端控制器
 * </p>
 *
 * @author huangjiajian
 * @since 2021-10-30
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping(value = "/api/admin/user")
public class UserController {

    @Autowired
    private IUserService service;


    /**
     * 登陆(迁移到认证中心 blog-auth)
     *
     * @param user
     * @param response
     * @return
     */
    @Deprecated
    @PostMapping(value = "/signIn")
    @ApiOperation(value = "登陆", httpMethod = "POST", response = ResultVO.class)
    public ResultVO login(@RequestBody LoginFormDTO user, HttpServletResponse response) {
        SaTokenInfo login = this.service.login(user);
        return ResultVO.successJson(login);
    }

    /**
     * (迁移到认证中心 blog-auth)
     * @return
     */
    @Deprecated
//    @PutMapping("/logout")
//    @ApiOperation(value = "退出登录", httpMethod = "PUT", response = ResultVO.class)
    public ResultVO logout() {
        // 当前会话注销登录
        StpUtil.logout();
        return ResultVO.successJson();
    }


    /**
     * 获取当前登陆用户信息
     *
     * @return
     */
    @GetMapping(value = "/info")
    @ApiOperation(value = "获取当前用户信息", httpMethod = "GET", response = ResultVO.class)
    public ResultVO getUserInfo() {
        Long loginId = StpUtil.getLoginIdAsLong();
        UserDetailVO userInfo = this.service.getUserInfo(loginId);
        return ResultVO.successJson(userInfo);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @GetMapping(value = "/info/{userId}")
    @ApiOperation(value = "获取用户信息ByUserId", httpMethod = "GET", response = ResultVO.class)
    public ResultVO getUserInfo(@PathVariable("userId") Long userId) {
        UserDetailVO userInfo = service.getUserInfo(userId);
        return ResultVO.successJson(userInfo);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @GetMapping(value = "/loadUserByUsername")
    @ApiOperation(value = "获取用户信息ByUserName", httpMethod = "GET", response = ResultVO.class)
    public ResultVO<UserDetailDTO> loadUserByUsername(@RequestParam String account) {
        UserDetailDTO userInfo = this.service.loadUserByUsername(account);
        return ResultVO.successJson(userInfo);
    }

    @GetMapping(value = "/getPermissionList")
    @ApiOperation(value = "获取用户权限", httpMethod = "GET", response = ResultVO.class)
    public ResultVO<RoleMenuPermissionDTO> getPermissionList(@RequestParam Long loginId) {
        List<RoleMenuPermissionDTO> permissionList = this.service.getPermissionList(loginId);
        return ResultVO.successJson(permissionList);
    }



    /**
     * 获取用户列表
     *
     * @param params
     * @return
     */
    @GetMapping(value = "/list")
    @ApiOperation(value = "获取用户列表", httpMethod = "GET", response = ResultVO.class)
    public ResultVO<PageVO> listUser(@ApiParam UserSearchForm params) {
        PageVO pageVO = service.listUser(params);
        return ResultVO.successJson(pageVO);
    }


    @PostMapping(value = "/add")
    @ApiOperation(value = "添加用户", httpMethod = "POST", response = ResultVO.class)
    public ResultVO addUser(@RequestBody UserFormDTO user) {
        ValidatorUtils.validateEntity(user, AddGroup.class);
        service.addUser(user);
        return ResultVO.successJson();
    }

    /**
     * 修改用户
     *
     * @param userId
     * @param user
     * @return
     */
    @PutMapping(value = "/edit/{userId}")
    @ApiOperation(value = "修改用户", httpMethod = "PUT", response = ResultVO.class)
    public ResultVO editUser(@PathVariable("userId") Long userId, @ApiParam @RequestBody UserFormDTO user) {
        ValidatorUtils.validateEntity(user, UpdateGroup.class);
        service.editUser(userId, user);
        return ResultVO.successJson();
    }


    /**
     * 删除多个用户
     *
     * @param userIds
     * @return
     */
    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "删除多个用户", httpMethod = "DELETE", response = ResultVO.class)
    public ResultVO deleteUser(@ApiParam("userIds") @RequestBody List<Long> userIds) {
        if (CollectionUtil.isNotEmpty(userIds)) {
            service.deleteBatch(userIds);
        }
        return ResultVO.successJson();
    }


    @PutMapping("/profile")
    @ApiOperation(value = "更改profile", httpMethod = "PUT", response = ResultVO.class)
    public ResultVO profile(@RequestBody UserFormDTO userForm) {
        ValidatorUtils.validateEntity(userForm);
        service.profile(userForm);
        return ResultVO.successJson();
    }

}
