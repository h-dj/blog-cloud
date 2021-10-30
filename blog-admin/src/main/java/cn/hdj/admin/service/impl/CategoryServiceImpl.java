package cn.hdj.admin.service.impl;

import cn.hdj.admin.po.CategoryPO;
import cn.hdj.admin.mapper.CategoryMapper;
import cn.hdj.admin.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
