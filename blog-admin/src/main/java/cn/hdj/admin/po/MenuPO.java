package cn.hdj.admin.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统菜单
 * </p>
 *
 * @author huangjiajian
 * @since 2021-10-30
 */
@Getter
@Setter
@TableName("t_menu")
@ApiModel(value = "MenuPO对象", description = "系统菜单")
public class MenuPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("资源ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("资源名称")
    private String menuName;

    @ApiModelProperty("菜单类型：0目录，1菜单，2按钮")
    private Integer type;

    @ApiModelProperty("parentId")
    private Long parentId;

    @ApiModelProperty("父菜单名称")
    private String parentName;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("权限标识")
    private String permission;

    @ApiModelProperty("前端路由")
    private String url;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("是否删除")
    private Boolean deleted;

    @ApiModelProperty("菜单图标")
    private String icon;

    @ApiModelProperty("路由组件")
    private String component;

    @ApiModelProperty("是否隐藏")
    private Boolean hidden;

    @ApiModelProperty("请求url")
    private String requestUrl;


}
