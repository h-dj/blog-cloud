package cn.hdj.portal.service.impl;


import cn.hdj.portal.mapper.TagMapper;
import cn.hdj.portal.po.TagPO;
import cn.hdj.portal.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author huangjiajian
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, TagPO> implements ITagService {
    @Override
    public List<TagPO> getTagListByArticleId(Long articleId) {
        return this.baseMapper.getTagListByArticleId(articleId);
    }

    @Override
    public List<Map<String, Integer>> groupCount() {
        return this.baseMapper.groupCount();
    }
}
