package com.example.controller;

import com.example.domain.Assessment;
import com.example.domain.User;
import com.example.pojo.QueryInfo;
import com.example.pojo.R;
import com.example.service.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author : [wangminan]
 * @description : [用户Controller]
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static final String MEANINGLESS_QUERY = "";
    private static final int MAX_PAGE_SIZE = Integer.MAX_VALUE;
    private static final int MIN_PAGE_NUM = 1;

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

    // 接下来是用户状态控制
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public R handleUserRegister(@RequestBody User user){
        return userService.registerUser(user);
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public R handleUserLogin(HttpServletRequest request, HttpServletResponse response,
                             @RequestBody LoginUser loginUser){
        User user = loginUser.getUser();
        if(userService.login(user)){
            // 验证session已经写入
             System.out.println(request.getSession().getAttribute("username"));

            // 设置Cookie和Session
            Cookie nameCookie = new Cookie("username",user.getUsername());
            nameCookie.setMaxAge(60*60*24*7);
            Cookie passwordCookie = new Cookie("password",user.getPassword());
            passwordCookie.setMaxAge(60*60*24*7);
            Cookie rememberMeCookie = new Cookie("rememberme",String.valueOf(loginUser.getRememberMe()));
            rememberMeCookie.setMaxAge(60*60*24*7);
            response.addCookie(nameCookie);
            response.addCookie(passwordCookie);
            response.addCookie(rememberMeCookie);

            // 这样的话随着用户登录他的个人信息就存在session里面了，修改个人信息的时候可以直接拿出来用不需要发送请求
            user = userService.getUserByUsername(user.getUsername());
            request.getSession().setAttribute("id",user.getId());
            request.getSession().setAttribute("username",user.getUsername());
            request.getSession().setAttribute("password",user.getPassword());
            request.getSession().setAttribute("mail",user.getMail());
            System.out.println(request.getSession());
            return R.ok("登录成功");
        } else {
            return R.error("用户名或密码错误,登陆失败");
        }
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    @ResponseBody
    public R handleUserLogout(HttpServletRequest request){
        // 清除Session
        request.getSession().removeAttribute("username"); // 强制过期
        return R.ok("退出成功");
    }

    // 接下来是书籍、电影、音乐查看与留言
    @RequestMapping(value = "/books")
    public String booksInit(HttpServletRequest request, HttpServletResponse response) throws IOException {

        QueryInfo queryInfo = new QueryInfo();
        queryInfo.setQuery(MEANINGLESS_QUERY);
        queryInfo.setPagenum(MIN_PAGE_NUM);
        queryInfo.setPagesize(MAX_PAGE_SIZE);
        request.setAttribute("books", bookService.getAllBooks(queryInfo));
        request.setAttribute("assessments", assessmentService.getAllAssessments(queryInfo));
        return "/booksPage";
    }

    @RequestMapping(value = "/films")
    public String filmsInit(HttpServletRequest request, HttpServletResponse response) throws IOException {

        QueryInfo queryInfo = new QueryInfo();
        queryInfo.setQuery(MEANINGLESS_QUERY);
        queryInfo.setPagenum(MIN_PAGE_NUM);
        queryInfo.setPagesize(MAX_PAGE_SIZE);
        request.setAttribute("films", filmService.getAllFilms(queryInfo));
        request.setAttribute("assessments", assessmentService.getAllAssessments(queryInfo));
        return "/filmsPage";
    }

    @RequestMapping(value = "/musics")
    public String musicsInit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        QueryInfo queryInfo = new QueryInfo();
        queryInfo.setQuery(MEANINGLESS_QUERY);
        queryInfo.setPagenum(MIN_PAGE_NUM);
        queryInfo.setPagesize(MAX_PAGE_SIZE);
        request.setAttribute("musics", musicService.getAllMusics(queryInfo));
        request.setAttribute("assessments", assessmentService.getAllAssessments(queryInfo));
        return "/musicsPage";
    }

    // 资源的分类展示请由前端进行处理 页面初始化时已经返回了全部资源

    @RequestMapping(value = "/assessments", method = RequestMethod.POST)
    @ResponseBody
    public R handleAssessment(HttpServletRequest request, HttpServletResponse response,
                              @RequestBody Assessment assessmentView) throws IOException {
        checkSession(request, response);
        assessmentView.setUsername((String) request.getSession().getAttribute("username"));
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format(date);
        assessmentView.setPostdate(time);
        return assessmentService.addAssessment(assessmentView);
    }

    // 修改个人信息
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseBody
    public R handleUserUpdate(HttpServletRequest request, HttpServletResponse response,
                              @RequestBody User user) throws IOException {
        checkSession(request, response);
        long id = (long) request.getSession().getAttribute("id");
        return userService.updateUser(id,user);
    }

    @GetMapping("/books/{query}/{pagenum}/{pagesize}")
    @ResponseBody
    public R handleGetAllBooks(HttpServletRequest request,
                               HttpServletResponse response,
                               @PathVariable("query") String query,
                               @PathVariable("pagenum") int pagenum,
                               @PathVariable("pagesize") int pagesize) throws IOException {
        checkSession(request, response);
        if(query.equals(MEANINGLESS_QUERY)){
            query="";
        }
        QueryInfo queryInfo = new QueryInfo(query, pagenum, pagesize);
        Map<String,Object> map = bookService.getAllBooks(queryInfo);
        return R.ok(map);
    }

    @GetMapping("/films/{query}/{pagenum}/{pagesize}")
    @ResponseBody
    public R handleGetAllMovies(HttpServletRequest request,
                                HttpServletResponse response,
                                @PathVariable("query") String query,
                                @PathVariable("pagenum") int pagenum,
                                @PathVariable("pagesize") int pagesize) throws IOException {
        checkSession(request, response);
        if(query.equals(MEANINGLESS_QUERY)){
            query="";
        }
        QueryInfo queryInfo = new QueryInfo(query, pagenum, pagesize);
        Map<String,Object> map = filmService.getAllFilms(queryInfo);
        return R.ok(map);
    }

    @GetMapping("/musics/{query}/{pagenum}/{pagesize}")
    @ResponseBody
    public R handleGetAllMusics(HttpServletRequest request,
                                HttpServletResponse response,
                                @PathVariable("query") String query,
                                @PathVariable("pagenum") int pagenum,
                                @PathVariable("pagesize") int pagesize) throws IOException {
        checkSession(request, response);
        if(query.equals(MEANINGLESS_QUERY)){
            query="";
        }
        QueryInfo queryInfo = new QueryInfo(query, pagenum, pagesize);
        Map<String,Object> map = musicService.getAllMusics(queryInfo);
        return R.ok(map);
    }

    private static void checkSession(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 检查Session 如果没有登录则跳转到登录页面
        if(request.getSession().getAttribute("username") == null){
            response.sendRedirect("/view/loginPage");
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class LoginUser{
        private User user;
        private String rememberMe;
    }
}
