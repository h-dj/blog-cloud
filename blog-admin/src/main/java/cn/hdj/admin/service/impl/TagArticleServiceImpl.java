package cn.hdj.admin.service.impl;

import cn.hdj.admin.po.TagArticlePO;
import cn.hdj.admin.mapper.TagArticleMapper;
import cn.hdj.admin.service.ITagArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章标签表 服务实现类
 * </p>
 *
 * @author huangjiajian
 * @since 2021-10-30
 */
@Service
public class TagArticleServiceImpl extends ServiceImpl<TagArticleMapper, TagArticlePO> implements ITagArticleService {

}
