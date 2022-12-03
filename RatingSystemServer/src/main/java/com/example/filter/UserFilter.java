package com.example.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter(urlPatterns = "/user/getMyAssessments", filterName = "userLoginFilter")
public class UserFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession session = req.getSession();

        String requestPath = req.getServletPath();
        String name = req.getParameter("username");
        if(name == null) {
            System.out.println("已过滤");
            HttpServletResponse res = (HttpServletResponse) servletResponse;
            res.sendRedirect("/view/loginPage");
            System.out.println("Hello");
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
