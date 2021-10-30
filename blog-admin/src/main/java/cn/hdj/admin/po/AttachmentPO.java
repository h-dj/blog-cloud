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
 * 附件表
 * </p>
 *
 * @author huangjiajian
 * @since 2021-10-30
 */
@Getter
@Setter
@TableName("t_attachment")
@ApiModel(value = "AttachmentPO对象", description = "附件表")
public class AttachmentPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("文件后缀")
    private String fileSuffix;

    private String fileSize;

    @ApiModelProperty("创建人")
    private Long createBy;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("附件链接")
    private String url;


}
