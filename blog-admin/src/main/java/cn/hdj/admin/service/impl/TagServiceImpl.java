package cn.hdj.admin.service.impl;

import cn.hdj.admin.mapper.TagMapper;
import cn.hdj.admin.po.TagArticlePO;
import cn.hdj.admin.po.TagPO;
import cn.hdj.admin.service.ITagArticleService;
import cn.hdj.admin.service.ITagService;
import cn.hdj.common.enums.ResponseCodeEnum;
import cn.hdj.common.exception.BaseException;
import cn.hdj.common.exception.RecordRepeatException;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author huangjiajian
 * @since 2021-10-30
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, TagPO> implements ITagService {


    @Autowired
    private ITagArticleService tagArticleService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<Long> ids) {
        if (ids != null && ids.size() > 0) {
            List<TagArticlePO> list = tagArticleService.list(Wrappers.<TagArticlePO>lambdaQuery()
                    .in(TagArticlePO::getTagId, ids)
            );
            if (CollectionUtil.isNotEmpty(list)) {
                throw new BaseException("所选标签有有关联文章，无法删除！", ResponseCodeEnum.ERROR.getCode(), false);
            }
            //删除标签
            baseMapper.deleteBatchIds(ids);
        }
    }

    @Override
    public List<TagPO> getTagListByArticleId(Long articleId) {
        return this.getBaseMapper().getTagListByArticleId(articleId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveTagLink(List<TagPO> tagList, Long articleId) {
        Assert.notNull(articleId, "Article id must not be null");

        Optional.ofNullable(tagList)
                .ifPresent(tags -> {
                    List<TagArticlePO> tagLinkList = new ArrayList<>(tags.size());
                    tags.forEach(tagDO -> {
                        Long id = tagDO.getId();
                        TagArticlePO tagArticleDO = new TagArticlePO();
                        if (id == null) {
                            baseMapper.insert(tagDO);
                        }
                        tagArticleDO.setTagId(tagDO.getId());
                        tagArticleDO.setArticleId(articleId);
                        tagLinkList.add(tagArticleDO);
                    });
                    tagArticleService.saveBatch(tagLinkList);
                });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeTagLink(List<Long> articleIds) {
        if (CollectionUtil.isNotEmpty(articleIds)) {
            tagArticleService.remove(Wrappers.<TagArticlePO>lambdaQuery()
                    .in(TagArticlePO::getArticleId, articleIds)
            );
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveAndUpdateById(TagPO tagPO) {

        LambdaQueryWrapper<TagPO> queryWrapper = Wrappers.<TagPO>lambdaQuery()
                .eq(TagPO::getTagName, tagPO.getTagName())
                .ne(tagPO.getId() != null, TagPO::getId, tagPO.getId());

        long count = this.count(queryWrapper);

        if (count > 0) {
            throw new RecordRepeatException("标签重复 :" + tagPO.getTagName());
        }

        if (tagPO.getId() != null) {
            this.updateById(tagPO);
        } else {
            this.save(tagPO);
        }
    }
}
