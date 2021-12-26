package cn.hdj.portal.controller;


import cn.hdj.common.domain.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 友链 前端控制器
 * </p>
 *
 * @author hdj
 * @since 2020-01-14
 */
@RestController
@RequestMapping("/api/friendLinks")
@Api(value = "(前台)友链")
public class FriendLinkFrontController {


    @GetMapping(value = "/list")
    @ApiOperation(value = "友链列表", httpMethod = "GET", response = ResultVO.class)
    public ResultVO list() {

        return ResultVO.successJson(null);
    }


    @PostMapping(value = "/add")
    @ApiOperation(value = "申请友链", httpMethod = "POST", response = ResultVO.class)
    public ResultVO add() {

        return ResultVO.successJson();
    }

}
