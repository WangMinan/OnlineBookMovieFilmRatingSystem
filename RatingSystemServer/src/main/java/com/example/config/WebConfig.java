package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : [wangminan]
 * @description : [为swagger-ui.html配置访问路径]
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        /**
         * 前后分离之后SpringBoot+SpringSecurity放行Swagger访问后,security跨域配置失效的问题
         */
        registry.addViewController("/swagger-ui/")
                .setViewName("forward:/swagger-ui/index.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(31556926);
        /**
         * 项目用的swagger生成的接口文档,同时也有security权限验证,为了方便后端自己测试,所以接口测试直接访问swagger;
         * 但是security如果没有放行swagger的话,本地是访问不到swagger的,同时前端要访问后端接口,也有跨域问题;
         * Security没有放行swagger访问的时候,用swagger请求接口会报错:
         */
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false);
    }
}

