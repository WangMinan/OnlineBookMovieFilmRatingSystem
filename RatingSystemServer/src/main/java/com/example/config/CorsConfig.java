package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : [wangminan]
 * @description : [跨域请求配置类]
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路径
        registry.addMapping("/**")
                // 设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                // 是否允许cookie
                .allowCredentials(true)
                // 设置允许的请求方式
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE","OPTIONS")
                // 设置允许的header属性
                .allowedHeaders("*")
                // 我们这次不带token 只在前端挂载路由守卫 所以可以直接写*
                .allowedOriginPatterns("*")
                // 跨域允许时间
                .maxAge(3600);
    }
}
