package cn.hdj.admin.domain.dto;

import cn.hdj.admin.po.TagPO;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author huangjiajian
 * @Date 2021/11/28 下午11:38
 */
@Data
public class ArticleFormDTO implements Serializable {

    private Long id;

    /**
     * 文章标题
     */
    @NotBlank(message = "标题不能未空")
    private String title;

    /**
     * 0:普通文章，1：关于页
     */
    private Integer type;
    /**
     * 概要
     */
    private String description;

    /**
     * 正文（markdown）
     */
    private String content;


    /**
     * 状态，0：新建，1：发布，2：删除
     */
    private Integer status;

    /**
     * 是否允许评论：0：不允许，1允许
     */
    private Boolean allowComment;

    /**
     * 是否允许订阅：0允许，1不允许
     */
    private Boolean allowFeed;

    /**
     * 是否推荐
     */
    private Boolean recommend;

    /**
     * 是否置顶
     */
    private Boolean top;

    /**
     * 标签
     */
    private List<TagPO> tagList;

    /**
     * 分类
     */
    private Long categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 标签
     */
    private String tags;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 发布时间
     */
    private LocalDateTime publishTime;

    /**
     * 自定义路径
     */
    private String slug;

    /**
     * 文章封面
     */
    private String cover;

    /**
     * 阅读量
     */
    private Integer readNum;

    /**
     * 评论数
     */
    private Integer commentNum;

    /**
     * 点赞数
     */
    private Integer likeNum;

}
