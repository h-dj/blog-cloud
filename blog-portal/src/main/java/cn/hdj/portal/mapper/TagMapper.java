package cn.hdj.portal.mapper;


import cn.hdj.portal.po.TagPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标签表 Mapper 接口
 * </p>
 *
 * @author huangjiajian
 */
public interface TagMapper extends BaseMapper<TagPO> {

    /**
     * 获取文章标签
     *
     * @param articleId
     * @return
     */
    List<TagPO> getTagListByArticleId(@Param("articleId") Long articleId);

    List<Map<String, Integer>> groupCount();

}
