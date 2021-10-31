package cn.hdj.admin.controller;

import cn.hdj.common.domain.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author huangjiajian
 * @Date 2021/10/31 下午3:36
 */
@Profile({"dev","test"})
@RestController
@RequestMapping("/api/admin/configs")
public class ConfigController {

    @Autowired
    private Environment environment;
    
    @GetMapping(value="/query")
    public ResultVO queryByName(String configName){
        String property = environment.getProperty(configName);
        return ResultVO.successJson(property);
    }
}
