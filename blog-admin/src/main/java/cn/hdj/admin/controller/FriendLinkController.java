package cn.hdj.admin.controller;


import cn.hdj.admin.domain.dto.FriendLinkFormDTO;
import cn.hdj.admin.domain.dto.FriendLinkSearchFormDTO;
import cn.hdj.admin.po.FriendLinkPO;
import cn.hdj.admin.service.IFriendLinkService;
import cn.hdj.common.domain.vo.PageVO;
import cn.hdj.common.domain.vo.ResultVO;
import cn.hdj.common.validator.ValidatorUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
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
 *  前端控制器
 * </p>
 *
 * @author huangjiajian
 * @since 2021-10-30
 */
@RestController
@RequestMapping("/api/admin/friendLinks")
@Api(value = "友链")
public class FriendLinkController {

    @Autowired
    private IFriendLinkService friendLinkService;



    @GetMapping(value = "/list")
    @ApiOperation(value = "友链列表", httpMethod = "GET", response = ResultVO.class)
    public ResultVO list(@ApiParam FriendLinkSearchFormDTO roleForm) {
        IPage<FriendLinkPO> page = friendLinkService.page(roleForm.getIPage(), Wrappers.<FriendLinkPO>lambdaQuery()
                .eq(roleForm.getStatus() != null, FriendLinkPO::getStatus, roleForm.getStatus())
        );
        return ResultVO.successJson(PageVO.build(page));
    }

    @PostMapping(value = "/add")
    @ApiOperation(value = "友链列表", httpMethod = "POST", response = ResultVO.class)
    public ResultVO add(@ApiParam @RequestBody FriendLinkFormDTO form) {
        ValidatorUtils.validateEntity(form);
        FriendLinkPO friendLinkDO = new FriendLinkPO();
        BeanUtil.copyProperties(form, friendLinkDO);
        friendLinkService.save(friendLinkDO);
        return ResultVO.successJson();
    }


    @PutMapping("/examine")
    @ApiOperation(value = "审核友链", httpMethod = "PUT", response = ResultVO.class)
    public ResultVO examine(@ApiParam @RequestBody FriendLinkFormDTO form) {
        FriendLinkPO friendLinkDO = new FriendLinkPO();
        friendLinkDO.setId(form.getId());
        friendLinkDO.setStatus(form.getStatus());
        friendLinkService.updateById(friendLinkDO);
        return ResultVO.successJson();
    }


    @DeleteMapping("/delete")
    @ApiOperation(value = "删除链接", httpMethod = "DELETE", response = ResultVO.class)
    public ResultVO delete(@RequestBody List<Long> ids) {
        if (CollectionUtil.isNotEmpty(ids)) {
            friendLinkService.removeByIds(ids);
        }
        return ResultVO.successJson();
    }
}

