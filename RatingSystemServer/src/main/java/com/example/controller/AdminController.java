package com.example.controller;

import com.example.domain.Admin;
import com.example.domain.Book;
import com.example.domain.User;
import com.example.pojo.QueryInfo;
import com.example.pojo.R;
import com.example.service.*;
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

    /*
     * 这回我们遵循Restful风格开发把查询用户改成GET请求了，但是GET请求没有请求体，所以我们需要把查询条件放到请求头里面
     * 这样一来如果query是空的话就会出现"//"的路径，这种路径是不被SpringSecurity允许的
     * 所以约定用MEANINGLESS_QUERY代替空查询
     */
    private static final String MEANINGLESS_QUERY="AAA";

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private FilmService filmService;

    @Autowired
    private MusicService musicService;

    @PostMapping("/login")
    public R handleAdminLogin(@RequestBody Admin admin){
        return adminService.login(admin);
    }

    @GetMapping("/logout")
    public R handleAdminLogout(){
        return adminService.logout();
    }

    // 接下来是用户管理部分
    @GetMapping("/users/{query}/{pagenum}/{pagesize}")
    public R handleGetAllUsers(@PathVariable("query") String query,
                               @PathVariable("pagenum") int pagenum,
                               @PathVariable("pagesize") int pagesize){
        if(query.equals(MEANINGLESS_QUERY)){
            query="";
        }
        QueryInfo queryInfo = new QueryInfo(query, pagenum, pagesize);
        Map<String,Object> map = userService.getAllUsers(queryInfo);
        return R.ok(map);
    }

    @PostMapping("/users")
    public R handleAddUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @DeleteMapping("/users/{id}")
    public R handleDeleteUser(@PathVariable("id") long id){
        return userService.deleteUser(id);
    }

    @PutMapping("/users/{id}")
    public R handleUpdateUser(@PathVariable("id") long id, @RequestBody User user){
        return userService.updateUser(id, user);
    }

    // 接下来是书籍管理部分
    @GetMapping("/books/{query}/{pagenum}/{pagesize}")
    public R handleGetAllBooks(@PathVariable("query") String query,
                               @PathVariable("pagenum") int pagenum,
                               @PathVariable("pagesize") int pagesize){
        if(query.equals(MEANINGLESS_QUERY)){
            query="";
        }
        QueryInfo queryInfo = new QueryInfo(query, pagenum, pagesize);
        Map<String,Object> map = bookService.getAllBooks(queryInfo);
        return R.ok(map);
    }

    @PostMapping("/books")
    public R handleAddBook(@RequestBody Book book){
        return bookService.addBook(book);
    }

    @DeleteMapping("/books/{id}")
    public R handleDeleteBook(@PathVariable("id") long id){
        return bookService.deleteBook(id);
    }

    @PutMapping("/books/{id}")
    public R handleUpdateBook(@PathVariable("id") long id, @RequestBody Book book){
        return bookService.updateBook(id, book);
    }

    // 接下来是电影管理部分
    @GetMapping("/movies/{query}/{pagenum}/{pagesize}")
    public R handleGetAllMovies(@PathVariable("query") String query,
                               @PathVariable("pagenum") int pagenum,
                               @PathVariable("pagesize") int pagesize){
        if(query.equals(MEANINGLESS_QUERY)){
            query="";
        }
        QueryInfo queryInfo = new QueryInfo(query, pagenum, pagesize);
        Map<String,Object> map = filmService.getAllFilms(queryInfo);
        return R.ok(map);
    }
}