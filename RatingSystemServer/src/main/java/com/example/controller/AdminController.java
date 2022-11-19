package com.example.controller;

import com.example.domain.Admin;
import com.example.domain.User;
import com.example.pojo.QueryInfo;
import com.example.pojo.R;
import com.example.service.AdminService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author : [wangminan]
 * @description : [一句话描述该类的功能]
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public R handleAdminLogin(@RequestBody Admin admin){
        return adminService.login(admin);
    }

    @GetMapping("/logout")
    public R handleAdminLogout(){
        return adminService.logout();
    }

    @GetMapping("/users/{query}/{pagenum}/{pagesize}")
    public R handleGetAllUsers(@PathVariable("query") String query,
                               @PathVariable("pagenum") int pagenum,
                               @PathVariable("pagesize") int pagesize){
        QueryInfo queryInfo = new QueryInfo(query, pagenum, pagesize);
        Map<String,Object> map = userService.getAllUsers(queryInfo);
        return R.ok(map);
    }

    @PostMapping("/users")
    public R handleAddUser(@RequestBody User user){
        return userService.addUser(user);
    }
}
