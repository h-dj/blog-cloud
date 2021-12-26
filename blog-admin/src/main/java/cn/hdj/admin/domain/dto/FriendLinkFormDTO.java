package cn.hdj.admin.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author huangjiajian
 * @Date 2021/12/5 下午3:32
 */
@Data
@ApiModel(value = "FriendLinkFormDTO", description = "友链表单")
public class FriendLinkFormDTO {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "状态:0新建，1审核通过，2不通过")
    private Integer status;

    @ApiModelProperty(value = "描述")
    private String remark;

    @ApiModelProperty(value = "友链")
    private String url;

    @ApiModelProperty(value = "通知邮箱")
    private String email;

    @ApiModelProperty(value = "昵称")
    private String title;
}

