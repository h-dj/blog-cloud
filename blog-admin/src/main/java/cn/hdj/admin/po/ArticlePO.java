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
 * 文章信息表
 * </p>
 *
 * @author huangjiajian
 * @since 2021-10-30
 */
@Getter
@Setter
@TableName("t_article")
@ApiModel(value = "ArticlePO对象", description = "文章信息表")
public class ArticlePO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("文章ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("文章标题")
    private String title;

    @ApiModelProperty("概要")
    private String description;

    @ApiModelProperty("正文（markdown）")
    private String content;

    @ApiModelProperty("标签")
    private String tags;

    @ApiModelProperty("作者")
    private Long authorId;

    @ApiModelProperty("是否允许评论：0：不允许，1允许")
    private Boolean allowComment;

    @ApiModelProperty("作者名称")
    private String authorName;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("状态，0：草稿，1：发布，2：删除")
    private Integer status;

    @ApiModelProperty("0:普通文章，1：简历，2关于页")
    private Integer type;

    @ApiModelProperty("发布时间")
    private LocalDateTime publishTime;

    @ApiModelProperty("点赞数")
    private Integer likeNum;

    @ApiModelProperty("评论数")
    private Integer commentNum;

    @ApiModelProperty("修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("阅读量")
    private Integer readNum;

    @ApiModelProperty("是否推荐")
    private Boolean recommend;

    @ApiModelProperty("是否允许订阅：0不允许，不允许")
    private Boolean allowFeed;

    @ApiModelProperty("分类id")
    private Long categoryId;

    @ApiModelProperty("置顶")
    private Boolean top;

    @ApiModelProperty("封面")
    private String cover;

    @ApiModelProperty("文章自定义路径")
    private String slug;


}
