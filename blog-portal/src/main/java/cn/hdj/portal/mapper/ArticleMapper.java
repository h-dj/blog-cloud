package cn.hdj.portal.mapper;


import cn.hdj.portal.domain.vo.TimeLineVO;
import cn.hdj.portal.domain.vo.TimelinePostVO;
import cn.hdj.portal.po.ArticlePO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 文章信息表 Mapper 接口
 * </p>
 *
 * @author huangjiajian
 * @since 2021-10-30
 */
public interface ArticleMapper extends BaseMapper<ArticlePO> {

    List<TimeLineVO> archiveTimeLine(@Param("tag") String tag);

    List<TimelinePostVO> listTimelinePost(@Param("year") Integer year, @Param("month")int month, String tag);
}
