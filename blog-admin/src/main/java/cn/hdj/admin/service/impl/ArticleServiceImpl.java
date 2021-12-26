package cn.hdj.admin.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hdj.admin.domain.dto.ArticleFormDTO;
import cn.hdj.admin.domain.dto.ArticleSearchFormDTO;
import cn.hdj.admin.domain.vo.ArticleDetailVO;
import cn.hdj.admin.mapper.ArticleMapper;
import cn.hdj.admin.po.ArticlePO;
import cn.hdj.admin.po.CategoryPO;
import cn.hdj.admin.service.IArticleService;
import cn.hdj.admin.service.ICategoryService;
import cn.hdj.admin.service.ITagService;
import cn.hdj.common.consts.SysConst;
import cn.hdj.common.domain.dto.UserDetailDTO;
import cn.hdj.common.enums.ResponseCodeEnum;
import cn.hdj.common.exception.BaseException;
import cn.hdj.common.exception.RecordRepeatException;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public IPage<ArticlePO> queryList(ArticleSearchFormDTO form) {
        IPage<ArticlePO> page = this.page(
                form.getIPage(),
                Wrappers.<ArticlePO>lambdaQuery()
                        .select(ArticlePO::getId,
                                ArticlePO::getTitle,
                                ArticlePO::getAuthorName,
                                ArticlePO::getReadNum,
                                ArticlePO::getLikeNum,
                                ArticlePO::getCommentNum,
                                ArticlePO::getAllowComment,
                                ArticlePO::getAllowFeed,
                                ArticlePO::getRecommend,
                                ArticlePO::getTags,
                                ArticlePO::getStatus,
                                ArticlePO::getPublishTime,
                                ArticlePO::getCreateTime
                        )
                        .ne(ArticlePO::getStatus, 2)
                        .like(StrUtil.isNotEmpty(form.getTitle()), ArticlePO::getTitle, form.getTitle())
        );
        return page;
    }

    @Override
    public ArticleDetailVO detail(Long articleId) {
        ArticlePO articlePO = this.getBaseMapper().selectOne(Wrappers.<ArticlePO>lambdaQuery()
                .eq(ArticlePO::getId, articleId)
                .ne(ArticlePO::getStatus, 2)

        );
        if (articlePO == null) {
            throw new BaseException("文章不存在", ResponseCodeEnum.ERROR.getCode());
        }

        ArticleDetailVO articleDetailVO = BeanUtil.copyProperties(articlePO, ArticleDetailVO.class);
        articleDetailVO.setTagList(this.tagService.getTagListByArticleId(articleDetailVO.getId()));
        CategoryPO categoryDO = this.categoryService.getById(articleDetailVO.getCategoryId());
        if (categoryDO != null) {
            articleDetailVO.setCategoryName(categoryDO.getCategoryName());
        }
        return articleDetailVO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveArticle(ArticleFormDTO form) {
        Object loginId = StpUtil.getLoginId();
        UserDetailDTO user = StpUtil.getSessionByLoginId(StpUtil.getLoginId())
                .getModel(SysConst.LOGIN_USER_PREFIX + loginId, UserDetailDTO.class);


        if (StrUtil.isNotEmpty(form.getSlug())) {
            long count = this.count(Wrappers.<ArticlePO>lambdaQuery()
                    .eq(ArticlePO::getSlug, form.getSlug()));

            if (count > 0) {
                throw new RecordRepeatException("该slug 已存在！ " + form.getSlug());
            }
        }

        ArticlePO articlePO = BeanUtil.copyProperties(form, ArticlePO.class);

        Optional.ofNullable(form.getTagList())
                .ifPresent(list -> {
                    String tags = list.stream()
                            .map(v -> v.getTagName())
                            .collect(Collectors.joining(","));
                    articlePO.setTags(tags);
                });
        articlePO.setAuthorId(user.getId());
        articlePO.setAuthorName(user.getUserName());
        if (articlePO.getStatus() == 1) {
            articlePO.setPublishTime(LocalDateTime.now());
        }
        //保存文章
        baseMapper.insert(articlePO);
        //保存标签
        tagService.saveTagLink(form.getTagList(), articlePO.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void editArticle(Long articleId, ArticleFormDTO form) {
        long count = this.count(Wrappers.<ArticlePO>lambdaQuery()
                .eq(ArticlePO::getSlug, form.getSlug())
                .ne(ArticlePO::getId, articleId)
        );
        if (count > 0) {
            throw new RecordRepeatException("该slug 已存在！");
        }

        ArticlePO articlePO = BeanUtil.copyProperties(form, ArticlePO.class);
        articlePO.setId(articleId);
        Optional.ofNullable(form.getTagList())
                .ifPresent(list -> {
                    String tags = list.stream().map(v -> v.getTagName())
                            .collect(Collectors.joining(","));
                    articlePO.setTags(tags);
                });
        if (articlePO.getStatus() == 1 && articlePO.getPublishTime() == null) {
            articlePO.setPublishTime(LocalDateTime.now());
        }
        this.getBaseMapper().updateById(articlePO);
        //删除标签关联
        this.tagService.removeTagLink(ListUtil.of(articleId));
        //保存标签
        this.tagService.saveTagLink(form.getTagList(), articleId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatch(List<Long> articleIds) {
        if (CollectionUtil.isNotEmpty(articleIds)) {
            //删除文章管理的标签
            tagService.removeTagLink(articleIds);

            this.update(Wrappers.<ArticlePO>lambdaUpdate()
                    .set(ArticlePO::getStatus, 2)
                    .in(ArticlePO::getId, articleIds)
            );
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void publishArticle(Long articleId) {
        ArticlePO articleDO = this.getBaseMapper().selectById(articleId);
        if (articleDO == null || articleDO.getStatus().equals(2)) {
            throw new BaseException("文章不存在!", ResponseCodeEnum.ERROR.getCode());
        }
        if (articleDO.getStatus().equals(1)) {
            throw new BaseException("文章已发布!", ResponseCodeEnum.ERROR.getCode());
        }
        articleDO.setStatus(1);
        this.getBaseMapper().updateById(articleDO);
    }
}
