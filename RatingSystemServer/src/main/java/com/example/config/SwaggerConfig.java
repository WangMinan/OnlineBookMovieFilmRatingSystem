package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

/**
 * @author : [wangminan]
 * @description : [swagger配置类]
 */
@Configuration
public class SwaggerConfig {
    /**
     * 配置swagger的Docket bean
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30) // 指定swagger3.0版本
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.controller"))
                .build()
                .apiInfo(createApiInfo())
                .enable(true); // 不需要使用swagger时在这个位置做全局关闭;
    }
    /**
     * 配置swagger的ApiInfo bean
     * @return
     */
    @Bean
    public ApiInfo createApiInfo(){
        return new ApiInfo("书影音评价系统 Swagger"
                ,"书影音评价系统API文档"
                ,"3.0"
                ,"http://wangminan.me"
                ,new Contact("王旻安", "http://wangminan.me",
                "wangminan0811@hotmail.com")
                ,"Apache 2.0"
                ,"http://www.apache.org/licenses/LICENSE-2.0"
                ,new ArrayList<>());
    }
}
