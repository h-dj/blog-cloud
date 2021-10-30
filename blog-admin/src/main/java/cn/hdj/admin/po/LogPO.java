package cn.hdj.admin.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 系统日志
 * </p>
 *
 * @author huangjiajian
 * @since 2021-10-30
 */
@Getter
@Setter
@TableName("t_log")
@ApiModel(value = "LogPO对象", description = "系统日志")
public class LogPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("操作标题")
    private String title;

    @ApiModelProperty("操作方法")
    private String method;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("执行时间")
    private Long time;

    @ApiModelProperty("请求参数")
    private String params;

    @ApiModelProperty("ip")
    private String ip;

    @ApiModelProperty("请求url")
    private String url;

    @ApiModelProperty("ip地址")
    private String ipAddress;

    @ApiModelProperty("操作时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("异常")
    private String exception;

    @ApiModelProperty("用户代理")
    private String userAgent;

    @ApiModelProperty("等级")
    private String level;

    @ApiModelProperty("浏览器")
    private String browser;

    @ApiModelProperty("操作系统")
    private String os;


}
