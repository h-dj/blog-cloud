package cn.hdj.admin.config;

import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.hdj.admin.domain.dto.RoleMenuPermissionDTO;
import cn.hdj.admin.service.IMenuService;
import cn.hdj.common.consts.SysConst;
import cn.hutool.core.collection.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author huangjiajian
 * @Date 2021/10/31 下午10:34
 */
//@Configuration
public class SaTokenConfig implements WebMvcConfigurer {


    @Autowired
    private IMenuService menuService;

    /**
     * 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册路由拦截器，自定义认证规则
        registry.addInterceptor(new SaRouteInterceptor((req, res, handler) -> {

            // 登录认证 -- 拦截所有路由，并排除 /api/admin/user/signIn 用于开放登录
            SaRouter
                    .match("/**")
                    .notMatch("/api/admin/user/signIn")
                    .check(r -> StpUtil.checkLogin());

            //动态检验权限
            List<RoleMenuPermissionDTO> list = menuService.queryDynamicPermissionList();
            if (CollectionUtil.isNotEmpty(list)) {
                for (RoleMenuPermissionDTO entity : list) {
                    SaRouter.match(entity.getRequestUrl(), r -> {
                        //超管不用检查，不用检查
                        if (SysConst.SUPER_ADMIN.equals(StpUtil.getLoginIdAsLong())) {
                            SaRouter.stop();
                        }
                        StpUtil.checkPermission(entity.getPermissionCode());
                    });
                }
            }

        })).addPathPatterns("/**");
    }
}
