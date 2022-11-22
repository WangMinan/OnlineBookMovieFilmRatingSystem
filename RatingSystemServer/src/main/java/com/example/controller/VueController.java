package com.example.controller;

import com.example.pojo.R;
import com.example.service.VueRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : [wangminan]
 * @description : [前端动态链接等Vue功能的Controller]
 */
@RestController
public class VueController {

    @Autowired
    private VueRouteService vueRouteService;

    @GetMapping("/admin/menus")
    public R handleGetMenus(){
        return vueRouteService.getMenus();
    }
}
