package cn.hdj.admin.domain.dto;

import cn.hdj.common.domain.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author huangjiajian
 * @Version 1.0
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "RoleSearchForm", description = "角色搜索表单")
public class RoleSearchFormDTO extends BaseDTO {
    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    private String roleName;


}
