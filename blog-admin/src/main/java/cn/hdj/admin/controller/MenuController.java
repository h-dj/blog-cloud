package cn.hdj.admin.controller;


import cn.dev33.satoken.stp.StpUtil;
import cn.hdj.admin.po.MenuPO;
import cn.hdj.admin.service.IMenuService;
import cn.hdj.common.domain.vo.ResultVO;
import cn.hdj.common.enums.MenuTypeEnum;
import cn.hdj.common.exception.ParamInValidException;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 系统资源 前端控制器
 * </p>
 *
 * @author huangjiajian
 * @since 2021-10-30
 */
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/api/admin/menus")
public class MenuController {

    @Autowired
    private IMenuService service;


    @GetMapping("/nav")
    @ApiOperation(value = "获取侧边栏导航", httpMethod = "GET", response = ResultVO.class)
    public ResultVO nav() {
        long loginIdAsLong = StpUtil.getLoginIdAsLong();

        return ResultVO.successJson(this.service.listForUser(loginIdAsLong));
    }


    @GetMapping(value = "/all")
    @ApiOperation(value = "菜单列表", httpMethod = "GET", response = ResultVO.class)
    public ResultVO all(String key) {
        List<MenuPO> list = this.service.list(Wrappers.<MenuPO>lambdaQuery()
                .eq(MenuPO::getDeleted, false)
                .like(StrUtil.isNotEmpty(key), MenuPO::getMenuName, key)
        );
        return ResultVO.successJson(list);
    }


    /**
     * 下拉菜单列表(去除按钮菜单)
     *
     * @return
     */
    @GetMapping(value = "/select")
    @ApiOperation(value = "下拉菜单列表", httpMethod = "GET", response = ResultVO.class)
    public ResultVO select() {
        List<MenuPO> list = this.service.list(Wrappers.<MenuPO>lambdaQuery()
                .select(MenuPO::getId,MenuPO::getMenuName,MenuPO::getParentId)
                .eq(MenuPO::getDeleted, false)
        );
        return ResultVO.successJson(list);
    }

    @GetMapping(value = "/info/{menuId}")
    @ApiOperation(value = "菜单详情", httpMethod = "GET", response = ResultVO.class)
    public ResultVO menuIdInfo(@PathVariable("menuId") Long menuId) {
        return ResultVO.successJson(this.service.getById(menuId));
    }


    @PostMapping(value = "/add")
    @ApiOperation(value = "添加菜单", httpMethod = "POST", response = ResultVO.class)
    public ResultVO addMenu(@RequestBody MenuPO menu) {
        verifyForm(menu);
        this.service.save(menu);
        return ResultVO.successJson();
    }

    @PutMapping(value = "/edit/{menuId}")
    @ApiOperation(value = "修改菜单", httpMethod = "PUT", response = ResultVO.class)
    public ResultVO editMenu(@PathVariable("menuId") Long menuId, @RequestBody MenuPO menu) {
        verifyForm(menu);
        menu.setId(menuId);
        this.service.updateById(menu);
        return ResultVO.successJson();
    }


    @DeleteMapping(value = "/delete/{menuId}")
    @ApiOperation(value = "删除菜单", httpMethod = "DELETE", response = ResultVO.class)
    public ResultVO deleteRole(@PathVariable("menuId") Long menuId) {

        long count = this.service.count(Wrappers.<MenuPO>lambdaUpdate()
                .eq(MenuPO::getParentId, menuId)
                .eq(MenuPO::getDeleted, false)
        );
        if (count > 0) {
            return ResultVO.errorJson("请先删除子菜单或按钮");
        }
        this.service.update(Wrappers.<MenuPO>lambdaUpdate()
                .eq(MenuPO::getId, menuId)
                .set(MenuPO::getDeleted, true)
        );
        return ResultVO.successJson();
    }


    /**
     * 验证菜单
     *
     * @param menu
     */
    private void verifyForm(MenuPO menu) {
        if (menu == null) {
            throw new ParamInValidException("参数错误！");
        }
        if (StrUtil.isEmpty(menu.getMenuName())) {
            throw new ParamInValidException("菜单名称不能为空!");
        }
        if (menu.getType() == null) {
            throw new ParamInValidException("菜单类型不能为空");
        }
        if (menu.getParentId() == null) {
            throw new ParamInValidException("菜单父目录不能为空");
        }
        //菜单
        if (menu.getType() == MenuTypeEnum.MENU.getType()) {
            if (StrUtil.isBlank(menu.getUrl())) {
                throw new ParamInValidException("菜单URL不能为空");
            }
        }
        //上级菜单类型
        int parentType = MenuTypeEnum.CATEGORY.getType();
        if (menu.getParentId() != 0) {
            MenuPO parentMenu = this.service.getById(menu.getParentId());
            parentType = parentMenu.getType();
        }
        //目录、菜单
        if (menu.getType() == MenuTypeEnum.CATEGORY.getType() || menu.getType() == MenuTypeEnum.MENU.getType()) {
            if (parentType != MenuTypeEnum.CATEGORY.getType()) {
                throw new ParamInValidException("菜单类型的父菜单只能为目录类型");
            }
        }
        //按钮
        if (menu.getType() == MenuTypeEnum.BUTTON.getType()) {
            if (parentType != MenuTypeEnum.MENU.getType()) {
                throw new ParamInValidException("按钮类型的父菜单只能为菜单类型");
            }
            //按钮隐藏
            menu.setHidden(true);
        }
    }
}
