package cn.hdj.admin.service.impl;

import cn.hdj.admin.po.ArticlePO;
import cn.hdj.admin.mapper.ArticleMapper;
import cn.hdj.admin.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
