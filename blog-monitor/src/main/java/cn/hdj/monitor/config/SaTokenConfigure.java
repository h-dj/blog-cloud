package cn.hdj.monitor.config;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.basic.SaBasicUtil;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.id.SaIdUtil;
import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.dev33.satoken.util.SaTokenConsts;
import cn.hutool.core.util.StrUtil;
import org.aopalliance.intercept.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import java.util.Arrays;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author huangjiajian
 * @Date 2021/11/10 下午11:32
 */
@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {

    @Autowired
    Environment environment;

    @Bean
    public FilterRegistrationBean loginFilter() {
        String username = this.environment.getProperty("spring.boot.admin.client.username");
        String password = this.environment.getProperty("spring.boot.admin.client.password");


        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new LoginFilter());
        filterRegistrationBean.setOrder(0);
        filterRegistrationBean.setName("loginFilter");
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/login"));

        filterRegistrationBean.addInitParameter("username", username);
        filterRegistrationBean.addInitParameter("password", password);
        return filterRegistrationBean;
    }


    @Bean
    public SaServletFilter saServletFilter() {
        String username = this.environment.getProperty("spring.boot.admin.client.username");
        String password = this.environment.getProperty("spring.boot.admin.client.password");
        final String account = StrUtil.format("{}:{}", username, password);

        return new SaServletFilter()
                .addInclude("/**")
                //排除/login 用于开放登录
                .addExclude("/login")
                //排除静态文件
                .addExclude("/assets/**")
                .setAuth(obj -> {

                    //公共放行处理
                    SaRouter
                            //排除方法
                            .matchMethod(SaHttpMethod.OPTIONS.name())
                            .stop();

                    // 登录认证
                    String authorization = SaHolder.getRequest().getHeader("Authorization");
                    if (authorization != null && StrUtil.startWithIgnoreCase(authorization, "Basic")) {
                        //basic 认证
                        SaBasicUtil.check(account);
                    } else {
                        StpUtil.checkLogin();
                    }
                })
                .setError(e -> {
                    e.printStackTrace();
                    if (!SaHolder.getRequest().isAjax()) {
                        SaHolder.getResponse().redirect("/login");
                        return null;
                    }
                    return SaResult.error(e.getMessage());
                });
    }
}
