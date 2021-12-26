package cn.hdj.portal.service;


import cn.hdj.portal.po.TagPO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

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
     * 通过文章ID 获取标签列表
     *
     * @param articleId
     * @return
     */
    List<TagPO> getTagListByArticleId(Long articleId);

    /**
     * 按标签名称分组统计
     *
     * @return
     */
    List<Map<String, Integer>> groupCount();
}
