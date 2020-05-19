package com.tzy.demo.annotation.interceptor;

import com.tzy.demo.annotation.login.LoginRequired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tangzanyong
 * @description 资源访问拦截器
 * @date 2020/5/15
 **/
public class AccessInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("进入拦截器了");

        // 反射获取方法上的LoginRequred注解
        // 当springboot1.x升级到springboot2.x，会抛异常java.lang.ClassCastException: org.springframework.web.servlet.resource.ResourceHttpRequestHandler cannot be cast to org.springframework.web.method.HandlerMethod
        // 因为springboot2.x 对静态资源也进行了拦截，当拦截器拦截到请求之后，但controller里并没有对应的请求时，该请求会被当成是对静态资源的请求。此时的handler就是 ResourceHttpRequestHandler，就会抛出上述错误。
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            LoginRequired loginRequired = handlerMethod.getMethod().getAnnotation(LoginRequired.class);
            if(loginRequired == null){ //不需要登录
                return true;
            }
            // 有LoginRequired注解说明需要登录，提示用户登录
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().print("你访问的资源需要登录");
            return false;
        }else if(handler instanceof ResourceHttpRequestHandler){ //spring boot 2.0对静态资源也进行了拦截，当拦截器拦截时放行
            return true;
        }
        return false;
    }
    /*
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
    */
}
