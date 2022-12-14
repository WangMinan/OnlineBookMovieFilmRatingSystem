package com.example.filter;

import com.example.exception.TokenException;
import com.example.pojo.LoginAdmin;
import com.example.util.JwtUtil;
import com.example.util.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 我们需要自定义一个过滤器，这个过滤器会去获取请求头中的token，对token进行解析取出其中的userid。
 * 使用userid去redis中获取对应的LoginUser对象。
 * 然后封装Authentication对象存入SecurityContextHolder
 * @author : [wangminan]
 * @description : [Jwt认证过滤器]
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 使用doFilter方法将请求传递给过滤器链 对请求进行放行
        // 获取token
        // 如已经登录 则请求头中会有token
        String token = request.getHeader("token");
        if(!StringUtils.hasText(token)){
            // 没有token不解析 直接放行
            filterChain.doFilter(request,response);
        } else {
            // 解析token
            String adminId;
            try {
                Claims claims = JwtUtil.parseJWT(token);
                adminId = claims.getSubject();
            } catch (Exception e) {
                if(e instanceof io.jsonwebtoken.ExpiredJwtException){
                    // token过期
                    throw new TokenException("token过期");
                } else {
                    // token错误
                    throw new TokenException("token错误");
                }
            }
            // 从redis中获取LoginUser
            String redisKey = "adminLogin:" + adminId;
            LoginAdmin loginAdmin = redisCache.getCacheObject(redisKey);
            if(Objects.isNull(loginAdmin)){
                throw new RuntimeException("用户不存在");
            }
            // 存入SecurityContextHolder

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(loginAdmin, null, null);
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            filterChain.doFilter(request,response);
        }
    }
}
