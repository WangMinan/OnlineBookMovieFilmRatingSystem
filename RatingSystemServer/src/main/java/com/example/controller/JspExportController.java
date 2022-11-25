package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author : [wangminan]
 * @description : [将jsp页面暴露出来的Controller]
 */
@Controller
@RequestMapping("/view")
public class JspExportController {
    // 接下来是暴露jsp页面的接口 做页面跳转时请使用如下接口

    /**
     * 验证jsp页面是否能够正常访问
     * @return jsp页面
     */
    @RequestMapping("/hello")
    public String hello(){
        return "/HelloWorld";
    }

    @RequestMapping(value = "/loginPage", method = RequestMethod.GET)
    public String login(){
        return "/loginPage";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(){
        return "/register";
    }

    @RequestMapping(value = "/booksPage", method = RequestMethod.GET)
    public String booksPage(){
        return "/booksPage";
    }
}
