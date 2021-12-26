package cn.hdj.admin.service;

import cn.hdj.admin.po.TagPO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author huangjiajian
 * @since 2021-10-30
 */
public interface ITagService extends IService<TagPO> {

    /**
     * 删除标签
     *
     * @param ids
     */
    void delete(List<Long> ids);

    /**
     * 获取文章标签
     *
     * @param articleId
     * @return
     */
    List<TagPO> getTagListByArticleId(Long articleId);

    /**
     * 保存文章标签
     *
     * @param tagList
     * @param articleId
     */
    void saveTagLink(List<TagPO> tagList, Long articleId);

    /**
     * 删除文章标签关联
     *
     * @param articleIds
     */
    void removeTagLink(List<Long> articleIds);

    /**
     * 添加保存标签
     *
     * @param tagPO
     */
    void saveAndUpdateById(TagPO tagPO);
}
