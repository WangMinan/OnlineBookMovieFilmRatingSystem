package com.example.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 实现未登录情况写评价、查看自己评价的过滤功能
 */
@WebFilter(urlPatterns = {"/user/assessments", "/user/getMyAssessments/*"}, filterName = "userLoginFilter")
public class UserFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession session = req.getSession();

        String requestPath = req.getServletPath();

        String name = (String) req.getSession().getAttribute("username");
        //如果session中没有用户名，跳转到登陆页面
        if(name == null) {
            System.out.println("已过滤");
            HttpServletResponse res = (HttpServletResponse) servletResponse;
            res.sendRedirect("/view/loginPage");
        } else {
            System.out.println("已放行");
            servletRequest.setCharacterEncoding("UTF-8");
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {
    }
}
