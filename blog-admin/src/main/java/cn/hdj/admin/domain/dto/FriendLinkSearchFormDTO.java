package cn.hdj.admin.domain.dto;

import cn.hdj.common.domain.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author huangjiajian
 * @Date 2021/12/5 下午3:34
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "友链搜索表单")
public class FriendLinkSearchFormDTO extends BaseDTO {

    @ApiModelProperty(value = "状态:0新建，1审核通过，2不通过")
    private Integer status;


}