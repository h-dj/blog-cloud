package cn.hdj.admin.service.impl;

import cn.hdj.admin.po.LogPO;
import cn.hdj.admin.mapper.LogMapper;
import cn.hdj.admin.service.ILogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author huangjiajian
 * @since 2021-10-30
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, LogPO> implements ILogService {

}
