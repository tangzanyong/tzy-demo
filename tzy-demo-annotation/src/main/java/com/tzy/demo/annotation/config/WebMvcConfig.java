package com.tzy.demo.annotation.config;

import com.tzy.demo.annotation.interceptor.AccessInterceptor;
import com.tzy.demo.annotation.login.LoginUserResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author tangzanyong
 * @description @TODO
 * @date 2020/5/15
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Lazy
    @Autowired
    private LoginUserResolver loginUserResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserResolver);
    }

    //在实现spring的WebMvcConfigurer配置类中添加拦截器,并把拦截器添加到拦截器链中
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*
        //把AccessInterceptor拦截器添加到拦截器链中
        registry.addInterceptor(new AccessInterceptor()).addPathPatterns("/**");//所有路径都被拦截
        */
        InterceptorRegistration registration = registry.addInterceptor(new AccessInterceptor());
        registration.addPathPatterns("/**");//所有路径都被拦截
        registration.excludePathPatterns( //添加不拦截路径
                //"登录路径",
                "/**/*.html",
                "/**/*.htm",    // html静态资源
                "/**/.js",      // js静态资源
                "/**/*.css"     // CSS静态资源
        );
    }
}
