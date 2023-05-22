package com.fairing.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by flanker on 2022/05/04.
 */
//配置登录拦截器
public class LoginInterceptor extends HandlerInterceptorAdapter {

    //继承并重写preHandle方法
    //如果未登录用户试图进入后台页面返回false执行拦截操作
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
