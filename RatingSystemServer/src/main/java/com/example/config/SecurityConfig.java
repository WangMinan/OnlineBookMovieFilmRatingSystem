package com.example.config;

import com.example.filter.JwtAuthenticationTokenFilter;
import com.example.handler.AuthenticationEntryPointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author : [wangminan]
 * @description : [BCryptPasswordEncoder加密器]
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启权限管理
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPoint;

    private static final String[] URL_WHITELIST ={
            "/admin/login",
            "/admin/logout",
            //  这算是自己摸索出来的通配符
            "/user/**"
    };

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean // 获取到AuthenticationManager对象后，就可以调用其authenticate方法进行身份认证
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // 配置拦截规则
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 关闭权限校验 在没有权限校验流程的情况下用anonymous()方法会报403错误
                    .antMatchers(URL_WHITELIST).permitAll()
                // 接下来的一句和@PreAuthorize("@MyAuthorityCheck.hasAuthority('system:dept:list')") 一样
                // .antMatchers("/testCors").hasAuthority("@MyAuthorityCheck.hasAuthority('system:dept:list')")
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();

        // 在UsernamePasswordAuthenticationFilter之前添加JwtAuthenticationTokenFilter
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        //  配置异常处理器
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint);

        // 允许跨域
        http.cors();
    }
}
