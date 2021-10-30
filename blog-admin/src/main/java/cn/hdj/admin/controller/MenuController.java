package cn.hdj.admin.controller;


import cn.hdj.admin.service.IMenuService;
import cn.hdj.common.domain.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return ResultVO.successJson(service.listForUser(null));
    }


}
