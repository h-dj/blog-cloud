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
 * 评论表
 * </p>
 *
 * @author huangjiajian
 * @since 2021-10-30
 */
@Getter
@Setter
@TableName("t_comment")
@ApiModel(value = "CommentPO对象", description = "评论表")
public class CommentPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("文章id")
    private Long articleId;

    @ApiModelProperty("父评论")
    private Long parentId;

    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("评论人的昵称")
    private String author;

    @ApiModelProperty("评论人的邮箱(不公开)")
    private String email;

    @ApiModelProperty("评论人的网站")
    private String authorUrl;

    @ApiModelProperty("点赞数")
    private Long likeCount;

    private String userAgent;

    @ApiModelProperty("ip 地址")
    private String ipAddress;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
