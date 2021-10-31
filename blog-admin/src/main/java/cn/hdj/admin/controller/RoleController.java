package cn.hdj.admin.controller;


import cn.hdj.admin.domain.dto.RoleFormDTO;
import cn.hdj.admin.domain.dto.RoleSearchFormDTO;
import cn.hdj.admin.domain.vo.RoleDetailVO;
import cn.hdj.admin.po.RolePO;
import cn.hdj.admin.service.IRoleService;
import cn.hdj.common.domain.vo.PageVO;
import cn.hdj.common.domain.vo.ResultVO;
import cn.hdj.common.validator.ValidatorUtils;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 系统角色 前端控制器
 * </p>
 *
 * @author huangjiajian
 * @since 2021-10-30
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("/api/admin/role")
public class RoleController {

    @Autowired
    private IRoleService service;


    @GetMapping(value = "/selectList")
    @ApiOperation(value = "下拉角色列表", httpMethod = "GET", response = ResultVO.class)
    public ResultVO selectList() {
        LambdaQueryWrapper<RolePO> query = Wrappers.lambdaQuery();
        return ResultVO.successJson(
                service.list(query.eq(RolePO::getDeleted, false))
        );
    }


    @GetMapping(value = "/list")
    @ApiOperation(value = "角色列表", httpMethod = "GET", response = ResultVO.class)
    public ResultVO list(@ApiParam RoleSearchFormDTO roleForm) {
        LambdaQueryWrapper<RolePO> query = Wrappers.lambdaQuery();
        IPage<RolePO> page = service.page(
                roleForm.getIPage(),
                query.eq(RolePO::getDeleted, false)
                        .like(
                                StrUtil.isNotBlank(roleForm.getRoleName()),
                                RolePO::getRoleName,
                                roleForm.getRoleName()
                        )
        );
        return ResultVO.successJson(PageVO.build(page));
    }


    @GetMapping(value = "/info/{roleId}")
    @ApiOperation(value = "角色详情", httpMethod = "GET", response = ResultVO.class)
    public ResultVO roleInfo(@PathVariable("roleId") Long roleId) {
        RoleDetailVO roleDetailVO = service.roleInfo(roleId);
        return ResultVO.successJson(roleDetailVO);
    }


    @PostMapping(value = "/add")
    @ApiOperation(value = "添加角色", httpMethod = "POST", response = ResultVO.class)
    public ResultVO addRole(@RequestBody RoleFormDTO role) {
        ValidatorUtils.validateEntity(role);
        service.addRole(role);
        return ResultVO.successJson();
    }


    @PutMapping(value = "/edit/{roleId}")
    @ApiOperation(value = "编辑角色", httpMethod = "PUT", response = ResultVO.class)
    public ResultVO editRole(@PathVariable("roleId") Long roleId, @RequestBody RoleFormDTO role) {
        ValidatorUtils.validateEntity(role);
        service.editRole(roleId, role);
        return  ResultVO.successJson();
    }


    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "批量删除角色", httpMethod = "DELETE", response = ResultVO.class)
    public ResultVO deleteBatch(@RequestBody List<Long> roleIds) {
        service.deleteBatch(roleIds);
        return ResultVO.successJson();
    }
}

