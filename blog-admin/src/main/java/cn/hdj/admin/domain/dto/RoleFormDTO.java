package cn.hdj.admin.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * @author huangjiajian
 * @Version 1.0
 * @Description: 角色form
 */
@Data
@ApiModel(value = "RoleForm", description = "角色表单")
public class RoleFormDTO {

    /**
     * 角色名称
     */
    @NotEmpty
    private String roleName;

    /**
     * 角色编码
     */
    @NotEmpty
    private String roleCode;
    /**
     * 描述
     */
    private String remark;

    /**
     * 状态：1启用，0禁用
     */
    private Boolean enable;

    /**
     * 菜单Id
     */
    @ApiModelProperty("菜单Id")
    private Set<Long> menuIds;
}
