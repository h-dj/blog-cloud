package cn.hdj.portal.domain.vo;


import cn.hdj.portal.po.ArticlePO;
import cn.hdj.portal.po.TagPO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author huangjiajian
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
