package cn.hdj.admin.service.impl;

import cn.hdj.admin.po.TagPO;
import cn.hdj.admin.mapper.TagMapper;
import cn.hdj.admin.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
