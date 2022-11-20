package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : [wangminan]
 * @description : [一句话描述该类的功能]
 */
@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * 验证jsp页面是否能够正常访问
     * @return jsp页面
     */
    @RequestMapping("/hello")
    public String hello(){
        return "/HelloWorld";
    }
}
