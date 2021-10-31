package cn.hdj.admin.domain.vo;

import cn.hdj.admin.po.RolePO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author huangjiajian
 * @Date 2021/10/31 上午12:59
 */
@Getter
@Setter
@ApiModel
public class RoleDetailVO extends RolePO {

    /**
     * 菜单Id
     */
    @ApiModelProperty("菜单Id")
    private Set<Long> menuIds;
}
