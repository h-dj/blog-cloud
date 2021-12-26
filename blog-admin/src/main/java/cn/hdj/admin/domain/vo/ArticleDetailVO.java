package cn.hdj.admin.domain.vo;

import cn.hdj.admin.po.ArticlePO;
import cn.hdj.admin.po.TagPO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author huangjiajian
 * @Date 2021/11/28 下午11:43
 */
@Data
public class ArticleDetailVO extends ArticlePO implements Serializable {
	private static final long serialVersionUID = 1L;

    /**
     * 标签
     */
    private List<TagPO> tagList;

    /**
     * 分类名称
     */
    private String categoryName;
}
