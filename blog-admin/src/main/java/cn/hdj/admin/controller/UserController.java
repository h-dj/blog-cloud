package cn.hdj.admin.controller;


import cn.hdj.admin.domain.dto.UserFormDTO;
import cn.hdj.admin.domain.dto.UserSearchForm;
import cn.hdj.admin.service.IUserService;
import cn.hdj.common.domain.vo.ResultVO;
import cn.hdj.common.validator.ValidatorUtils;
import cn.hdj.common.validator.group.AddGroup;
import cn.hdj.common.validator.group.UpdateGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * 获取当前登陆用户信息
     *
     * @return
     */
    @GetMapping(value = "/info")
    @ApiOperation(value = "获取当前用户信息", httpMethod = "GET", response = ResultVO.class)
    public ResultVO getUserInfo() {
        return ResultVO.successJson(null);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @GetMapping(value = "/info/{userId}")
    @ApiOperation(value = "获取用户信息", httpMethod = "GET", response = ResultVO.class)
    public ResultVO getUserInfo(@PathVariable("userId") Long userId) {
        return ResultVO.successJson(service.getUserInfo(userId));
    }


    /**
     * 获取用户列表
     *
     * @param params
     * @return
     */
    @GetMapping(value = "/list")
    @ApiOperation(value = "获取用户列表", httpMethod = "GET", response = ResultVO.class)
    public ResultVO listUser(@ApiParam UserSearchForm params) {
        return ResultVO.successJson(service.listUser(params));
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
        if (userIds != null && userIds.size() > 0) {
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
