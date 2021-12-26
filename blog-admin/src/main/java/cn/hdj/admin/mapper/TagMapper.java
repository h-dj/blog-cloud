package cn.hdj.admin.mapper;

import cn.hdj.admin.po.TagPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 标签表 Mapper 接口
 * </p>
 *
 * @author huangjiajian
 * @since 2021-10-30
 */
public interface TagMapper extends BaseMapper<TagPO> {

    /**
     * 获取文章标签
     *
     * @param articleId
     * @return
     */
    List<TagPO> getTagListByArticleId(@Param("articleId") Long articleId);
}
