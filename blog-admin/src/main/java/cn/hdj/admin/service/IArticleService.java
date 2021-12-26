package cn.hdj.admin.service;

import cn.hdj.admin.domain.dto.ArticleFormDTO;
import cn.hdj.admin.domain.dto.ArticleSearchFormDTO;
import cn.hdj.admin.domain.vo.ArticleDetailVO;
import cn.hdj.admin.po.ArticlePO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 文章信息表 服务类
 * </p>
 *
 * @author huangjiajian
 * @since 2021-10-30
 */
public interface IArticleService extends IService<ArticlePO> {

    /**
     * 查询文章列表
     *
     * @param form
     * @return
     */
    IPage<ArticlePO> queryList(ArticleSearchFormDTO form);

    /**
     * 文章ID
     *
     * @param articleId
     * @return
     */
    ArticleDetailVO detail(Long articleId);

    /**
     * 保存文章
     *
     * @param form
     */
    void saveArticle(ArticleFormDTO form);

    /**
     * 编辑文章
     *
     * @param articleId
     * @param form
     */
    void editArticle(Long articleId, ArticleFormDTO form);

    /**
     * 删除文章
     *
     * @param articleIds
     */
    void deleteBatch(List<Long> articleIds);

    /**
     * 发布文章
     *
     * @param articleId
     */
    void publishArticle(Long articleId);
}
