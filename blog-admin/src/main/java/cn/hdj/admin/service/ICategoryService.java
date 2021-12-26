package cn.hdj.admin.service;

import cn.hdj.admin.po.CategoryPO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 分类表 服务类
 * </p>
 *
 * @author huangjiajian
 * @since 2021-10-30
 */
public interface ICategoryService extends IService<CategoryPO> {

    /**
     *dsdsddddddddxgdsg
     * @param ids
     */
    void delete(List<Long> ids);

    /**
     * 保持或更新
     * @param categoryForm
     */
    void saveOrUpdateById(CategoryPO categoryForm);

}
