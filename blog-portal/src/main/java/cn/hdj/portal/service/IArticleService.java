package cn.hdj.portal.service;


import cn.hdj.common.domain.vo.ResultVO;
import cn.hdj.portal.domain.dto.ArticleSearchFormDTO;
import cn.hdj.portal.domain.vo.ArticleDetailVO;
import cn.hdj.portal.domain.vo.TimeLineVO;
import cn.hdj.portal.po.ArticlePO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 文章信息表 服务类
 * </p>
 *
 * @author huangjiajian
 */
public interface IArticleService extends IService<ArticlePO> {


    ArticleDetailVO detail(String slug);

    List<TimeLineVO> archive(String tag);

    ResultVO articleList(ArticleSearchFormDTO params);

}
