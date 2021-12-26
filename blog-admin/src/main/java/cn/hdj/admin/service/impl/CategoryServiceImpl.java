package cn.hdj.admin.service.impl;

import cn.hdj.admin.po.ArticlePO;
import cn.hdj.admin.po.CategoryPO;
import cn.hdj.admin.mapper.CategoryMapper;
import cn.hdj.admin.service.IArticleService;
import cn.hdj.admin.service.ICategoryService;
import cn.hdj.common.enums.ResponseCodeEnum;
import cn.hdj.common.exception.BaseException;
import cn.hdj.common.exception.RecordRepeatException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 分类表 服务实现类
 * </p>
 *
 * @author huangjiajian
 * @since 2021-10-30
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, CategoryPO> implements ICategoryService {

    @Autowired
    private IArticleService articleService;

    @Override
    public void delete(List<Long> ids) {
        if (ids != null && ids.size() > 0) {
            long count = articleService.count(Wrappers.<ArticlePO>lambdaQuery()
                    .in(ArticlePO::getCategoryId, ids)
            );
            if (count > 0) {
                throw new BaseException("所选分类下有文章，无法删除！", ResponseCodeEnum.ERROR.getCode());
            }
            //删除分类
            this.removeByIds(ids);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateById(CategoryPO categoryPO) {
        LambdaQueryWrapper<CategoryPO> wrapper = Wrappers.<CategoryPO>lambdaQuery()
                .eq(CategoryPO::getCategoryName, categoryPO.getCategoryName())
                .ne(categoryPO.getId() != null, CategoryPO::getId, categoryPO.getId());

        long count = this.count(wrapper);
        if (count > 0) {
            throw new RecordRepeatException("该分类已存在! " + categoryPO.getCategoryName());
        }
        if (categoryPO.getId() != null) {
            this.updateById(categoryPO);
        } else {
            this.save(categoryPO);
        }
    }
}
