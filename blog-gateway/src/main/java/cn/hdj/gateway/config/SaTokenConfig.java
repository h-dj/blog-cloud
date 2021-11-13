package cn.hdj.gateway.config;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.reactor.context.SaReactorSyncHolder;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.hdj.common.consts.SysConst;
import cn.hdj.common.domain.vo.ResultVO;
import cn.hdj.common.enums.ResponseCodeEnum;
import cn.hdj.common.exception.BaseException;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.ContentType;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author huangjiajian
 * @Date 2021/11/8 下午10:22
 */
@Configuration
public class SaTokenConfig {

    @Autowired
    private StringRedisTemplate redisTemplate;

    // 注册 Sa-Token全局过滤器
    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter()
                // 拦截地址
                .addInclude("/**")
                // 开放地址
                .addExclude("/favicon.ico")
                // 鉴权方法：每次访问进入
                .setAuth(obj -> {
                    // 登录认证 -- 拦截所有路由，并排除 /api/admin/user/signIn 用于开放登录
                    SaRouter
                            .match("/**")
                            .notMatch("/api/auth/signIn")
                            .check(r -> StpUtil.checkLogin());

                    //动态检验权限
                    Map<Object, Object> permissionMap = redisTemplate.opsForHash()
                            .entries(SysConst.MENU_PERMISSON);


                    if (CollectionUtil.isEmpty(permissionMap)) {
                        throw new BaseException(ResponseCodeEnum.UNKNOWN.getMsg(), ResponseCodeEnum.UNKNOWN.getCode());
                    }
                    for (Map.Entry<Object, Object> entry : permissionMap.entrySet()) {
                        Object url = entry.getKey();
                        Object permissonCode = entry.getValue();

                        SaRouter.match(ObjectUtil.toString(url), () -> {
                            //超管不用检查，不用检查
                            if (SysConst.SUPER_ADMIN.equals(StpUtil.getLoginIdAsLong())) {
                                SaRouter.stop();
                            }
                            StpUtil.checkPermission(ObjectUtil.toString(permissonCode));
                        });
                    }
                })
                // 异常处理方法：每次setAuth函数出现异常时进入
                .setError(e -> {
                    //响应JSON
                    ServerWebExchange exchange = SaReactorSyncHolder.getContext();
                    exchange.getResponse().getHeaders().set("Content-Type", ContentType.JSON.getValue());
                    if (e instanceof NotLoginException) {
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return JSONUtil.toJsonStr(ResultVO.errorJson(e.getMessage(), ResponseCodeEnum.NO_AUTH.getCode()));
                    }
                    exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                    return JSONUtil.toJsonStr(ResultVO.errorJson(e.getMessage(), ResponseCodeEnum.FORBIDDEN.getCode()));

                })
                ;
    }
}
