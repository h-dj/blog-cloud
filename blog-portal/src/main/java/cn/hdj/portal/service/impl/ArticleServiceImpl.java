package cn.hdj.portal.service.impl;


import cn.hdj.common.domain.vo.PageVO;
import cn.hdj.common.domain.vo.ResultVO;
import cn.hdj.portal.domain.dto.ArticleSearchFormDTO;
import cn.hdj.portal.domain.vo.ArticleDetailVO;
import cn.hdj.portal.domain.vo.TimeLineVO;
import cn.hdj.portal.domain.vo.TimelineMonthVO;
import cn.hdj.portal.domain.vo.TimelinePostVO;
import cn.hdj.portal.mapper.ArticleMapper;
import cn.hdj.portal.po.ArticlePO;
import cn.hdj.portal.po.CategoryPO;
import cn.hdj.portal.service.IArticleService;
import cn.hdj.portal.service.ICategoryService;
import cn.hdj.portal.service.ITagService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * <p>
 * 文章信息表 服务实现类
 * </p>
 *
 * @author huangjiajian
 * @since 2021-10-30
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, ArticlePO> implements IArticleService {

    @Autowired
    private ITagService tagService;
    @Autowired
    private ICategoryService categoryService;

    @Override
    public ArticleDetailVO detail(String slug) {
        ArticlePO articleDO = this.getBaseMapper().selectOne(Wrappers.<ArticlePO>lambdaQuery()
                .eq(ArticlePO::getSlug, slug)
                .or()
                .eq(ArticlePO::getId, slug)
        );
        ArticleDetailVO articleDetailVO = BeanUtil.copyProperties(articleDO, ArticleDetailVO.class);

        articleDetailVO.setTagList(this.tagService.getTagListByArticleId(articleDO.getId()));
        CategoryPO categoryDO = categoryService.getById(articleDO.getCategoryId());
        if (categoryDO != null) {
            articleDetailVO.setCategoryName(categoryDO.getCategoryName());
        }
        return articleDetailVO;
    }

    @Override
    public List<TimeLineVO> archive(String tag) {
        //查出按时间统计文章
        List<TimeLineVO> timeLineList = this.baseMapper.archiveTimeLine(tag);
        for (TimeLineVO timeline : timeLineList) {
            List<TimelineMonthVO> timelineMonthList = new ArrayList<>();
            for (int i = Calendar.UNDECIMBER; i > 0; i--) {
                List<TimelinePostVO> postList = baseMapper.listTimelinePost(timeline.getYear(), i, tag);
                if (CollectionUtil.isEmpty(postList)) {
                    continue;
                }
                TimelineMonthVO month = new TimelineMonthVO();
                month.setCount(postList.size());
                month.setMonth(i);
                month.setPosts(postList);
                timelineMonthList.add(month);
            }
            timeline.setMonths(timelineMonthList);
        }
        return timeLineList;
    }

    @Override
    public ResultVO articleList(ArticleSearchFormDTO form) {
        Page<ArticlePO> iPage = (Page<ArticlePO>) form.<ArticlePO>getIPage();
        iPage.setOrders(form.getOrderList());
        IPage<ArticlePO> page = this.page(
                iPage,
                Wrappers.<ArticlePO>lambdaQuery()
                        .select(ArticlePO::getId,
                                ArticlePO::getTitle,
                                ArticlePO::getAuthorName,
                                ArticlePO::getReadNum,
                                ArticlePO::getLikeNum,
                                ArticlePO::getRecommend,
                                ArticlePO::getTags,
                                ArticlePO::getPublishTime,
                                ArticlePO::getDescription,
                                ArticlePO::getCover,
                                ArticlePO::getSlug,
                                ArticlePO::getTop
                        )
                        .eq(ArticlePO::getStatus, 1)
                        .eq(form.getCategoryId() != null, ArticlePO::getCategoryId, form.getCategoryId())

        );
        return ResultVO.successJson(PageVO.build(page));
    }
}
