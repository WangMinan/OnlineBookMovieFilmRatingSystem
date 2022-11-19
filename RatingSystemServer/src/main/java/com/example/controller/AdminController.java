package com.example.controller;

import com.example.domain.Admin;
import com.example.pojo.R;
import com.example.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : [wangminan]
 * @description : [一句话描述该类的功能]
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public R handleAdminLogin(@RequestBody Admin admin){
        return adminService.login(admin);
    }

    @GetMapping("/logout")
    public R handleAdminLogout(){
        return adminService.logout();
    }
}
