package cn.hdj.common.config;

import cn.hutool.core.util.IdUtil;
import com.yomahub.tlog.id.TLogIdGenerator;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author huangjiajian
 * @Date 2021/11/17 下午9:12
 */
public class UUIDTLogIdGenerator extends TLogIdGenerator {
    @Override
    public String generateTraceId() {
        return IdUtil.fastSimpleUUID();
    }
}
