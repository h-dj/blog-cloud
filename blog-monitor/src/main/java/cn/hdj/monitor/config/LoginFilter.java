package cn.hdj.monitor.config;

import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author huangjiajian
 * @Date 2021/11/10 下午11:44
 */
public class LoginFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String username = this.getFilterConfig().getInitParameter("username");
        String password = this.getFilterConfig().getInitParameter("password");

        boolean isPostMethod = ServletUtil.isPostMethod(request);
        Map<String, String> paramMap = ServletUtil.getParamMap(request);
        if (isPostMethod) {

            /**
             * username:
             * password:
             * remember-me: on
             */
            String usernameParam = paramMap.get("username");
            String passwordParam = paramMap.get("password");
            String rememberMe = paramMap.get("remember-me");
            if (StrUtil.equals(username, usernameParam) && StrUtil.equals(password, passwordParam)) {
                if (StrUtil.equals(rememberMe, "on")) {
                    StpUtil.login(username);
                } else {
                    StpUtil.login(username, false);
                }
                response.sendRedirect("/");
            } else {
                response.sendRedirect("/login?error");
            }
            return;
        }
        filterChain.doFilter(request, response);
    }
}
