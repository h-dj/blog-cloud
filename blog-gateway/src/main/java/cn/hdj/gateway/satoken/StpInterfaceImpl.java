package cn.hdj.gateway.satoken;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpInterface;

import cn.dev33.satoken.stp.StpUtil;
import cn.hdj.common.consts.SysConst;
import cn.hdj.common.domain.dto.RoleMenuPermissionDTO;
import cn.hdj.common.domain.vo.ResultVO;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.convert.Converter;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 自定义权限验证接口扩展
 * @Author huangjiajian
 * @Date 2021/10/31 下午10:24
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 获取当前会话的 User-Session
        SaSession session = StpUtil.getSessionByLoginId(loginId);
        //获取当前用户的权限
        Object o = session.get(SaSession.PERMISSION_LIST);
        if (ObjectUtil.isNotNull(o)) {
            return Convert.toList(String.class, o);
        }
        return Collections.emptyList();
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return null;
    }
}
