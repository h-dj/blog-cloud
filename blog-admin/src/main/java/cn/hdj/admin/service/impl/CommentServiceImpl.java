package cn.hdj.admin.service.impl;

import cn.hdj.admin.po.CommentPO;
import cn.hdj.admin.mapper.CommentMapper;
import cn.hdj.admin.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author huangjiajian
 * @since 2021-10-30
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, CommentPO> implements ICommentService {

}
