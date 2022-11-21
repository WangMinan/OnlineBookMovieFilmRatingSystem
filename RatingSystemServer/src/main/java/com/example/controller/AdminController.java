package com.example.controller;

import com.example.domain.*;
import com.example.pojo.QueryInfo;
import com.example.pojo.R;
import com.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author : [wangminan]
 * @description : [管理员Controller]
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

    @Autowired
    private AssessmentService assessmentService;

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
    @GetMapping("/films/{query}/{pagenum}/{pagesize}")
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

    @PostMapping("/films")
    public R handleAddMovie(@RequestBody Film film){
        return filmService.addFilm(film);
    }

    @DeleteMapping("/films/{id}")
    public R handleDeleteMovie(@PathVariable("id") long id){
        return filmService.deleteFilm(id);
    }

    @PutMapping("/films/{id}")
    public R handleUpdateMovie(@PathVariable("id") long id, @RequestBody Film film){
        return filmService.updateFilm(id, film);
    }

    // 接下来是音乐管理部分
    @GetMapping("/musics/{query}/{pagenum}/{pagesize}")
    public R handleGetAllMusics(@PathVariable("query") String query,
                                @PathVariable("pagenum") int pagenum,
                                @PathVariable("pagesize") int pagesize){
        if(query.equals(MEANINGLESS_QUERY)){
            query="";
        }
        QueryInfo queryInfo = new QueryInfo(query, pagenum, pagesize);
        Map<String,Object> map = musicService.getAllMusics(queryInfo);
        return R.ok(map);
    }

    @PostMapping("/musics")
    public R handleAddMusic(@RequestBody Music music){
        return musicService.addMusic(music);
    }

    @DeleteMapping("/musics/{id}")
    public R handleDeleteMusic(@PathVariable("id") long id){
        return musicService.deleteMusic(id);
    }

    @PutMapping("/musics/{id}")
    public R handleUpdateMusic(@PathVariable("id") long id, @RequestBody Music music){
        return musicService.updateMusic(id, music);
    }

    // 接下来是评价管理
    @GetMapping("/assessments/{query}/{pagenum}/{pagesize}")
    public R handleGetAllAssessments(@PathVariable("query") String query,
                                     @PathVariable("pagenum") int pagenum,
                                     @PathVariable("pagesize") int pagesize){
        if(query.equals(MEANINGLESS_QUERY)){
            query="";
        }
        QueryInfo queryInfo = new QueryInfo(query, pagenum, pagesize);
        Map<String,Object> map = assessmentService.getAllAssessments(queryInfo);
        return R.ok(map);
    }

    @DeleteMapping("/assessments/{id}")
    public R handleDeleteAssessment(@PathVariable("id") long id){
        return assessmentService.deleteAssessment(id);
    }
}
