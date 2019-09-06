package org.yzr.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录拦截器
 *
 * @author guolf
 */
public class AuthenticationInterceptor extends HandlerInterceptorAdapter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        System.out.println("getRequestURI = " + request.getRequestURI());
        if (session.getAttribute("hasLogin") != null) {
            return true;
        }
        response.sendRedirect("/login");
        return false;
    }
}
